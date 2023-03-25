package mod.azure.arachnids.client.models.mobs.projectiles;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.projectiles.TacticalOxygenNukeEntity;
import mod.azure.azurelib.model.GeoModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class TONEntityModel extends GeoModel<TacticalOxygenNukeEntity> {
	@Override
	public ResourceLocation getModelResource(TacticalOxygenNukeEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "geo/ton.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TacticalOxygenNukeEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "textures/item/tactical_oxygen_nuke.png");
	}

	@Override
	public ResourceLocation getAnimationResource(TacticalOxygenNukeEntity animatable) {
		return new ResourceLocation(ArachnidsMod.MODID, "animations/ton.animation.json");
	}

	@Override
	public RenderType getRenderType(TacticalOxygenNukeEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
