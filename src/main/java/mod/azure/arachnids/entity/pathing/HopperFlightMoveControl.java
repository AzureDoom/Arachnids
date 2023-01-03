package mod.azure.arachnids.entity.pathing;

import mod.azure.arachnids.entity.bugs.HopperEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class HopperFlightMoveControl extends MoveControl {
	private int courseChangeCooldown;
	protected final HopperEntity entity;

	public HopperFlightMoveControl(HopperEntity entity) {
		super(entity);
		this.entity = entity;
	}

	public void tick() {
		if (this.operation == MoveControl.Operation.MOVE_TO) {
			if (this.courseChangeCooldown-- <= 0) {
				this.courseChangeCooldown += this.entity.getRandom().nextInt(5) + 2;
				Vec3 vector3d = new Vec3(this.wantedX - this.entity.getX(), this.wantedY - this.entity.getY(),
						this.wantedZ - this.entity.getZ());
				double d0 = vector3d.length();
				vector3d = vector3d.normalize();
				if (this.canReach(vector3d, Mth.ceil(d0))) {
					this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(vector3d.scale(0.1D)));
				} else {
					this.operation = MoveControl.Operation.WAIT;
				}
			}
		}
	}

	private boolean canReach(Vec3 direction, int steps) {
		AABB axisalignedbb = this.mob.getBoundingBox();
		for (int i = 1; i < steps; ++i) {
			axisalignedbb = axisalignedbb.move(direction);
			if (!this.mob.level.noCollision(this.entity, axisalignedbb)) {
				return false;
			}
		}
		return true;
	}
}
