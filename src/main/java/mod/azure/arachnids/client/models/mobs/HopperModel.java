package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.HopperEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HopperModel extends GeoModel<HopperEntity> {

	@Override
	public ResourceLocation getAnimationResource(HopperEntity animatable) {
		return new ResourceLocation(ArachnidsMod.MODID, "animations/hopper.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(HopperEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "geo/hopper.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(HopperEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "textures/entity/hopper_" + object.getVariant() + ".png");
	}

	@Override
	public RenderType getRenderType(HopperEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}

}
