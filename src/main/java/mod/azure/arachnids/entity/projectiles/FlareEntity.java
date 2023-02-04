package mod.azure.arachnids.entity.projectiles;

import org.jetbrains.annotations.Nullable;

import mod.azure.arachnids.client.ArachnidsParticles;
import mod.azure.arachnids.util.ArachnidsItems;
import mod.azure.arachnids.util.ArachnidsSounds;
import mod.azure.arachnids.util.ProjectilesEntityRegister;
import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.entities.TickingLightEntity;
import mod.azure.azurelib.network.packet.EntityPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class FlareEntity extends AbstractArrow {

	public int life;
	protected int timeInAir;
	protected boolean inAir;
	protected String type;
	private BlockPos lightBlockPos = null;
	private int idleTicks = 0;
	private static final EntityDataAccessor<Boolean> PATHING = SynchedEntityData.defineId(FlareEntity.class,
			EntityDataSerializers.BOOLEAN);

	public FlareEntity(Level world, double x, double y, double z, ItemStack stack) {
		super(ProjectilesEntityRegister.FLARE, world);
		this.absMoveTo(x, y, z);
	}

	public FlareEntity(EntityType<? extends FlareEntity> entityType, Level world) {
		super(entityType, world);
	}

	public FlareEntity(Level world, @Nullable Entity entity, double x, double y, double z, ItemStack stack) {
		this(world, x, y, z, stack);
		this.setOwner(entity);
	}

	public FlareEntity(Level world, ItemStack stack, LivingEntity shooter) {
		this(world, shooter, shooter.getX(), shooter.getY(), shooter.getZ(), stack);
	}

	public FlareEntity(Level world, ItemStack stack, double x, double y, double z, boolean shotAtAngle) {
		this(world, x, y, z, stack);
	}

	public FlareEntity(Level world, ItemStack stack, Entity entity, double x, double y, double z, boolean shotAtAngle) {
		this(world, stack, x, y, z, shotAtAngle);
		this.setOwner(entity);
	}

	public FlareEntity(Level world, ItemStack stack, LivingEntity user, boolean firingmethod) {
		super(ProjectilesEntityRegister.FLARE, user, world);
		this.entityData.set(PATHING, firingmethod);
	}

	@Override
	public void shoot(double x, double y, double z, float speed, float divergence) {
		super.shoot(x, y, z, speed, divergence);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("isGunFired", isGunFired());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("isGunFired")) {
			setFireMethod(compound.getBoolean("isGunFired"));
		}
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(PATHING, false);
	}

	public boolean isGunFired() {
		return (Boolean) this.entityData.get(PATHING);
	}

	public void setFireMethod(boolean spin) {
		this.entityData.set(PATHING, spin);
	}

	@Override
	public void tick() {
		int idleOpt = 100;
		if (getDeltaMovement().lengthSqr() < 0.01)
			idleTicks++;
		else
			idleTicks = 0;
		if (idleOpt <= 0 || idleTicks < idleOpt)
			super.tick();
		if (this.tickCount >= 800)
			this.remove(Entity.RemovalReason.DISCARDED);
		setNoGravity(false);
		++this.life;
		if (this.level.isClientSide()) {
			this.level.addParticle(ArachnidsParticles.FLARE, true, this.getX(), this.getY() - 0.3D, this.getZ(),
					this.random.nextGaussian() * 0.05D, -this.getDeltaMovement().y * 0.07D,
					this.random.nextGaussian() * 0.05D);
		}
		if (this.tickCount > 25)
			this.setDeltaMovement(0.0, -0.1, 0.0);
		boolean isInsideWaterBlock = level.isWaterAt(blockPosition());
		spawnLightSource(isInsideWaterBlock);
	}
	
	@Override
	public void startFalling() {
        this.inGround = false;
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.multiply(this.random.nextFloat() * -2.0f, this.random.nextFloat() * -2.0f, this.random.nextFloat() * -2.0f));
        this.life = 0;
	}

	@Override
	public void handleEntityEvent(byte status) {
		super.handleEntityEvent(status);
	}

	public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

	@Override
	public void setSoundEvent(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return ArachnidsSounds.FLAREGUN;
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		this.setSoundEvent(ArachnidsSounds.FLAREGUN);
		this.setSilent(true);
	}

	@Override
	public boolean isAttackable() {
		return false;
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(ArachnidsItems.FLARE);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRenderAtSqrDistance(double distance) {
		return true;
	}

	@Override
	protected boolean tryPickup(Player player) {
		return false;
	}

	private void spawnLightSource(boolean isInWaterBlock) {
		if (lightBlockPos == null) {
			lightBlockPos = findFreeSpace(level, blockPosition(), 2);
			if (lightBlockPos == null)
				return;
			level.setBlockAndUpdate(lightBlockPos, AzureLibMod.TICKING_LIGHT_BLOCK.defaultBlockState());
		} else if (checkDistance(lightBlockPos, blockPosition(), 2)) {
			BlockEntity blockEntity = level.getBlockEntity(lightBlockPos);
			if (blockEntity instanceof TickingLightEntity) {
				((TickingLightEntity) blockEntity).refresh(isInWaterBlock ? 20 : 0);
			} else
				lightBlockPos = null;
		} else
			lightBlockPos = null;
	}

	private boolean checkDistance(BlockPos blockPosA, BlockPos blockPosB, int distance) {
		return Math.abs(blockPosA.getX() - blockPosB.getX()) <= distance
				&& Math.abs(blockPosA.getY() - blockPosB.getY()) <= distance
				&& Math.abs(blockPosA.getZ() - blockPosB.getZ()) <= distance;
	}

	private BlockPos findFreeSpace(Level world, BlockPos blockPos, int maxDistance) {
		if (blockPos == null)
			return null;

		int[] offsets = new int[maxDistance * 2 + 1];
		offsets[0] = 0;
		for (int i = 2; i <= maxDistance * 2; i += 2) {
			offsets[i - 1] = i / 2;
			offsets[i] = -i / 2;
		}
		for (int x : offsets)
			for (int y : offsets)
				for (int z : offsets) {
					BlockPos offsetPos = blockPos.offset(x, y, z);
					BlockState state = world.getBlockState(offsetPos);
					if (state.isAir() || state.getBlock().equals(AzureLibMod.TICKING_LIGHT_BLOCK))
						return offsetPos;
				}

		return null;
	}

}