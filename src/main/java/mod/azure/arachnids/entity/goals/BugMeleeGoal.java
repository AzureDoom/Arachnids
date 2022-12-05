package mod.azure.arachnids.entity.goals;

import java.util.SplittableRandom;

import mod.azure.arachnids.entity.BaseBugEntity;
import mod.azure.arachnids.entity.bugs.WarriorEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class BugMeleeGoal extends Goal {
	private final BaseBugEntity entity;
	public int attackTime;
	private double moveSpeedAmp = 1;

	public BugMeleeGoal(BaseBugEntity zombie, double speed) {
		this.entity = zombie;
		this.moveSpeedAmp = speed;
	}

	@Override
	public boolean canUse() {
		return this.entity.getTarget() != null;
	}

	@Override
	public boolean canContinueToUse() {
		return this.canUse();
	}

	@Override
	public void start() {
		super.start();
		this.entity.setAggressive(true);
	}

	@Override
	public void stop() {
		super.stop();
		this.entity.setAggressive(false);
		this.entity.setAttackingState(0);
		this.attackTime = -1;
	}

	@Override
	public void tick() {
		LivingEntity livingentity = this.entity.getTarget();
		if (livingentity != null) {
			boolean inLineOfSight = this.entity.getSensing().hasLineOfSight(livingentity);
			this.attackTime++;
			SplittableRandom random = new SplittableRandom();
			int var = random.nextInt(0, 4);
			this.entity.lookAt(livingentity, 30.0F, 30.0F);
			double d0 = this.entity.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
			double d1 = this.getAttackReachSqr(livingentity);
			if (inLineOfSight) {
				if (this.entity.distanceTo(livingentity) >= 3.0D) {
					this.entity.getNavigation().moveTo(livingentity, this.moveSpeedAmp);
					this.attackTime = -5;
				} else {
					this.entity.getLookControl().setLookAt(livingentity.getX(), livingentity.getEyeY(),
							livingentity.getZ());
					if (this.attackTime == 1) {
						this.entity.getNavigation().stop();
						this.entity.setAttackingState(2);
					}
					if (this.attackTime == 9) {
						if (d0 <= d1) {
							if (this.entity instanceof WarriorEntity) {
								if (var == 1) {
									this.entity.setAttackingState(1);
									this.entity.tryLightAttack(livingentity);
								}
								if (var == 2) {
									this.entity.setAttackingState(2);
									this.entity.doHurtTarget(livingentity);
								}
								if (var == 3) {
									this.entity.setAttackingState(3);
									this.entity.tryHeavyAttack(livingentity);
								}
							} else {
								this.entity.setAttackingState(1);
								this.entity.doHurtTarget(livingentity);
							}
						}
					}
					if (this.attackTime == 15) {
						this.attackTime = 0;
						this.entity.setAttackingState(0);
						this.entity.getNavigation().moveTo(livingentity, 1.35);
					}
				}
			}
		}
	}

	protected double getAttackReachSqr(LivingEntity entity) {
		return (double) (this.entity.getBbWidth() * 1.5F * this.entity.getBbWidth() * 1.5F + entity.getBbWidth());
	}

}