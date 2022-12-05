package mod.azure.arachnids.client.models.mobs.projectiles;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.items.ammo.TONItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TONBlockModel extends GeoModel<TONItem> {
	@Override
	public ResourceLocation getModelResource(TONItem object) {
		return new ResourceLocation(ArachnidsMod.MODID, "geo/ton.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TONItem object) {
		return new ResourceLocation(ArachnidsMod.MODID, "textures/item/tactical_oxygen_nuke.png");
	}

	@Override
	public ResourceLocation getAnimationResource(TONItem animatable) {
		return new ResourceLocation(ArachnidsMod.MODID, "animations/ton.animation.json");
	}

	@Override
	public RenderType getRenderType(TONItem animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
