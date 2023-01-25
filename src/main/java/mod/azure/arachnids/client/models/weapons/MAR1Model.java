package mod.azure.arachnids.client.models.weapons;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.items.weapons.MAR1Item;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class MAR1Model extends GeoModel<MAR1Item> {
	@Override
	public ResourceLocation getModelResource(MAR1Item object) {
		return new ResourceLocation(ArachnidsMod.MODID, "geo/mar1.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MAR1Item object) {
		return new ResourceLocation(ArachnidsMod.MODID, "textures/item/morita_assault_rifle.png");
	}

	@Override
	public ResourceLocation getAnimationResource(MAR1Item animatable) {
		return new ResourceLocation(ArachnidsMod.MODID, "animations/mar1.animation.json");
	}
}
