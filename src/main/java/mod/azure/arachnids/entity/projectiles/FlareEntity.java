package mod.azure.arachnids.entity.projectiles;

import java.util.Iterator;

import org.jetbrains.annotations.Nullable;

import mod.azure.arachnids.client.ArachnidsParticles;
import mod.azure.arachnids.network.EntityPacket;
import mod.azure.arachnids.util.ArachnidsItems;
import mod.azure.arachnids.util.ArachnidsSounds;
import mod.azure.arachnids.util.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class FlareEntity extends PersistentProjectileEntity {

	public int life;
	protected int timeInAir;
	protected boolean inAir;
	protected String type;
	private int ticksInAir;

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
		if (this.isGunFired()) {
			boolean bl = this.isNoClip();
			BlockPos blockPos = this.getBlockPos();
			BlockState blockState = this.world.getBlockState(blockPos);
			Vec3d vec3d = this.getVelocity();
			Vec3d vec3d4;
			if (!blockState.isAir() && !bl) {
				VoxelShape voxelShape = blockState.getCollisionShape(this.world, blockPos);
				if (!voxelShape.isEmpty()) {
					vec3d4 = this.getPos();
					Iterator<?> var7 = voxelShape.getBoundingBoxes().iterator();

					while (var7.hasNext()) {
						Box box = (Box) var7.next();
						if (box.offset(blockPos).contains(vec3d4)) {
							this.inGround = true;
							break;
						}
					}
					this.setFireMethod(false);
				}
			}
			if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
				double f = vec3d.horizontalLength();
				this.setYaw((float) (MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875D));
				this.setPitch((float) (MathHelper.atan2(vec3d.y, f) * 57.2957763671875D));
				this.prevYaw = this.getYaw();
				this.prevPitch = this.getPitch();
			}
			if (this.inAir && !bl) {
				this.age();
				++this.timeInAir;
			} else {
				this.timeInAir = 0;
				Vec3d vec3d3 = this.getPos();
				Vec3d vector3d3 = vec3d3.add(vec3d);
				HitResult hitResult = this.world.raycast(new RaycastContext(vec3d3, vector3d3,
						RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
				if (((HitResult) hitResult).getType() != HitResult.Type.MISS) {
					vector3d3 = ((HitResult) hitResult).getPos();
				}
				while (!this.isRemoved()) {
					EntityHitResult entityHitResult = this.getEntityCollision(vec3d3, vector3d3);
					if (entityHitResult != null) {
						hitResult = entityHitResult;
					}
					if (hitResult != null && ((HitResult) hitResult).getType() == HitResult.Type.ENTITY) {
						Entity entity = ((EntityHitResult) hitResult).getEntity();
						Entity entity2 = this.getOwner();
						if (entity instanceof PlayerEntity && entity2 instanceof PlayerEntity
								&& !((PlayerEntity) entity2).shouldDamagePlayer((PlayerEntity) entity)) {
							hitResult = null;
							entityHitResult = null;
						}
					}
					if (hitResult != null && !bl) {
						this.onCollision((HitResult) hitResult);
						this.velocityDirty = true;
					}
					if (entityHitResult == null || this.getPierceLevel() <= 0) {
						break;
					}
					hitResult = null;
				}
				vec3d = this.getVelocity();
				double d = vec3d.x;
				double e = vec3d.y;
				double g = vec3d.z;
				double h = this.getX() + d;
				double j = this.getY() + e;
				double k = this.getZ() + g;
				double l = vec3d.horizontalLength();
				if (bl) {
					this.setYaw((float) (MathHelper.atan2(-e, -g) * 57.2957763671875D));
				} else {
					this.setYaw((float) (MathHelper.atan2(e, g) * 57.2957763671875D));
				}
				this.setPitch((float) (MathHelper.atan2(e, l) * 57.2957763671875D));
				this.setPitch(updateRotation(this.prevPitch, this.getPitch()));
				this.setYaw(updateRotation(this.prevYaw, this.getYaw()));
				float m = 0.99F;

				this.setVelocity(vec3d.multiply((double) m));
				Vec3d vec3d5 = this.getVelocity();
				this.setVelocity(vec3d5.x, vec3d5.y - 0.05000000074505806D, vec3d5.z);
				this.updatePosition(h, j, k);
				this.checkBlockCollision();
			}
		} else {
			this.setVelocity(this.getVelocity().multiply((double) 0.99F));
			this.setVelocity(this.getVelocity().x, this.getVelocity().y - 0.05000000074505806D, this.getVelocity().z);
		}
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
	public void onRemoved() {
		if (world.getBlockState(this.getBlockPos()) == Blocks.LIGHT.getDefaultState()
				&& world.getBlockState(this.getBlockPos().north()) == Blocks.LIGHT.getDefaultState()
				&& world.getBlockState(this.getBlockPos().south()) == Blocks.LIGHT.getDefaultState()
				&& world.getBlockState(this.getBlockPos().east()) == Blocks.LIGHT.getDefaultState()
				&& world.getBlockState(this.getBlockPos().west()) == Blocks.LIGHT.getDefaultState()
				&& world.getBlockState(this.getBlockPos().up()) == Blocks.LIGHT.getDefaultState()) {
			world.updateNeighbors(this.getBlockPos(), Blocks.AIR);
			world.setBlockState(this.getBlockPos(), Blocks.AIR.getDefaultState(), Block.NOTIFY_NEIGHBORS);
		}
		super.onRemoved();
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		if (world.getBlockState(this.getBlockPos()) == Blocks.AIR.getDefaultState()
				&& world.getBlockState(this.getBlockPos().north()) == Blocks.AIR.getDefaultState()
				&& world.getBlockState(this.getBlockPos().south()) == Blocks.AIR.getDefaultState()
				&& world.getBlockState(this.getBlockPos().east()) == Blocks.AIR.getDefaultState()
				&& world.getBlockState(this.getBlockPos().west()) == Blocks.AIR.getDefaultState()
				&& world.getBlockState(this.getBlockPos().up()) == Blocks.AIR.getDefaultState()) {
			if (this.isAlive())
				world.setBlockState(blockHitResult.getBlockPos().offset(Direction.UP), Blocks.LIGHT.getDefaultState(),
						Block.NOTIFY_NEIGHBORS);
		}
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

}