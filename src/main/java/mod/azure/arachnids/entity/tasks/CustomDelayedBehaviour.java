package mod.azure.arachnids.entity.tasks;

import java.util.function.Consumer;

import mod.azure.arachnids.entity.BaseBugEntity;
import mod.azure.arachnids.entity.bugs.HopperEntity;
import mod.azure.arachnids.entity.bugs.WarriorEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

public abstract class CustomDelayedBehaviour<E extends BaseBugEntity> extends ExtendedBehaviour<E> {
	protected final int delayTime;
	protected long delayFinishedAt = 0;
	protected Consumer<E> delayedCallback = entity -> {
	};

	public CustomDelayedBehaviour(int delayTicks) {
		this.delayTime = delayTicks;

		runFor(entity -> Math.max(delayTicks, 60));
	}

	public final CustomDelayedBehaviour<E> whenActivating(Consumer<E> callback) {
		this.delayedCallback = callback;

		return this;
	}

	@Override
	protected final void start(ServerLevel level, E entity, long gameTime) {
		if (this.delayTime > 0) {
			this.delayFinishedAt = gameTime + this.delayTime;
			super.start(level, entity, gameTime);
		} else {
			super.start(level, entity, gameTime);
			doDelayedAction(entity);
		}
		int var = entity.getRandom().nextInt(0, 4);
		if (entity instanceof WarriorEntity warrior)
			if (var == 1)
				warrior.triggerAnim("base_controller", "light_attack");
			else if (var == 2)
				warrior.triggerAnim("base_controller", "normal_attack");
			else
				warrior.triggerAnim("base_controller", "heavy_attack");
		else if (entity instanceof HopperEntity hopper && hopper.getEntityData().get(BaseBugEntity.VARIANT) == 2)
			hopper.triggerAnim("base_controller", "melee");
		else
			entity.triggerAnim("base_controller", "melee");
		entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, this.delayTime, 100, false, false));
	}

	@Override
	protected final void stop(ServerLevel level, E entity, long gameTime) {
		super.stop(level, entity, gameTime);

		this.delayFinishedAt = 0;
	}

	@Override
	protected boolean shouldKeepRunning(E entity) {
		return this.delayFinishedAt >= entity.level().getGameTime();
	}

	@Override
	protected final void tick(ServerLevel level, E entity, long gameTime) {
		super.tick(level, entity, gameTime);
		if (entity.getTarget() != null)
			BehaviorUtils.lookAtEntity(entity, entity.getTarget());
		if (this.delayFinishedAt <= gameTime) {
			doDelayedAction(entity);
			this.delayedCallback.accept(entity);
		}
	}

	protected void doDelayedAction(E entity) {
	}
}
