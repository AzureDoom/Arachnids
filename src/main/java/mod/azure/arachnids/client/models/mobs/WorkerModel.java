package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.WorkerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class WorkerModel extends AnimatedTickingGeoModel<WorkerEntity> {

	@Override
	public Identifier getAnimationResource(WorkerEntity animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/warriorworker.animation.json");
	}

	@Override
	public Identifier getModelResource(WorkerEntity object) {
		return new Identifier(ArachnidsMod.MODID, "geo/warriorworker.geo.json");
	}

	@Override
	public Identifier getTextureResource(WorkerEntity object) {
		return new Identifier(ArachnidsMod.MODID, "textures/entity/worker.png");
	}

}
