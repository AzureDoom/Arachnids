package mod.azure.arachnids.entity.bugs;

import mod.azure.arachnids.config.ArachnidsConfig;
import mod.azure.arachnids.entity.BaseBugEntity;
import mod.azure.arachnids.entity.goals.BugMeleeGoal;
import mod.azure.arachnids.util.ArachnidsSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class WorkerEntity extends BaseBugEntity {

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	public WorkerEntity(EntityType<? extends BaseBugEntity> entityType, World world) {
		super(entityType, world);
		this.experiencePoints = ArachnidsConfig.worker_exp;
	}

	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving() && !this.isAttacking()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("moving", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
		if (this.isAttacking() && event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("running", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("light_attack", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", EDefaultLoopTypes.PLAY_ONCE));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", EDefaultLoopTypes.LOOP));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<WorkerEntity>(this, "idle_controller", 5, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
		this.goalSelector.add(2, new BugMeleeGoal(this, 1.35D));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, MerchantEntity.class, true));
		this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, ArachnidsConfig.worker_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, ArachnidsConfig.worker_melee)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 15.0D)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient) {
			this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 1000000, 1, false, false));
		}
	}

	@Override
	public int getArmor() {
		return ArachnidsConfig.worker_armor;
	}

	public int getVariant() {
		return MathHelper.clamp((Integer) this.dataTracker.get(VARIANT), 1, 1);
	}

	public int getVariants() {
		return 1;
	}

	@Override
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 1.55F;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ArachnidsSounds.WORKER_IDLE;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ArachnidsSounds.WORKER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ArachnidsSounds.WORKER_DEATH;
	}

	protected SoundEvent getStepSound() {
		return ArachnidsSounds.WARRIOR_MOVING;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

}