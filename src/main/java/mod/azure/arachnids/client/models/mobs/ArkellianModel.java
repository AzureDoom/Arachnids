package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.ArkellianEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

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
