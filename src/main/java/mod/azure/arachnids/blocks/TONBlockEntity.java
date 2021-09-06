package mod.azure.arachnids.blocks;

import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.config.ArachnidsConfig.Weapons;
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

public class TONBlockEntity extends Entity {

	@Nullable
	private LivingEntity causingEntity;
	public static Weapons config = ArachnidsMod.config.weapons;

	public TONBlockEntity(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	protected void explode() {
		this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), config.TON_damage, true,
				(config.break_blocks ? Explosion.DestructionType.BREAK : Explosion.DestructionType.NONE));
	}

	public TONBlockEntity(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {
		this(ArachnidsMobs.TON, worldIn);
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