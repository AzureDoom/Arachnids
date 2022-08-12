package mod.azure.arachnids.blocks;

import org.jetbrains.annotations.Nullable;

import mod.azure.arachnids.config.ArachnidsConfig;
import mod.azure.arachnids.util.ArachnidsMobs;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class MZ90BlockEntity extends Entity {

	@Nullable
	private LivingEntity causingEntity;

	public MZ90BlockEntity(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	protected void explode() {
		this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(),
				ArachnidsConfig.MZ90_explode_damage, true,
				(ArachnidsConfig.break_blocks ? Explosion.DestructionType.BREAK : Explosion.DestructionType.NONE));
	}

	public MZ90BlockEntity(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {
		this(ArachnidsMobs.MZ90, worldIn);
		this.updatePosition(x, y, z);
		double d = world.random.nextDouble() * 6.2831854820251465D;
		this.setVelocity(-Math.sin(d) * 0.02D, 0.20000000298023224D, -Math.cos(d) * 0.02D);
		this.prevX = x;
		this.prevY = y;
		this.prevZ = z;
		this.causingEntity = igniter;
	}

	@Nullable
	public LivingEntity getCausingEntity() {
		return this.causingEntity;
	}

	@Override
	protected void initDataTracker() {
	}

	public void tick() {
		this.remove(Entity.RemovalReason.DISCARDED);
		if (!this.world.isClient) {
			this.explode();
		}
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound tag) {
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound tag) {
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return new EntitySpawnS2CPacket(this);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}

}