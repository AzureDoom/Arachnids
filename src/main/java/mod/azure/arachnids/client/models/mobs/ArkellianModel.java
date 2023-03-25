package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.ArkellianEntity;
import mod.azure.azurelib.model.GeoModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ArkellianModel extends GeoModel<ArkellianEntity> {

	@Override
	public ResourceLocation getAnimationResource(ArkellianEntity animatable) {
		return new ResourceLocation(ArachnidsMod.MODID, "animations/arkellian.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ArkellianEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "geo/arkellian.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ArkellianEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "textures/entity/arkellian.png");
	}

	@Override
	public RenderType getRenderType(ArkellianEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}

}
