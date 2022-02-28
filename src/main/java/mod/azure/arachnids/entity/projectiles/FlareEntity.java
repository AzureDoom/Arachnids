package mod.azure.arachnids.entity.projectiles;

import org.jetbrains.annotations.Nullable;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.blocks.TickingLightEntity;
import mod.azure.arachnids.client.ArachnidsParticles;
import mod.azure.arachnids.network.EntityPacket;
import mod.azure.arachnids.util.ArachnidsItems;
import mod.azure.arachnids.util.ArachnidsSounds;
import mod.azure.arachnids.util.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FlareEntity extends PersistentProjectileEntity {

	public int life;
	protected int timeInAir;
	protected boolean inAir;
	protected String type;
	private int ticksInAir;
	private BlockPos lightBlockPos = null;
	private int idleTicks = 0;

	private static final TrackedData<Boolean> PATHING = DataTracker.registerData(FlareEntity.class,
			TrackedDataHandlerRegistry.BOOLEAN);

	public FlareEntity(World world, double x, double y, double z, ItemStack stack) {
		super(ProjectilesEntityRegister.FLARE, world);
		this.updatePosition(x, y, z);
	}

	public FlareEntity(EntityType<? extends FlareEntity> entityType, World world) {
		super(entityType, world);
	}

	public FlareEntity(World world, @Nullable Entity entity, double x, double y, double z, ItemStack stack) {
		this(world, x, y, z, stack);
		this.setOwner(entity);
	}

	public FlareEntity(World world, ItemStack stack, LivingEntity shooter) {
		this(world, shooter, shooter.getX(), shooter.getY(), shooter.getZ(), stack);
	}

	public FlareEntity(World world, ItemStack stack, double x, double y, double z, boolean shotAtAngle) {
		this(world, x, y, z, stack);
	}

	public FlareEntity(World world, ItemStack stack, Entity entity, double x, double y, double z, boolean shotAtAngle) {
		this(world, stack, x, y, z, shotAtAngle);
		this.setOwner(entity);
	}

	public FlareEntity(World world, ItemStack stack, LivingEntity user, boolean firingmethod) {
		super(ProjectilesEntityRegister.FLARE, user, world);
		this.dataTracker.set(PATHING, firingmethod);
	}

	@Override
	public void setVelocity(double x, double y, double z, float speed, float divergence) {
		super.setVelocity(x, y, z, speed, divergence);
		this.ticksInAir = 0;
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putShort("life", (short) this.ticksInAir);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.ticksInAir = tag.getShort("life");
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(PATHING, false);
	}

	public boolean isGunFired() {
		return (Boolean) this.dataTracker.get(PATHING);
	}

	public void setFireMethod(boolean spin) {
		this.dataTracker.set(PATHING, spin);
	}

	@Override
	public void tick() {
		int idleOpt = 100;
		if (getVelocity().lengthSquared() < 0.01)
			idleTicks++;
		else
			idleTicks = 0;
		if (idleOpt <= 0 || idleTicks < idleOpt)
			super.tick();
		if (this.age >= 800) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		setNoGravity(false);
		++this.life;
		if (this.world.isClient) {
			this.world.addParticle(ArachnidsParticles.FLARE, true, this.getX(), this.getY() - 0.3D, this.getZ(),
					this.random.nextGaussian() * 0.05D, -this.getVelocity().y * 0.07D,
					this.random.nextGaussian() * 0.05D);
		}
		boolean isInsideWaterBlock = world.isWater(getBlockPos());
		spawnLightSource(isInsideWaterBlock);
	}

	@Override
	public void handleStatus(byte status) {
		super.handleStatus(status);
	}

	public SoundEvent hitSound = this.getHitSound();

	@Override
	public void setSound(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@Override
	protected SoundEvent getHitSound() {
		return ArachnidsSounds.FLAREGUN;
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		this.setSound(ArachnidsSounds.FLAREGUN);
		this.setSilent(true);
	}

	@Override
	public boolean isAttackable() {
		return false;
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	protected ItemStack asItemStack() {
		return new ItemStack(ArachnidsItems.FLARE);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}

	@Override
	protected boolean tryPickup(PlayerEntity player) {
		return false;
	}

	private void spawnLightSource(boolean isInWaterBlock) {
		if (lightBlockPos == null) {
			lightBlockPos = findFreeSpace(world, getBlockPos(), 2);
			if (lightBlockPos == null)
				return;
			world.setBlockState(lightBlockPos, ArachnidsMod.TICKING_LIGHT_BLOCK.getDefaultState());
		} else if (checkDistance(lightBlockPos, getBlockPos(), 2)) {
			BlockEntity blockEntity = world.getBlockEntity(lightBlockPos);
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

	private BlockPos findFreeSpace(World world, BlockPos blockPos, int maxDistance) {
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
					BlockPos offsetPos = blockPos.add(x, y, z);
					BlockState state = world.getBlockState(offsetPos);
					if (state.isAir() || state.getBlock().equals(ArachnidsMod.TICKING_LIGHT_BLOCK))
						return offsetPos;
				}

		return null;
	}

}