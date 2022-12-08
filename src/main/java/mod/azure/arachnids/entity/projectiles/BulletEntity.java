package mod.azure.arachnids.entity.projectiles;

import mod.azure.arachnids.util.ArachnidsItems;
import mod.azure.arachnids.util.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BulletEntity extends AbstractArrow implements GeoEntity {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private static float bulletdamage;
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public BulletEntity(EntityType<? extends BulletEntity> entityType, Level world) {
		super(entityType, world);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public BulletEntity(Level world, LivingEntity owner, Float damage) {
		super(ProjectilesEntityRegister.BULLETS, owner, world);
		bulletdamage = damage;
	}

	protected BulletEntity(EntityType<? extends BulletEntity> type, double x, double y, double z, Level world) {
		this(type, world);
	}

	protected BulletEntity(EntityType<? extends BulletEntity> type, LivingEntity owner, Level world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		if (owner instanceof Player) {
			this.pickup = AbstractArrow.Pickup.ALLOWED;
		}

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

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return new ClientboundAddEntityPacket(this);
	}

	@Override
	protected void doPostHurtEffects(LivingEntity living) {
		super.doPostHurtEffects(living);
		if (!(living instanceof Player)) {
			living.invulnerableTime = 0;
			living.setDeltaMovement(0, 0, 0);
		}
	}

	@Override
	public void tickDespawn() {
		++this.ticksInAir;
		if (this.ticksInAir >= 40) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
	}

	@Override
	public void shoot(double x, double y, double z, float speed, float divergence) {
		super.shoot(x, y, z, speed, divergence);
		this.ticksInAir = 0;
	}

	@Override
	public void tick() {
		super.tick();
		if (this.ticksInAir >= 80) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		if (this.level.isClientSide()) {
			double d2 = this.getX() + (this.random.nextDouble()) * (double) this.getBbWidth() * 0.5D;
			double f2 = this.getZ() + (this.random.nextDouble()) * (double) this.getBbWidth() * 0.5D;
			this.level.addParticle(ParticleTypes.SMOKE, true, d2, this.getY(), f2, 0, 0, 0);
		}
	}

	public void initFromStack(ItemStack stack) {
		if (stack.getItem() == ArachnidsItems.BULLETS) {
		}
	}

	@Override
	public boolean isNoGravity() {
		if (this.isInWater()) {
			return false;
		} else {
			return true;
		}
	}

	public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

	@Override
	public void setSoundEvent(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.ARMOR_EQUIP_IRON;
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		if (!this.level.isClientSide()) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		this.setSoundEvent(SoundEvents.ARMOR_EQUIP_IRON);
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		if (entityHitResult.getType() != HitResult.Type.ENTITY
				|| !((EntityHitResult) entityHitResult).getEntity().is(entity)) {
			if (!this.level.isClientSide()) {
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
		Entity entity2 = this.getOwner();
		DamageSource damageSource2;
		if (entity2 == null) {
			damageSource2 = DamageSource.arrow(this, this);
		} else {
			damageSource2 = DamageSource.arrow(this, entity2);
			if (entity2 instanceof LivingEntity) {
				((LivingEntity) entity2).setLastHurtMob(entity);
			}
		}
		if (entity.hurt(damageSource2, bulletdamage)) {
			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) entity;
				if (!this.level.isClientSide() && entity2 instanceof LivingEntity) {
					EnchantmentHelper.doPostHurtEffects(livingEntity, entity2);
					EnchantmentHelper.doPostDamageEffects((LivingEntity) entity2, livingEntity);
				}

				this.doPostHurtEffects(livingEntity);
				if (entity2 != null && livingEntity != entity2 && livingEntity instanceof Player
						&& entity2 instanceof ServerPlayer && !this.isSilent()) {
					((ServerPlayer) entity2).connection.send(
							new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
				}
			}
		} else {
			if (!this.level.isClientSide()) {
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(ArachnidsItems.BULLETS);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRenderAtSqrDistance(double distance) {
		return true;
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

}