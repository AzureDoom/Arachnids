package mod.azure.arachnids.entity.bugs;

import mod.azure.arachnids.config.ArachnidsConfig;
import mod.azure.arachnids.entity.BaseBugEntity;
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
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PlasmaEntity extends BaseBugEntity {

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public PlasmaEntity(EntityType<? extends BaseBugEntity> entityType, Level world) {
		super(entityType, world);
		this.xpReward = ArachnidsConfig.plasma_exp;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			if (event.isMoving()) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("moving"));
				return PlayState.CONTINUE;
			}
			if (this.entityData.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("attack_ranged"));
				return PlayState.CONTINUE;
			}
			if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
				event.getController().setAnimation(RawAnimation.begin().thenPlayAndHold("death"));
				return PlayState.CONTINUE;
			}
			event.getController().setAnimation(RawAnimation.begin().thenLoop("idle"));
			return PlayState.CONTINUE;
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers());
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
				.add(Attributes.MAX_HEALTH, ArachnidsConfig.plasma_health)
				.add(Attributes.ATTACK_DAMAGE, ArachnidsConfig.plasma_melee)
				.add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 15.0D)
				.add(Attributes.ATTACK_KNOCKBACK, 0.0D);
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
		return ArachnidsConfig.plasma_armor;
	}

	public int getVariant() {
		return Mth.clamp((Integer) this.entityData.get(VARIANT), 1, 1);
	}

	public int getVariants() {
		return 1;
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
		return 2.55F;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ArachnidsSounds.PLASMA_IDLE;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ArachnidsSounds.PLASMA_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ArachnidsSounds.PLASMA_DEATH;
	}

	protected SoundEvent getStepSound() {
		return ArachnidsSounds.PLASMA_MOVING;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.75F, 1.0F);
	}

}