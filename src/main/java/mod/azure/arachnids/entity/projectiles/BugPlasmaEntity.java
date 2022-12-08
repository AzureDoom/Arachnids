package mod.azure.arachnids.entity.projectiles;

import mod.azure.arachnids.util.ProjectilesEntityRegister;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BugPlasmaEntity extends AbstractHurtingProjectile implements GeoEntity {

	public int explosionPower = 1;
	protected int timeInAir;
	protected boolean inAir;
	private float directHitDamage = 0F;
	private LivingEntity shooter;
	private SoundEvent attacksound;
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public BugPlasmaEntity(EntityType<? extends BugPlasmaEntity> entity, Level world) {
		super(entity, world);
	}

	public void setDirectHitDamage(float directHitDamage) {
		this.directHitDamage = directHitDamage;
	}

	public BugPlasmaEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ,
			float directHitDamage, SoundEvent attacksound) {
		super(ProjectilesEntityRegister.BUGPLASMA, shooter, accelX, accelY, accelZ, worldIn);
		this.shooter = shooter;
		this.attacksound = attacksound;
		this.directHitDamage = directHitDamage;
	}

	public BugPlasmaEntity(Level worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(ProjectilesEntityRegister.BUGPLASMA, x, y, z, accelX, accelY, accelZ, worldIn);
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
	public void shoot(double x, double y, double z, float speed, float divergence) {
		super.shoot(x, y, z, speed, divergence);
	}

	@Override
	protected boolean shouldBurn() {
		return false;
	}

	@Override
	public boolean isNoGravity() {
		if (this.isInWater()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		if (!this.level.isClientSide()) {
			this.explode();
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		this.playSound(this.attacksound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));

	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		if (!this.level.isClientSide()) {
			Entity entity = entityHitResult.getEntity();
			Entity entity2 = this.getOwner();
			entity.hurt(DamageSource.mobAttack((LivingEntity) entity2), directHitDamage);
			if (entity2 instanceof LivingEntity) {
				this.doEnchantDamageEffects((LivingEntity) entity2, entity);
			}
		}
		this.playSound(this.attacksound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
	}

	protected void explode() {
		this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F, false,
				Level.ExplosionInteraction.NONE);
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