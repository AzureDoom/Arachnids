package mod.azure.arachnids.entity.goals;

import java.util.SplittableRandom;

import mod.azure.arachnids.entity.BaseBugEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class BugMeleeGoal extends MeleeAttackGoal {
	private final BaseBugEntity actor;
	private final double speed;
	public int cooldown;

	public BugMeleeGoal(BaseBugEntity zombie, double speed) {
		super(zombie, speed, false);
		this.actor = zombie;
		this.speed = speed;
	}

	@Override
	public void stop() {
		super.stop();
		this.actor.setAttacking(false);
		this.actor.setAttackingState(0);
	}

	@Override
	protected void attack(LivingEntity livingentity, double squaredDistance) {
		double d0 = this.getSquaredMaxAttackDistance(livingentity);
		if (squaredDistance <= d0 && this.getCooldown() <= 150) {
			this.resetCooldown();
			SplittableRandom random = new SplittableRandom();
			int var = random.nextInt(0, 4);
			if (var == 1) {
				this.actor.setAttackingState(1);
				this.actor.tryLightAttack(livingentity);
			}
			if (var == 2) {
				this.actor.setAttackingState(2);
				this.actor.tryAttack(livingentity);
			}
			if (var == 3) {
				this.actor.setAttackingState(3);
				this.actor.tryHeavyAttack(livingentity);
			}
		}
	}

	@Override
	protected int getMaxCooldown() {
		return 400;
	}

	@Override
	protected void resetCooldown() {
		this.cooldown = -400;
	}

	@Override
	protected double getSquaredMaxAttackDistance(LivingEntity entity) {
		return (double) (this.mob.getWidth() * 1.0F * this.mob.getWidth() * 1.0F + entity.getWidth());
	}

}