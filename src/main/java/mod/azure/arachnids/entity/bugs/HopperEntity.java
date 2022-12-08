package mod.azure.arachnids.entity.bugs;

import mod.azure.arachnids.config.ArachnidsConfig;
import mod.azure.arachnids.entity.BaseBugEntity;
import mod.azure.arachnids.entity.goals.HopperFlightMoveControl;
import mod.azure.arachnids.util.ArachnidsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class HopperEntity extends BaseBugEntity {

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public HopperEntity(EntityType<? extends BaseBugEntity> entityType, Level world) {
		super(entityType, world);
		this.xpReward = ArachnidsConfig.hopper_exp;
		this.moveControl = new HopperFlightMoveControl(this, 90, false);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			if (this.entityData.get(MOVING) == 1) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("flying"));
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
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		FlyingPathNavigation birdNavigation = new FlyingPathNavigation(this, world);
		birdNavigation.setCanOpenDoors(false);
		birdNavigation.setCanFloat(true);
		birdNavigation.setCanPassDoors(true);
		return birdNavigation;
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	@Override
	protected void checkFallDamage(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
	}

	public boolean isInAir() {
		return !this.onGround;
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MAX_HEALTH, ArachnidsConfig.hopper_health)
				.add(Attributes.ATTACK_DAMAGE, ArachnidsConfig.hopper_melee)
				.add(Attributes.KNOCKBACK_RESISTANCE, 15.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.FLYING_SPEED, 2.0D)
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
		return ArachnidsConfig.hopper_armor;
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