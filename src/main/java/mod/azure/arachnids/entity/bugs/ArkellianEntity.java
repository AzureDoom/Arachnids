package mod.azure.arachnids.entity.bugs;

import mod.azure.arachnids.config.ArachnidsConfig;
import mod.azure.arachnids.entity.BaseBugEntity;
import mod.azure.arachnids.util.ArachnidsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ArkellianEntity extends BaseBugEntity {

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public ArkellianEntity(EntityType<? extends BaseBugEntity> entityType, Level world) {
		super(entityType, world);
		this.xpReward = ArachnidsConfig.arkellian_exp;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			if (event.isMoving()) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("moving"));
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

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MAX_HEALTH, ArachnidsConfig.arkellian_health)
				.add(Attributes.ATTACK_DAMAGE, ArachnidsConfig.arkellian_melee).add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	public int getVariant() {
		return Mth.clamp((Integer) this.entityData.get(VARIANT), 1, 1);
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