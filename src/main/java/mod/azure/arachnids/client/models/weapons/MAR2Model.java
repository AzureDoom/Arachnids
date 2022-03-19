package mod.azure.arachnids.client.models.weapons;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.items.weapons.MAR2Item;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MAR2Model extends AnimatedGeoModel<MAR2Item> {
	@Override
	public Identifier getModelResource(MAR2Item object) {
		return new Identifier(ArachnidsMod.MODID, "geo/mar2.geo.json");
	}

	@Override
	public Identifier getTextureResource(MAR2Item object) {
		return new Identifier(ArachnidsMod.MODID, "textures/items/morita_assault_rifle_2.png");
	}

	@Override
	public Identifier getAnimationResource(MAR2Item animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/mar2.animation.json");
	}
}
