package mod.azure.arachnids.entity.bugs;

import java.util.List;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.arachnids.config.ArachnidsConfig;
import mod.azure.arachnids.entity.BaseBugEntity;
import mod.azure.arachnids.entity.tasks.BugProjectileAttack;
import mod.azure.arachnids.util.ArachnidsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
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
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TankerEntity extends BaseBugEntity implements SmartBrainOwner<TankerEntity> {

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public TankerEntity(EntityType<? extends BaseBugEntity> entityType, Level world) {
		super(entityType, world);
		this.xpReward = ArachnidsConfig.tanker_exp;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			if (this.entityData.get(STATE) == 0 && event.isMoving())
				return event.setAndContinue(RawAnimation.begin().thenLoop("moving"));
			if (this.entityData.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenLoop("melee"));
			if (this.entityData.get(STATE) == 2 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenLoop("ranged"));
			if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}));
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
	public List<ExtendedSensor<TankerEntity>> getSensors() {
		return ObjectArrayList.of(new NearbyPlayersSensor<>(),
				new NearbyLivingEntitySensor<TankerEntity>().setPredicate((target, entity) -> target instanceof Player
						|| !(target instanceof BaseBugEntity) || target instanceof Villager),
				new HurtBySensor<>(), new UnreachableTargetSensor<TankerEntity>());
	}

	@Override
	public BrainActivityGroup<TankerEntity> getCoreTasks() {
		return BrainActivityGroup.coreTasks(new LookAtTarget<>(), new MoveToWalkTarget<>());
	}

	@Override
	public BrainActivityGroup<TankerEntity> getIdleTasks() {
		return BrainActivityGroup.idleTasks(
				new FirstApplicableBehaviour<TankerEntity>(new TargetOrRetaliate<>(),
						new SetPlayerLookTarget<>().stopIf(target -> !target.isAlive()
								|| target instanceof Player && ((Player) target).isCreative()),
						new SetRandomLookTarget<>()),
				new OneRandomBehaviour<>(
						new SetRandomWalkTarget<>().speedModifier(1).startCondition(entity -> !entity.isAggressive()),
						new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60))));
	}

	@Override
	public BrainActivityGroup<TankerEntity> getFightTasks() {
		return BrainActivityGroup.fightTasks(
				new InvalidateAttackTarget<>().stopIf(
						target -> !target.isAlive() || target instanceof Player && ((Player) target).isCreative()),
				new SetWalkTargetToAttackTarget<>().speedMod(0.5F), new BugProjectileAttack<>(20)
						.whenStarting(entity -> setAggressive(true)).whenStarting(entity -> setAggressive(false)));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		final AABB aabb = new AABB(this.blockPosition().above()).inflate(64D, 64D, 64D);
		this.getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
			if ((e instanceof ScorpionEntity || e instanceof TankerEntity || e instanceof PlasmaEntity)
					&& e.tickCount < 1) {
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		});
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MAX_HEALTH, ArachnidsConfig.tanker_health)
				.add(Attributes.ATTACK_DAMAGE, ArachnidsConfig.tanker_melee).add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 15.0D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level.isClientSide()) {
			this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1000000, 1, false, false));
		}
	}

	@Override
	public int getArmorValue() {
		return ArachnidsConfig.tanker_armor;
	}

	public int getVariant() {
		return Mth.clamp((Integer) this.entityData.get(VARIANT), 1, 1);
	}

	public int getVariants() {
		return 1;
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
		return 3.45F;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ArachnidsSounds.TANKER_IDLE;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ArachnidsSounds.TANKER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ArachnidsSounds.TANKER_DEATH;
	}

	protected SoundEvent getStepSound() {
		return ArachnidsSounds.TANKER_MOVING;
	}
	
	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.75F, 1.0F);
	}

}