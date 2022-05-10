package mod.azure.arachnids.entity;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.config.ArachnidsConfig.MobStats;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SpiderNavigation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class BaseBugEntity extends PathAwareEntity implements IAnimatable, IAnimationTickable {

	public static final TrackedData<Integer> STATE = DataTracker.registerData(BaseBugEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	public static final TrackedData<Integer> MOVING = DataTracker.registerData(BaseBugEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	public static final TrackedData<Integer> VARIANT = DataTracker.registerData(BaseBugEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	public static MobStats config = ArachnidsMod.config.stats;
	public AnimationFactory factory = new AnimationFactory(this);

	public BaseBugEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
		this.ignoreCameraFrustum = true;
	}

	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(
				new AnimationController<BaseBugEntity>(this, "idle_controller", 3, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public int getAttckingState() {
		return this.dataTracker.get(STATE);
	}

	public void setAttackingState(int time) {
		this.dataTracker.set(STATE, time);
	}

	public int getMovingState() {
		return this.dataTracker.get(MOVING);
	}

	public void setMovingState(int time) {
		this.dataTracker.set(MOVING, time);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(VARIANT, 0);
		this.dataTracker.startTracking(STATE, 0);
		this.dataTracker.startTracking(MOVING, 0);
	}

	@Override
	protected void updatePostDeath() {
		++this.deathTime;
		if (this.deathTime == 25) {
			this.remove(Entity.RemovalReason.KILLED);
		}
	}

	@Override
	public EntityData initialize(ServerWorldAccess serverWorldAccess, LocalDifficulty difficulty,
			SpawnReason spawnReason, EntityData entityData, NbtCompound entityTag) {
		entityData = super.initialize(serverWorldAccess, difficulty, spawnReason, entityData, entityTag);
		this.setVariant(this.random.nextInt());
		return entityData;
	}

	@Override
	protected boolean isDisallowedInPeaceful() {
		return true;
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.setVariant(tag.getInt("Variant"));
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
	}

	public void setVariant(int variant) {
		this.dataTracker.set(VARIANT, variant);
	}

	@Override
	public boolean doesRenderOnFire() {
		return false;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void tickCramming() {
	}

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.ARTHROPOD;
	}

	@Override
	public float getSoundVolume() {
		return 0.5F;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}

	public boolean tryLightAttack(Entity target) {
		float f = (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
		float g = (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
		if (target instanceof LivingEntity) {
			f += EnchantmentHelper.getAttackDamage(this.getMainHandStack(), ((LivingEntity) target).getGroup());
			g += (float) EnchantmentHelper.getKnockback(this);
		}
		int i = EnchantmentHelper.getFireAspect(this);
		if (i > 0) {
			target.setOnFireFor(i * 4);
		}
		boolean bl = target.damage(DamageSource.mob(this), f - 4);
		if (bl) {
			if (g > 0.0F && target instanceof LivingEntity) {
				((LivingEntity) target).takeKnockback((double) (g * 0.5F),
						(double) MathHelper.sin(this.getYaw() * 0.017453292F),
						(double) (-MathHelper.cos(this.getYaw() * 0.017453292F)));
				this.setVelocity(this.getVelocity().multiply(0.6D, 1.0D, 0.6D));
			}
			if (target instanceof PlayerEntity) {
				PlayerEntity playerEntity = (PlayerEntity) target;
				this.disablePlayerShield(playerEntity, this.getMainHandStack(),
						playerEntity.isUsingItem() ? playerEntity.getActiveItem() : ItemStack.EMPTY);
			}
			this.applyDamageEffects(this, target);
			this.onAttacking(target);
		}
		return bl;
	}

	@Override
	public boolean tryAttack(Entity target) {
		float f = (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
		float g = (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
		if (target instanceof LivingEntity) {
			f += EnchantmentHelper.getAttackDamage(this.getMainHandStack(), ((LivingEntity) target).getGroup());
			g += (float) EnchantmentHelper.getKnockback(this);
		}
		int i = EnchantmentHelper.getFireAspect(this);
		if (i > 0) {
			target.setOnFireFor(i * 4);
		}
		boolean bl = target.damage(DamageSource.mob(this), f);
		if (bl) {
			if (g > 0.0F && target instanceof LivingEntity) {
				((LivingEntity) target).takeKnockback((double) (g * 0.5F),
						(double) MathHelper.sin(this.getYaw() * 0.017453292F),
						(double) (-MathHelper.cos(this.getYaw() * 0.017453292F)));
				this.setVelocity(this.getVelocity().multiply(0.6D, 1.0D, 0.6D));
			}
			if (target instanceof PlayerEntity) {
				PlayerEntity playerEntity = (PlayerEntity) target;
				this.disablePlayerShield(playerEntity, this.getMainHandStack(),
						playerEntity.isUsingItem() ? playerEntity.getActiveItem() : ItemStack.EMPTY);
			}
			this.applyDamageEffects(this, target);
			this.onAttacking(target);
		}
		return bl;
	}

	public boolean tryHeavyAttack(Entity target) {
		float f = (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
		float g = (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
		if (target instanceof LivingEntity) {
			f += EnchantmentHelper.getAttackDamage(this.getMainHandStack(), ((LivingEntity) target).getGroup());
			g += (float) EnchantmentHelper.getKnockback(this);
		}
		int i = EnchantmentHelper.getFireAspect(this);
		if (i > 0) {
			target.setOnFireFor(i * 4);
		}
		boolean bl = target.damage(DamageSource.mob(this), f + 4);
		if (bl) {
			if (g > 0.0F && target instanceof LivingEntity) {
				((LivingEntity) target).takeKnockback((double) (g * 0.5F),
						(double) MathHelper.sin(this.getYaw() * 0.017453292F),
						(double) (-MathHelper.cos(this.getYaw() * 0.017453292F)));
				this.setVelocity(this.getVelocity().multiply(0.6D, 1.0D, 0.6D));
			}
			if (target instanceof PlayerEntity) {
				PlayerEntity playerEntity = (PlayerEntity) target;
				this.disablePlayerShield(playerEntity, this.getMainHandStack(),
						playerEntity.isUsingItem() ? playerEntity.getActiveItem() : ItemStack.EMPTY);
			}
			this.applyDamageEffects(this, target);
			this.onAttacking(target);
		}
		return bl;
	}

	private void disablePlayerShield(PlayerEntity player, ItemStack mobStack, ItemStack playerStack) {
		if (!mobStack.isEmpty() && !playerStack.isEmpty() && mobStack.getItem() instanceof AxeItem
				&& playerStack.isOf(Items.SHIELD)) {
			float f = 0.25F + (float) EnchantmentHelper.getEfficiency(this) * 0.05F;
			if (this.random.nextFloat() < f) {
				player.getItemCooldownManager().set(Items.SHIELD, 100);
				this.world.sendEntityStatus(player, (byte) 30);
			}
		}
	}

	@Override
	public int tickTimer() {
		return age;
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		return new SpiderNavigation(this, world);
	}

	@Override
	public void playAmbientSound() {
		SoundEvent soundEvent = this.getAmbientSound();
		if (soundEvent != null) {
			this.playSound(soundEvent, 0.25F, this.getSoundPitch());
		}
	}

}
