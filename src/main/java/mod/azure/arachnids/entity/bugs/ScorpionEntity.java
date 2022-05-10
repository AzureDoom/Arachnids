package mod.azure.arachnids.entity.bugs;

import java.util.List;

import mod.azure.arachnids.entity.BaseBugEntity;
import mod.azure.arachnids.util.ArachnidsSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ScorpionEntity extends BaseBugEntity {

	public AnimationFactory factory = new AnimationFactory(this);

	public ScorpionEntity(EntityType<? extends BaseBugEntity> entityType, World world) {
		super(entityType, world);
		this.experiencePoints = config.scorpion_exp;
	}

	@Override
	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	public static boolean canSpawn(EntityType<ScorpionEntity> type, WorldAccess world, SpawnReason reason, BlockPos pos,
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
	public void baseTick() {
		super.baseTick();
		float q = 150.0F;
		int k = MathHelper.floor(this.getX() - (double) q - 1.0D);
		int l = MathHelper.floor(this.getX() + (double) q + 1.0D);
		int t = MathHelper.floor(this.getY() - (double) q - 1.0D);
		int u = MathHelper.floor(this.getY() + (double) q + 1.0D);
		int v = MathHelper.floor(this.getZ() - (double) q - 1.0D);
		int w = MathHelper.floor(this.getZ() + (double) q + 1.0D);
		List<Entity> list = this.world.getOtherEntities(this,
				new Box((double) k, (double) t, (double) v, (double) l, (double) u, (double) w));
		for (int x = 0; x < list.size(); ++x) {
			Entity entity = (Entity) list.get(x);
			if ((entity instanceof ScorpionEntity || entity instanceof TankerEntity || entity instanceof PlasmaEntity)
					&& entity.age < 1) {
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, config.scorpion_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, config.scorpion_melee)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 15.0D)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient) {
			this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 1000000, 2, false, false));
		}
	}

	@Override
	public int getArmor() {
		return config.scorpion_armor;
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
		return ArachnidsSounds.SCORPION_IDLE;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ArachnidsSounds.SCORPION_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ArachnidsSounds.SCORPION_DEATH;
	}

	protected SoundEvent getStepSound() {
		return ArachnidsSounds.SCORPION_MOVING;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.75F, 1.0F);
	}

}