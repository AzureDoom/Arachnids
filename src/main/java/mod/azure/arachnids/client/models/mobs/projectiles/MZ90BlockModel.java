package mod.azure.arachnids.client.models.mobs.projectiles;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.items.ammo.MZ90Item;
import mod.azure.azurelib.model.GeoModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class MZ90BlockModel extends GeoModel<MZ90Item> {
	@Override
	public ResourceLocation getModelResource(MZ90Item object) {
		return new ResourceLocation(ArachnidsMod.MODID, "geo/mz90.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MZ90Item object) {
		return new ResourceLocation(ArachnidsMod.MODID, "textures/item/mz-90_fragmentation_grenade.png");
	}

	@Override
	public ResourceLocation getAnimationResource(MZ90Item animatable) {
		return new ResourceLocation(ArachnidsMod.MODID, "animations/mz90.animation.json");
	}

	@Override
	public RenderType getRenderType(MZ90Item animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
