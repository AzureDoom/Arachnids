package mod.azure.arachnids.entity.goals;

import java.util.EnumSet;

import mod.azure.arachnids.entity.BaseBugEntity;
import mod.azure.arachnids.entity.projectiles.BugPlasmaEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BugPlasmaGoal extends Goal {
	private final BaseBugEntity parentEntity;
	protected int cooldown = 0;
	private float directHitDamage;
	private Path path;
	private final double speed = 1.0D;
	private SoundEvent attacksound;

	public BugPlasmaGoal(BaseBugEntity parentEntity, float directHitDamage, SoundEvent attacksound) {
		this.parentEntity = parentEntity;
		this.attacksound = attacksound;
		this.directHitDamage = directHitDamage;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
	}

	@Override
	public boolean canStart() {
		return this.parentEntity.getTarget() != null;
	}

	@Override
	public boolean shouldContinue() {
		return (this.canStart() || !this.parentEntity.getNavigation().isIdle());
	}

	@Override
	public void start() {
		super.start();
		this.parentEntity.setAttacking(true);
		this.parentEntity.getNavigation().startMovingAlong(this.path, this.speed);
		this.cooldown = 0;
	}

	@Override
	public void stop() {
		super.stop();
		this.parentEntity.setAttacking(false);
		this.parentEntity.setAttackingState(0);
	}

	@Override
	public void tick() {
		LivingEntity livingEntity = this.parentEntity.getTarget();
		if (livingEntity != null) {
			this.parentEntity.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
			if (livingEntity.squaredDistanceTo(this.parentEntity) < 4096.0D && this.parentEntity.canSee(livingEntity)
					&& this.parentEntity.getVisibilityCache().canSee(livingEntity)) {
				World world = this.parentEntity.world;
				Vec3d vec3d = this.parentEntity.getRotationVec(1.0F);
				++this.cooldown;
				double f = livingEntity.getX() - (this.parentEntity.getX() + vec3d.x * 2.0D);
				double g = livingEntity.getBodyY(0.5D) - (0.5D + this.parentEntity.getBodyY(0.5D));
				double h = livingEntity.getZ() - (this.parentEntity.getZ() + vec3d.z * 2.0D);
				BugPlasmaEntity fireballEntity = new BugPlasmaEntity(world, this.parentEntity, f, g, h,
						this.directHitDamage, this.attacksound);
				if (this.cooldown == 15) {
					fireballEntity.updatePosition(this.parentEntity.getX() + vec3d.x * 2.0D,
							this.parentEntity.getBodyY(0.5D) + 0.5D, parentEntity.getZ() + vec3d.z * 2.0D);
					world.spawnEntity(fireballEntity);
				}
				if (this.cooldown == 25) {
					this.parentEntity.setAttackingState(0);
					this.cooldown = -150;
				}
			} else if (this.cooldown > 0) {
				--this.cooldown;
			}
		}
	}
}