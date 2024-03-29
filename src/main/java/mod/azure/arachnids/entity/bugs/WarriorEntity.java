package mod.azure.arachnids.entity.bugs;

import java.util.List;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.BaseBugEntity;
import mod.azure.arachnids.entity.tasks.BugMeleeAttack;
import mod.azure.arachnids.util.ArachnidsSounds;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.Animation.LoopType;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
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
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
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

public class WarriorEntity extends BaseBugEntity implements SmartBrainOwner<WarriorEntity> {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public WarriorEntity(EntityType<? extends BaseBugEntity> entityType, Level world) {
		super(entityType, world);
		this.xpReward = ArachnidsMod.config.warrior_exp;
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			if (event.isMoving() && !this.isAggressive())
				return event.setAndContinue(RawAnimation.begin().thenLoop("moving"));
			if (this.isAggressive() && event.isMoving())
				return event.setAndContinue(RawAnimation.begin().thenLoop("running"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		})
				.triggerableAnim("death", RawAnimation.begin().thenPlayAndHold("death"))
				.triggerableAnim("heavy_attack", RawAnimation.begin().then("heavy_attack", LoopType.PLAY_ONCE))
				.triggerableAnim("light_attack", RawAnimation.begin().then("light_attack", LoopType.PLAY_ONCE))
				.triggerableAnim("normal_attack", RawAnimation.begin().then("normal_attack", LoopType.PLAY_ONCE)));
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
	public List<ExtendedSensor<WarriorEntity>> getSensors() {
		return ObjectArrayList.of(new NearbyPlayersSensor<>(), // Nearby players
				new NearbyLivingEntitySensor<WarriorEntity>().setPredicate((target, entity) -> target instanceof Player || !(target instanceof BaseBugEntity) || target instanceof Villager), // Nearby attackable entities
				new HurtBySensor<>(), // What hurt the entity
				new UnreachableTargetSensor<WarriorEntity>()); // Untarget
	}

	@Override
	public BrainActivityGroup<WarriorEntity> getCoreTasks() {
		return BrainActivityGroup.coreTasks(new LookAtTarget<>(), new MoveToWalkTarget<>());
	}

	@Override
	public BrainActivityGroup<WarriorEntity> getIdleTasks() {
		return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<WarriorEntity>(new TargetOrRetaliate<>(), // Target things
				new SetPlayerLookTarget<>().stopIf(target -> !target.isAlive() || target instanceof Player && ((Player) target).isCreative()), // Look at players
				new SetRandomLookTarget<>()), // Look at things
				new OneRandomBehaviour<>(new SetRandomWalkTarget<>().speedModifier(1), // Walk around
						new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60)))); // Idle Entity
	}

	@Override
	public BrainActivityGroup<WarriorEntity> getFightTasks() {
		return BrainActivityGroup.fightTasks(new InvalidateAttackTarget<>().stopIf(target -> !target.isAlive() || target instanceof Player && ((Player) target).isCreative()), // Untarget
				new SetWalkTargetToAttackTarget<>().speedMod(1.5F), // Move to target
				new BugMeleeAttack<>(13).attackInterval(entity -> 40).whenStarting(entity -> setAggressive(true)).whenStarting(entity -> setAggressive(false))); // Attack things
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D).add(Attributes.MAX_HEALTH, ArachnidsMod.config.warrior_health).add(Attributes.ATTACK_DAMAGE, ArachnidsMod.config.warrior_melee).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.KNOCKBACK_RESISTANCE, 15.0D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level().isClientSide())
			this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1000000, 1, false, false));
	}

	@Override
	public int getArmorValue() {
		return ArachnidsMod.config.warrior_armor;
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
		return 1.55F;
	}

	/*
	 * Variant code
	 */

	public int getVariant() {
		return Mth.clamp((Integer) this.entityData.get(VARIANT), 1, 3);
	}

	public int getVariants() {
		return 3;
	}

	@Override
	public Component getCustomName() {
		return this.getVariant() == 1 ? Component.translatable("entity.arachnids.workertiger") : // Sets Worker Tiger name
				this.getVariant() == 2 ? Component.translatable("entity.arachnids.workerplasma") : // Sets Worker Plasma name
						super.getCustomName(); // Sets Default Name
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverWorldAccess, DifficultyInstance difficulty, MobSpawnType spawnReason, SpawnGroupData entityData, CompoundTag entityTag) {
		entityData = super.finalizeSpawn(serverWorldAccess, difficulty, spawnReason, entityData, entityTag);
		int var = this.getRandom().nextInt(0, 4);
		this.setVariant(var);
		return entityData;
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

	/*
	 * Sound code
	 */

	@Override
	protected SoundEvent getAmbientSound() {
		return ArachnidsSounds.WARRIOR_IDLE;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ArachnidsSounds.WARRIOR_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ArachnidsSounds.WARRIOR_DEATH;
	}

	protected SoundEvent getStepSound() {
		return ArachnidsSounds.WARRIOR_MOVING;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}
}