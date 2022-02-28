package mod.azure.arachnids.entity.goals;

import java.util.SplittableRandom;

import mod.azure.arachnids.entity.BaseBugEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class BugMeleeGoal extends Goal {
	private final BaseBugEntity entity;
	public int attackTime;
	private double moveSpeedAmp = 1;

	public BugMeleeGoal(BaseBugEntity zombie, double speed) {
		this.entity = zombie;
		this.moveSpeedAmp = speed;
	}

	public boolean canStart() {
		return this.entity.getTarget() != null;
	}

	public boolean shouldContinue() {
		return this.canStart();
	}

	public void start() {
		super.start();
		this.entity.setAttacking(true);
	}

	public void stop() {
		super.stop();
		this.entity.setAttacking(false);
		this.entity.setAttackingState(0);
		this.attackTime = -1;
	}

	public void tick() {
		LivingEntity livingentity = this.entity.getTarget();
		if (livingentity != null) {
			boolean inLineOfSight = this.entity.getVisibilityCache().canSee(livingentity);
			this.attackTime++;
			SplittableRandom random = new SplittableRandom();
			int var = random.nextInt(0, 4);
			this.entity.lookAtEntity(livingentity, 30.0F, 30.0F);
			double d0 = this.entity.squaredDistanceTo(livingentity.getX(), livingentity.getY(), livingentity.getZ());
			double d1 = this.getAttackReachSqr(livingentity);
			if (inLineOfSight) {
				if (this.entity.distanceTo(livingentity) >= 3.0D) {
					this.entity.getNavigation().startMovingTo(livingentity, this.moveSpeedAmp);
					this.attackTime = -5;
				} else {
					if (this.attackTime == 4) {
						this.entity.getNavigation().startMovingTo(livingentity, this.moveSpeedAmp);
						if (d0 <= d1) {
							if (var == 1) {
								this.entity.setAttackingState(1);
								this.entity.tryLightAttack(livingentity);
							}
							if (var == 2) {
								this.entity.setAttackingState(2);
								this.entity.tryAttack(livingentity);
							}
							if (var == 3) {
								this.entity.setAttackingState(3);
								this.entity.tryHeavyAttack(livingentity);
							}
						}
						livingentity.timeUntilRegen = 0;
					}
					if (this.attackTime == 8) {
						this.entity.setAttackingState(0);
					}
					if (this.attackTime == 12) {
						this.attackTime = -15;
					}
				}
			}
		}
	}

	protected double getAttackReachSqr(LivingEntity entity) {
		return (double) (this.entity.getWidth() * 1.5F * this.entity.getWidth() * 1.5F + entity.getWidth());
	}

}