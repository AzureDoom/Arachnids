package mod.azure.arachnids.client.models.mobs.projectiles;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.projectiles.MZ90Entity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class MZ90Model extends GeoModel<MZ90Entity> {
	@Override
	public ResourceLocation getModelResource(MZ90Entity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "geo/mz90.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MZ90Entity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "textures/item/mz-90_fragmentation_grenade.png");
	}

	@Override
	public ResourceLocation getAnimationResource(MZ90Entity animatable) {
		return new ResourceLocation(ArachnidsMod.MODID, "animations/mz90.animation.json");
	}

	@Override
	public RenderType getRenderType(MZ90Entity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
