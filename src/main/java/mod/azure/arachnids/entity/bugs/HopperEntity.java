package mod.azure.arachnids.entity.bugs;

import java.util.List;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.BaseBugEntity;
import mod.azure.arachnids.entity.pathing.HopperFlightMoveControl;
import mod.azure.arachnids.entity.tasks.BugMeleeAttack;
import mod.azure.arachnids.entity.tasks.BugProjectileAttack;
import mod.azure.arachnids.entity.tasks.RandomFlyConvergeOnTargetGoal;
import mod.azure.arachnids.util.ArachnidsSounds;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.Animation.LoopType;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.StrafeTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.UnreachableTargetSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;

public class HopperEntity extends BaseBugEntity implements SmartBrainOwner<HopperEntity> {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public HopperEntity(EntityType<? extends BaseBugEntity> entityType, Level world) {
		super(entityType, world);
		this.xpReward = ArachnidsMod.config.hopper_exp;
		this.moveControl = new HopperFlightMoveControl(this);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			if (!this.isAggressive() && event.isMoving() && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenLoop("moving"));
			if (this.isAggressive() && event.isMoving() && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenLoop("flying"));
			if (!event.isCurrentAnimation(RawAnimation.begin().thenLoop("flying")) && !event.isCurrentAnimation(RawAnimation.begin().thenLoop("moving")))
				return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
			return PlayState.CONTINUE;
		})
				.triggerableAnim("death", RawAnimation.begin().thenPlayAndHold("death"))
				.triggerableAnim("melee", RawAnimation.begin().then("melee", LoopType.PLAY_ONCE))
				.triggerableAnim("ranged", RawAnimation.begin().then("flame_breath", LoopType.PLAY_ONCE))
				.triggerableAnim("melee", RawAnimation.begin().then("melee", LoopType.PLAY_ONCE)));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	protected Brain.Provider<?> brainProvider() {
		return new SmartBrainProvider<>(this);
	}

	@Override
	protected void customServerAiStep() {
		tickBrain(this);
	}

	@Override
	public List<ExtendedSensor<HopperEntity>> getSensors() {
		return ObjectArrayList.of(new NearbyPlayersSensor<>(), new NearbyLivingEntitySensor<HopperEntity>().setPredicate((target, entity) -> target instanceof Player || !(target instanceof BaseBugEntity) || target instanceof Villager), new HurtBySensor<>(), new UnreachableTargetSensor<HopperEntity>());
	}

	@Override
	public BrainActivityGroup<HopperEntity> getCoreTasks() {
		return BrainActivityGroup.coreTasks(new LookAtTarget<>(), new StrafeTarget<>(), new MoveToWalkTarget<>());
	}

	@Override
	public BrainActivityGroup<HopperEntity> getIdleTasks() {
		return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<HopperEntity>(new TargetOrRetaliate<>(), new SetPlayerLookTarget<>().stopIf(target -> !target.isAlive() || target instanceof Player && ((Player) target).isCreative()), new SetRandomLookTarget<>()), new OneRandomBehaviour<>(new SetRandomWalkTarget<>().speedModifier(1), new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60))));
	}

	@Override
	public BrainActivityGroup<HopperEntity> getFightTasks() {
		return BrainActivityGroup.fightTasks(new InvalidateAttackTarget<>().stopIf(target -> !target.isAlive() || target instanceof Player && ((Player) target).isCreative() || !(target instanceof BaseBugEntity) || target instanceof Villager), new SetWalkTargetToAttackTarget<>().speedMod(this.getEntityData().get(VARIANT) == 2 ? 0.0F : 2.5F).stopIf(entity -> this.getEntityData().get(VARIANT) == 2), new BugProjectileAttack<>(5).startCondition(entity -> this.getEntityData().get(VARIANT) != 1),
				new BugMeleeAttack<>(15));
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		FlyingPathNavigation birdNavigation = new FlyingPathNavigation(this, world);
		birdNavigation.setCanOpenDoors(false);
		birdNavigation.setCanFloat(true);
		birdNavigation.setCanPassDoors(true);
		return birdNavigation;
	}

	public void travel(Vec3 movementInput) {
		if (this.tickCount % 10 == 0)
			this.refreshDimensions();
		if (this.isAggressive() && this.getTarget() != null) {
			var f = 0.91F;
			var f1 = 0.16277137F / (f * f * f);
			this.moveRelative(this.onGround() ? 0.3F * f1 : 3.6F, movementInput);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale((double) f));
			this.lookAt(this.getTarget(), 30.0F, 30.0F);
			this.calculateEntityAnimation(false);
		} else
			super.travel(movementInput);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(5, new RandomFlyConvergeOnTargetGoal(this, 2, 15, 0.5));
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	@Override
	protected void checkFallDamage(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
	}

	public boolean isInAir() {
		return !this.onGround();
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D).add(Attributes.MAX_HEALTH, ArachnidsMod.config.hopper_health).add(Attributes.ATTACK_DAMAGE, ArachnidsMod.config.hopper_melee).add(Attributes.KNOCKBACK_RESISTANCE, 15.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.FLYING_SPEED, 2.0D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level().isClientSide()) {
			this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1000000, 1, false, false));
		}
	}

	@Override
	public int getArmorValue() {
		return ArachnidsMod.config.hopper_armor;
	}

	public int getVariant() {
		return Mth.clamp((Integer) this.entityData.get(VARIANT), 1, 2);
	}

	public int getVariants() {
		return 2;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", this.getVariant());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setVariant(tag.getInt("Variant"));
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		var var = this.getRandom().nextInt(0, 3);
		this.setVariant(var);
		return spawnDataIn;
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
		return 1.25F;
	}

	@Override
	public Component getCustomName() {
		return this.getVariant() == 2 ? Component.translatable("entity.arachnids.firefly") : super.getCustomName();
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ArachnidsSounds.HOPPER_IDLE;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ArachnidsSounds.HOPPER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ArachnidsSounds.HOPPER_DEATH;
	}

	protected SoundEvent getStepSound() {
		return ArachnidsSounds.HOPPER_MOVING;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

}