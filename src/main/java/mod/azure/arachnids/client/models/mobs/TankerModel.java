package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.TankerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TankerModel extends GeoModel<TankerEntity> {

	@Override
	public ResourceLocation getAnimationResource(TankerEntity animatable) {
		return new ResourceLocation(ArachnidsMod.MODID, "animations/tanker.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TankerEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "geo/tanker.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TankerEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "textures/entity/tanker.png");
	}

	@Override
	public RenderType getRenderType(TankerEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}

}
