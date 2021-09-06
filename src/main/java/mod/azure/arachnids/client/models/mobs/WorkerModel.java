package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.WorkerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WorkerModel extends AnimatedGeoModel<WorkerEntity> {

	@Override
	public Identifier getAnimationFileLocation(WorkerEntity animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/warriorworker.animation.json");
	}

	@Override
	public Identifier getModelLocation(WorkerEntity object) {
		return new Identifier(ArachnidsMod.MODID, "geo/warriorworker.geo.json");
	}

	@Override
	public Identifier getTextureLocation(WorkerEntity object) {
		return new Identifier(ArachnidsMod.MODID, "textures/entity/worker.png");
	}

}
