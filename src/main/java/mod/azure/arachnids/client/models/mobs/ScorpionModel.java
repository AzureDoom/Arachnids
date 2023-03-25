package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.ScorpionEntity;
import mod.azure.azurelib.model.GeoModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ScorpionModel extends GeoModel<ScorpionEntity> {

	@Override
	public ResourceLocation getAnimationResource(ScorpionEntity animatable) {
		return new ResourceLocation(ArachnidsMod.MODID, "animations/scorpion.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ScorpionEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "geo/scorpion.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ScorpionEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "textures/entity/scorpion.png");
	}

	@Override
	public RenderType getRenderType(ScorpionEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}

}
