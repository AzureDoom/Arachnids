package mod.azure.arachnids.entity.bugs;

import mod.azure.arachnids.entity.BaseBugEntity;
import mod.azure.arachnids.entity.goals.HopperFlightMoveControl;
import mod.azure.arachnids.util.ArachnidsSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class HopperEntity extends BaseBugEntity {

	public AnimationFactory factory = new AnimationFactory(this);

	public HopperEntity(EntityType<? extends BaseBugEntity> entityType, World world) {
		super(entityType, world);
		this.experiencePoints = config.hopper_exp;
		this.moveControl = new HopperFlightMoveControl(this, 90, false);
	}

	@Override
	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (this.dataTracker.get(MOVING) == 1) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("flying", true));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<HopperEntity>(this, "idle_controller", 5, this::predicate));
	}

	public static boolean canSpawn(EntityType<HopperEntity> type, WorldAccess world, SpawnReason reason, BlockPos pos,
			AbstractRandom random) {
		if (world.getDifficulty() == Difficulty.PEACEFUL)
			return false;
		if ((reason != SpawnReason.CHUNK_GENERATION && reason != SpawnReason.NATURAL))
			return !world.getBlockState(pos.down()).isIn(BlockTags.LOGS)
					&& !world.getBlockState(pos.down()).isIn(BlockTags.LEAVES);
		return !world.getBlockState(pos.down()).isIn(BlockTags.LOGS)
				&& !world.getBlockState(pos.down()).isIn(BlockTags.LEAVES);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		BirdNavigation birdNavigation = new BirdNavigation(this, world);
		birdNavigation.setCanPathThroughDoors(false);
		birdNavigation.setCanSwim(true);
		birdNavigation.setCanEnterOpenDoors(true);
		return birdNavigation;
	}

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	@Override
	protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
	}

	public boolean isInAir() {
		return !this.onGround;
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, config.hopper_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, config.hopper_melee)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 15.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D).add(EntityAttributes.GENERIC_FLYING_SPEED, 2.0D)
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
		return config.hopper_armor;
	}

	public int getVariant() {
		return MathHelper.clamp((Integer) this.dataTracker.get(VARIANT), 1, 2);
	}

	public int getVariants() {
		return 2;
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Variant", this.getVariant());
	}

	@Override
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 1.25F;
	}

	@Override
	public Text getCustomName() {
		return this.getVariant() == 2 ? Text.translatable("entity.arachnids.firefly") : super.getCustomName();
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