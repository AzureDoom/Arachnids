package mod.azure.arachnids.entity.goals;

import java.util.SplittableRandom;

import mod.azure.arachnids.entity.BaseBugEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class BugMeleeGoal extends MeleeAttackGoal {
	private final BaseBugEntity actor;
	private int updateCountdownTicks;
	private final double speed;
	private int ticksUntilNextAttack;
	public int cooldown;
	private double targetX;
	private double targetY;
	private double targetZ;
	private final boolean pauseWhenMobIdle;

	public BugMeleeGoal(BaseBugEntity zombie, double speed) {
		super(zombie, speed, false);
		this.actor = zombie;
		this.speed = speed;
		this.pauseWhenMobIdle = false;
	}

	public void start() {
		super.start();
		this.updateCountdownTicks = 0;
		this.cooldown = 0;
	}

	public void stop() {
		super.stop();
		this.actor.setAttacking(false);
		this.actor.setAttackingState(0);
	}

	@Override
	public boolean shouldContinue() {
		return super.shouldContinue();
	}

	public void tick() {
		LivingEntity livingentity = this.actor.getTarget();
		if (livingentity != null) {
			this.mob.getLookControl().lookAt(livingentity, 30.0F, 30.0F);
			double d0 = this.mob.squaredDistanceTo(livingentity.getX(), livingentity.getY(), livingentity.getZ());
			this.updateCountdownTicks = Math.max(this.updateCountdownTicks - 1, 0);
			if ((this.pauseWhenMobIdle || this.mob.getVisibilityCache().canSee(livingentity))
					&& this.ticksUntilNextAttack <= 0
					&& (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D
							|| livingentity.squaredDistanceTo(this.targetX, this.targetY, this.targetZ) >= 1.0D
							|| this.mob.getRandom().nextFloat() < 0.05F)) {
				this.targetX = livingentity.getX();
				this.targetY = livingentity.getY();
				this.targetZ = livingentity.getZ();
				this.updateCountdownTicks = 4 + this.mob.getRandom().nextInt(7);
				if (d0 > 1024.0D) {
					this.updateCountdownTicks += 10;
				} else if (d0 > 256.0D) {
					this.updateCountdownTicks += 5;
				}

				if (!this.mob.getNavigation().startMovingTo(livingentity, this.speed)) {
					this.updateCountdownTicks += 15;
				}
			}
			this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 0, 0);
			this.attack(livingentity, d0);
		}
	}

	@Override
	protected void attack(LivingEntity livingentity, double squaredDistance) {
		double d0 = this.getSquaredMaxAttackDistance(livingentity);
		if (squaredDistance <= d0 && this.getCooldown() <= 10) {
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
		return 150;
	}

	@Override
	protected void resetCooldown() {
		this.cooldown = 0;
	}

	@Override
	protected double getSquaredMaxAttackDistance(LivingEntity entity) {
		return (double) (this.mob.getWidth() * 1.0F * this.mob.getWidth() * 1.0F + entity.getWidth());
	}

}