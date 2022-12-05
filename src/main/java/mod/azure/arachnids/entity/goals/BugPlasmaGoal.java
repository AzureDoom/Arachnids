package mod.azure.arachnids.entity.goals;

import java.util.EnumSet;

import mod.azure.arachnids.entity.BaseBugEntity;
import mod.azure.arachnids.entity.projectiles.BugPlasmaEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

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
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		return this.parentEntity.getTarget() != null;
	}

	@Override
	public boolean canContinueToUse() {
		return (this.canUse() || !this.parentEntity.getNavigation().isDone());
	}

	@Override
	public void start() {
		super.start();
		this.parentEntity.setAggressive(true);
		this.parentEntity.getNavigation().moveTo(this.path, this.speed);
		this.cooldown = 0;
	}

	@Override
	public void stop() {
		super.stop();
		this.parentEntity.setAggressive(false);
		this.parentEntity.setAttackingState(0);
	}

	@Override
	public void tick() {
		LivingEntity livingEntity = this.parentEntity.getTarget();
		if (livingEntity != null) {
			this.parentEntity.getLookControl().setLookAt(livingEntity, 30.0F, 30.0F);
			if (livingEntity.distanceToSqr(this.parentEntity) < 4096.0D
					&& this.parentEntity.hasLineOfSight(livingEntity)
					&& this.parentEntity.getSensing().hasLineOfSight(livingEntity)) {
				Level world = this.parentEntity.level;
				Vec3 vec3d = this.parentEntity.getViewVector(1.0F);
				++this.cooldown;
				double f = livingEntity.getX() - (this.parentEntity.getX() + vec3d.x * 2.0D);
				double g = livingEntity.getY(0.5D) - (0.5D + this.parentEntity.getY(0.5D));
				double h = livingEntity.getZ() - (this.parentEntity.getZ() + vec3d.z * 2.0D);
				BugPlasmaEntity fireballEntity = new BugPlasmaEntity(world, this.parentEntity, f, g, h,
						this.directHitDamage, this.attacksound);
				if (this.cooldown == 15) {
					fireballEntity.absMoveTo(this.parentEntity.getX() + vec3d.x * 2.0D,
							this.parentEntity.getY(0.5D) + 0.5D, parentEntity.getZ() + vec3d.z * 2.0D);
					world.addFreshEntity(fireballEntity);
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