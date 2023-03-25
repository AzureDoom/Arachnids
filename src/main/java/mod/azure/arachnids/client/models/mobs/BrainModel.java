package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.BrainEntity;
import mod.azure.azurelib.model.GeoModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class BrainModel extends GeoModel<BrainEntity> {

	@Override
	public ResourceLocation getAnimationResource(BrainEntity animatable) {
		return new ResourceLocation(ArachnidsMod.MODID, "animations/brainbug.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(BrainEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "geo/brainbug.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BrainEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "textures/entity/brainbug.png");
	}

	@Override
	public RenderType getRenderType(BrainEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}

}
