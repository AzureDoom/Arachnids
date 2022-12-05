package mod.azure.arachnids.blocks;

import org.jetbrains.annotations.Nullable;

import mod.azure.arachnids.config.ArachnidsConfig;
import mod.azure.arachnids.util.ArachnidsMobs;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class MZ90BlockEntity extends Entity {

	@Nullable
	private LivingEntity causingEntity;

	public MZ90BlockEntity(EntityType<?> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
	}

	protected void explode() {
		this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), ArachnidsConfig.MZ90_explode_damage,
				true,
				(ArachnidsConfig.break_blocks ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE));
	}

	public MZ90BlockEntity(Level worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {
		this(ArachnidsMobs.MZ90, worldIn);
		this.absMoveTo(x, y, z);
		double d = level.random.nextDouble() * 6.2831854820251465D;
		this.setDeltaMovement(-Math.sin(d) * 0.02D, 0.20000000298023224D, -Math.cos(d) * 0.02D);
		this.xo = x;
		this.yo = y;
		this.zo = z;
		this.causingEntity = igniter;
	}

	@Nullable
	public LivingEntity getCausingEntity() {
		return this.causingEntity;
	}

	@Override
	protected void defineSynchedData() {
	}

	public void tick() {
		this.remove(Entity.RemovalReason.DISCARDED);
		if (!this.level.isClientSide()) {
			this.explode();
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) {
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag tag) {
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return new ClientboundAddEntityPacket(this);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRenderAtSqrDistance(double distance) {
		return true;
	}

}