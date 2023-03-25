package mod.azure.arachnids.entity.projectiles;

import mod.azure.arachnids.util.ProjectilesEntityRegister;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.util.AzureLibUtil;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class BugPlasmaEntity extends AbstractHurtingProjectile implements GeoEntity {

	public int explosionPower = 1;
	protected int timeInAir;
	protected boolean inAir;
	private float directHitDamage = 0F;
	private LivingEntity shooter;
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public BugPlasmaEntity(EntityType<? extends BugPlasmaEntity> entity, Level world) {
		super(entity, world);
	}

	public void setDirectHitDamage(float directHitDamage) {
		this.directHitDamage = directHitDamage;
	}

	public BugPlasmaEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ, float directHitDamage) {
		super(ProjectilesEntityRegister.BUGPLASMA, shooter, accelX, accelY, accelZ, worldIn);
		this.shooter = shooter;
		this.directHitDamage = directHitDamage;
	}

	public BugPlasmaEntity(Level worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(ProjectilesEntityRegister.BUGPLASMA, x, y, z, accelX, accelY, accelZ, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
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
	public void shoot(double x, double y, double z, float speed, float divergence) {
		super.shoot(x, y, z, speed, divergence);
	}

	@Override
	protected boolean shouldBurn() {
		return false;
	}

	@Override
	public boolean isNoGravity() {
		if (this.isInWater())
			return false;
		return true;
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		super.onHitBlock(result);
		if (!this.level.isClientSide()) {
			this.explode();
			this.remove(Entity.RemovalReason.DISCARDED);
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		if (!this.level.isClientSide()) {
			var entity = entityHitResult.getEntity();
			var entity2 = this.getOwner();
			entity.hurt(damageSources().mobAttack((LivingEntity) entity2), directHitDamage);
			if (entity2 instanceof LivingEntity)
				this.doEnchantDamageEffects((LivingEntity) entity2, entity);
		}
	}

	protected void explode() {
		this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F, false, Level.ExplosionInteraction.NONE);
	}

	public LivingEntity getShooter() {
		return shooter;
	}

	public void setShooter(LivingEntity shooter) {
		this.shooter = shooter;
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

}