package mod.azure.arachnids.entity.projectiles;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.util.ArachnidsItems;
import mod.azure.arachnids.util.ProjectilesEntityRegister;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.util.AzureLibUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class MZ90Entity extends AbstractArrow implements GeoEntity {

	protected int timeInAir;
	protected boolean inAir;
	protected String type;
	private static final EntityDataAccessor<Boolean> SPINNING = SynchedEntityData.defineId(MZ90Entity.class, EntityDataSerializers.BOOLEAN);
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public MZ90Entity(EntityType<? extends MZ90Entity> entityType, Level world) {
		super(entityType, world);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public MZ90Entity(Level world, LivingEntity owner) {
		super(ProjectilesEntityRegister.MZ90, owner, world);
	}

	protected MZ90Entity(EntityType<? extends MZ90Entity> type, double x, double y, double z, Level world) {
		this(type, world);
	}

	protected MZ90Entity(EntityType<? extends MZ90Entity> type, LivingEntity owner, Level world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public MZ90Entity(Level world, LivingEntity user, boolean spinning) {
		super(ProjectilesEntityRegister.MZ90, user, world);
		this.entityData.set(SPINNING, spinning);
	}

	public boolean isSpinning() {
		return (Boolean) this.entityData.get(SPINNING);
	}

	public void setSpinning(boolean spin) {
		this.entityData.set(SPINNING, spin);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			if (!this.inGround && this.isSpinning())
				return event.setAndContinue(RawAnimation.begin().thenLoop("spin"));
			else
				return event.setAndContinue(RawAnimation.begin().thenLoop("bullet"));
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	public void remove(RemovalReason reason) {
		var areaeffectcloudentity = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
		areaeffectcloudentity.setParticle(ParticleTypes.EXPLOSION);
		areaeffectcloudentity.setRadius(ArachnidsMod.config.MZ90_explode_damage + 2);
		areaeffectcloudentity.setDuration(1);
		areaeffectcloudentity.setPos(this.getX(), this.getEyeY(), this.getZ());
		this.level().addFreshEntity(areaeffectcloudentity);
		this.explode();
		super.remove(reason);
	}

	@Override
	public void shoot(double x, double y, double z, float speed, float divergence) {
		super.shoot(x, y, z, speed, divergence);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("isSpinning", isSpinning());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("isSpinning")) {
			setSpinning(compound.getBoolean("isSpinning"));
		}
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SPINNING, false);
	}

	@Override
	public void tick() {
		if (this.tickCount >= 45 && !this.level().isClientSide())
			this.remove(Entity.RemovalReason.DISCARDED);
		super.tick();
	}

	public void initFromStack(ItemStack stack) {
		if (stack.getItem() == ArachnidsItems.MZ90) {
		}
	}

	public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

	@Override
	public void setSoundEvent(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.SOUL_ESCAPE;
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		if (!this.level().isClientSide()) 
			if (this.tickCount >= 45) {
				this.explode();
				this.entityData.set(SPINNING, false);
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		this.setSoundEvent(SoundEvents.GENERIC_EXPLODE);
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		if (!this.level().isClientSide()) 
			this.explode();
			if (this.tickCount >= 45) {
				this.explode();
				this.entityData.set(SPINNING, false);
				this.remove(Entity.RemovalReason.DISCARDED);
			}
	}

	protected void explode() {
		this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), ArachnidsMod.config.MZ90_explode_damage, (ArachnidsMod.config.cause_fire ? true : false), (ArachnidsMod.config.break_blocks ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE));
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(Items.AIR);
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