package mod.azure.arachnids.entity;

import mod.azure.arachnids.entity.pathing.CrawlerNavigation;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class BaseBugEntity extends PathfinderMob implements GeoEntity {

	public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(BaseBugEntity.class,
			EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> MOVING = SynchedEntityData.defineId(BaseBugEntity.class,
			EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(BaseBugEntity.class,
			EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public BaseBugEntity(EntityType<? extends PathfinderMob> entityType, Level world) {
		super(entityType, world);
		maxUpStep = 1.5f;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			return PlayState.CONTINUE;
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	public static boolean canSpawn(EntityType<? extends PathfinderMob> type, ServerLevelAccessor world,
			MobSpawnType reason, BlockPos pos, RandomSource random) {
		if (world.getDifficulty() == Difficulty.PEACEFUL)
			return false;
		if ((reason != MobSpawnType.CHUNK_GENERATION && reason != MobSpawnType.NATURAL))
			return !world.getBlockState(pos.below()).is(BlockTags.LOGS);
		return !world.getBlockState(pos.below()).is(BlockTags.LOGS);
	}

	public int getAttckingState() {
		return this.entityData.get(STATE);
	}

	public void setAttackingState(int time) {
		this.entityData.set(STATE, time);
	}

	public int getMovingState() {
		return this.entityData.get(MOVING);
	}

	public void setMovingState(int time) {
		this.entityData.set(MOVING, time);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(VARIANT, 0);
		this.entityData.define(STATE, 0);
		this.entityData.define(MOVING, 0);
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 25) {
			this.remove(Entity.RemovalReason.KILLED);
		}
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverWorldAccess, DifficultyInstance difficulty,
			MobSpawnType spawnReason, SpawnGroupData entityData, CompoundTag entityTag) {
		entityData = super.finalizeSpawn(serverWorldAccess, difficulty, spawnReason, entityData, entityTag);
		this.setVariant(this.random.nextInt());
		return entityData;
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setVariant(tag.getInt("Variant"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
	}

	public void setVariant(int variant) {
		this.entityData.set(VARIANT, variant);
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	@Override
	public boolean isPushable() {
		return true;
	}

	@Override
	protected void pushEntities() {
	}

	@Override
	public MobType getMobType() {
		return MobType.ARTHROPOD;
	}

	@Override
	public float getSoundVolume() {
		return 0.5F;
	}

	public boolean tryLightAttack(Entity target) {
		float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
		float g = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
		if (target instanceof LivingEntity) {
			f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity) target).getMobType());
			g += (float) EnchantmentHelper.getKnockbackBonus(this);
		}
		int i = EnchantmentHelper.getFireAspect(this);
		if (i > 0) {
			target.setSecondsOnFire(i * 4);
		}
		boolean bl = target.hurt(DamageSource.mobAttack(this), f - 4);
		if (bl) {
			if (g > 0.0F && target instanceof LivingEntity) {
				((LivingEntity) target).knockback((double) (g * 0.5F),
						(double) Math.sin(this.getYRot() * 0.017453292F),
						(double) (-Math.cos(this.getYRot() * 0.017453292F)));
				this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
			}
			if (target instanceof Player) {
				Player playerEntity = (Player) target;
				this.disablePlayerShield(playerEntity, this.getMainHandItem(),
						playerEntity.isUsingItem() ? playerEntity.getUseItem() : ItemStack.EMPTY);
			}
			this.doEnchantDamageEffects(this, target);
			this.setLastHurtMob(target);
		}
		return bl;
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
		float g = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
		if (target instanceof LivingEntity) {
			f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity) target).getMobType());
			g += (float) EnchantmentHelper.getKnockbackBonus(this);
		}
		int i = EnchantmentHelper.getFireAspect(this);
		if (i > 0) {
			target.setSecondsOnFire(i * 4);
		}
		boolean bl = target.hurt(DamageSource.mobAttack(this), f);
		if (bl) {
			if (g > 0.0F && target instanceof LivingEntity) {
				((LivingEntity) target).knockback((double) (g * 0.5F),
						(double) Math.sin(this.getYRot() * 0.017453292F),
						(double) (-Math.cos(this.getYRot() * 0.017453292F)));
				this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
			}
			if (target instanceof Player) {
				Player playerEntity = (Player) target;
				this.disablePlayerShield(playerEntity, this.getMainHandItem(),
						playerEntity.isUsingItem() ? playerEntity.getUseItem() : ItemStack.EMPTY);
			}
			this.doEnchantDamageEffects(this, target);
			this.setLastHurtMob(target);
		}
		return bl;
	}

	public boolean tryHeavyAttack(Entity target) {
		float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
		float g = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
		if (target instanceof LivingEntity) {
			f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity) target).getMobType());
			g += (float) EnchantmentHelper.getKnockbackBonus(this);
		}
		int i = EnchantmentHelper.getFireAspect(this);
		if (i > 0) {
			target.setSecondsOnFire(i * 4);
		}
		boolean bl = target.hurt(DamageSource.mobAttack(this), f + 4);
		if (bl) {
			if (g > 0.0F && target instanceof LivingEntity) {
				((LivingEntity) target).knockback((double) (g * 0.5F),
						(double) Math.sin(this.getYRot() * 0.017453292F),
						(double) (-Math.cos(this.getYRot() * 0.017453292F)));
				this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
			}
			if (target instanceof Player) {
				Player playerEntity = (Player) target;
				this.disablePlayerShield(playerEntity, this.getMainHandItem(),
						playerEntity.isUsingItem() ? playerEntity.getUseItem() : ItemStack.EMPTY);
			}
			this.doEnchantDamageEffects(this, target);
			this.setLastHurtMob(target);
		}
		return bl;
	}

	private void disablePlayerShield(Player player, ItemStack mobStack, ItemStack playerStack) {
		if (!mobStack.isEmpty() && !playerStack.isEmpty() && mobStack.getItem() instanceof AxeItem
				&& playerStack.is(Items.SHIELD)) {
			float f = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(this) * 0.05F;
			if (this.random.nextFloat() < f) {
				player.getCooldowns().addCooldown(Items.SHIELD, 100);
				this.level.broadcastEntityEvent(player, (byte) 30);
			}
		}
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		return new CrawlerNavigation(this, world);
	}

	@Override
	public void playAmbientSound() {
		SoundEvent soundEvent = this.getAmbientSound();
		if (soundEvent != null) {
			this.playSound(soundEvent, 0.25F, this.getVoicePitch());
		}
	}

}
