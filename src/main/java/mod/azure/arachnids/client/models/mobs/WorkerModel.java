package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.WorkerEntity;
import mod.azure.azurelib.model.GeoModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class WorkerModel extends GeoModel<WorkerEntity> {

	@Override
	public ResourceLocation getAnimationResource(WorkerEntity animatable) {
		return new ResourceLocation(ArachnidsMod.MODID, "animations/warriorworker.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(WorkerEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "geo/warriorworker.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(WorkerEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "textures/entity/worker.png");
	}

	@Override
	public RenderType getRenderType(WorkerEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}

}
