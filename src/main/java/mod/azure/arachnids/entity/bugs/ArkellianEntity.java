package mod.azure.arachnids.entity.bugs;

import mod.azure.arachnids.entity.BaseBugEntity;
import mod.azure.arachnids.util.ArachnidsSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ArkellianEntity extends BaseBugEntity {

	public AnimationFactory factory = new AnimationFactory(this);

	public ArkellianEntity(EntityType<? extends BaseBugEntity> entityType, World world) {
		super(entityType, world);
		this.experiencePoints = config.arkellian_exp;
	}

	@Override
	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("moving", true));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	public static boolean canSpawn(EntityType<ArkellianEntity> type, WorldAccess world, SpawnReason reason, BlockPos pos,
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

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, config.arkellian_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, config.arkellian_melee)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.0D);
	}

	public int getVariant() {
		return MathHelper.clamp((Integer) this.dataTracker.get(VARIANT), 1, 1);
	}

	public int getVariants() {
		return 1;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ArachnidsSounds.ARKELLIAN_IDLE;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ArachnidsSounds.ARKELLIAN_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ArachnidsSounds.ARKELLIAN_DEATH;
	}

	protected SoundEvent getStepSound() {
		return ArachnidsSounds.ARKELLIAN_MOVING;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

}