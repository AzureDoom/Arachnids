package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.PlasmaEntity;
import mod.azure.azurelib.model.GeoModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class PlasmaModel extends GeoModel<PlasmaEntity> {

	@Override
	public ResourceLocation getAnimationResource(PlasmaEntity animatable) {
		return new ResourceLocation(ArachnidsMod.MODID, "animations/plasma.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(PlasmaEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "geo/plasma.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PlasmaEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "textures/entity/" + (object.getAttckingState() == 1 ? "plasma_glow" : "plasma") + ".png");
	}

	@Override
	public RenderType getRenderType(PlasmaEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}

}
