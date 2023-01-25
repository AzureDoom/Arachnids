package mod.azure.arachnids.client.models.weapons;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.items.weapons.MAR2Item;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class MAR2Model extends GeoModel<MAR2Item> {
	@Override
	public ResourceLocation getModelResource(MAR2Item object) {
		return new ResourceLocation(ArachnidsMod.MODID, "geo/mar2.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MAR2Item object) {
		return new ResourceLocation(ArachnidsMod.MODID, "textures/item/morita_assault_rifle_2.png");
	}

	@Override
	public ResourceLocation getAnimationResource(MAR2Item animatable) {
		return new ResourceLocation(ArachnidsMod.MODID, "animations/mar2.animation.json");
	}
}
