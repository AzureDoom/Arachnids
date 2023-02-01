package mod.azure.arachnids.entity;

import mod.azure.arachnids.config.ArachnidsConfig;
import mod.azure.arachnids.entity.bugs.WarriorEntity;
import mod.azure.arachnids.entity.projectiles.BugPlasmaEntity;
import mod.azure.arachnids.entity.projectiles.CustomSmallFireballEntity;
import mod.azure.arachnids.entity.projectiles.FlameFiring;
import mod.azure.azurelib.ai.pathing.AzureNavigation;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
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
import net.minecraft.world.phys.Vec3;

public abstract class BaseBugEntity extends PathfinderMob implements GeoEntity {

	public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(BaseBugEntity.class,
			EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> MOVING = SynchedEntityData.defineId(BaseBugEntity.class,
			EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(BaseBugEntity.class,
			EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

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

	@Override
	public boolean doHurtTarget(Entity target) {
		float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
		float g = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
		int var = this.getRandom().nextInt(0, 4);
		if (target instanceof LivingEntity) {
			f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity) target).getMobType());
			g += (float) EnchantmentHelper.getKnockbackBonus(this);
		}
		int i = EnchantmentHelper.getFireAspect(this);
		if (i > 0) {
			target.setSecondsOnFire(i * 4);
		}
		boolean bl = false;
		if (this instanceof WarriorEntity) {
			if (var == 1)
				bl = target.hurt(DamageSource.mobAttack(this), f - 4);
			if (var == 2)
				bl = target.hurt(DamageSource.mobAttack(this), f);
			else
				bl = target.hurt(DamageSource.mobAttack(this), f + 4);
		} else
			bl = target.hurt(DamageSource.mobAttack(this), f);
		if (bl) {
			if (g > 0.0F && target instanceof LivingEntity) {
				((LivingEntity) target).knockback((double) (g * 0.5F), (double) Math.sin(this.getYRot() * 0.017453292F),
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

	public void shootPlasma(Entity target) {
		if (!this.level.isClientSide) {
			if (this.getTarget() != null) {
				LivingEntity livingentity = this.getTarget();
				Level world = this.getCommandSenderWorld();
				Vec3 vector3d = this.getViewVector(1.0F);
				double d2 = livingentity.getX() - (this.getX() + vector3d.x * 2);
				double d3 = livingentity.getY(0.5) - (this.getY(0.5));
				double d4 = livingentity.getZ() - (this.getZ() + vector3d.z * 2);
				BugPlasmaEntity projectile = new BugPlasmaEntity(level, this, d2, d3, d4,
						ArachnidsConfig.plasma_ranged);
				projectile.setPos(this.getX() + vector3d.x * 2, this.getY(0.5), this.getZ() + vector3d.z * 2);
				world.addFreshEntity(projectile);
			}
		}
	}

	public void shootFlames(Entity target) {
		if (!this.level.isClientSide) {
			if (this.getTarget() != null) {
				LivingEntity livingentity = this.getTarget();
				Level world = this.getCommandSenderWorld();
				Vec3 vector3d = this.getViewVector(1.0F);
				double d2 = livingentity.getX() - (this.getX() + vector3d.x * 2);
				double d3 = livingentity.getY(0.5) - (this.getY(0.5));
				double d4 = livingentity.getZ() - (this.getZ() + vector3d.z * 2);
				FlameFiring projectile = new FlameFiring(level, this, d2, d3, d4);
				projectile.setPos(this.getX() + vector3d.x * 7, this.getY(0.5), this.getZ() + vector3d.z * 7);
				world.addFreshEntity(projectile);
			}
		}
	}

	public void shootFire(Entity target) {
		if (!this.level.isClientSide) {
			if (this.getTarget() != null) {
				LivingEntity livingentity = this.getTarget();
				Level world = this.getCommandSenderWorld();
				Vec3 vector3d = this.getViewVector(1.0F);
				double d2 = livingentity.getX() - (this.getX() + vector3d.x * 2);
				double d3 = livingentity.getY(0.5D) - (this.getY(0.5));
				double d4 = livingentity.getZ() - (this.getZ() + vector3d.z * 2);
				CustomSmallFireballEntity projectile = new CustomSmallFireballEntity(level, this, d2, d3, d4,
						ArachnidsConfig.hopper_firefly_ranged);
				projectile.setPos(this.getX() + vector3d.x * 2, this.getY(0.5), this.getZ() + vector3d.z * 2);
				world.addFreshEntity(projectile);
			}
		}
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		return new AzureNavigation(this, world);
	}

	@Override
	public void playAmbientSound() {
		SoundEvent soundEvent = this.getAmbientSound();
		if (soundEvent != null) {
			this.playSound(soundEvent, 0.25F, this.getVoicePitch());
		}
	}

	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity entity) {
		double d = this.getPerceivedTargetDistanceSquareForMeleeAttack(entity);
		return d <= this.getMeleeAttackRangeSqr(entity);
	}

	@Override
	public double getMeleeAttackRangeSqr(LivingEntity entity) {
		return this.getBbWidth() * 2.0f * this.getBbWidth() + entity.getBbWidth();
	}

}
