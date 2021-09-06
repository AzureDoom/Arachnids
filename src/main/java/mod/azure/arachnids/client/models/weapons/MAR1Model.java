package mod.azure.arachnids.client.models.weapons;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.items.weapons.MAR1Item;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MAR1Model extends AnimatedGeoModel<MAR1Item> {
	@Override
	public Identifier getModelLocation(MAR1Item object) {
		return new Identifier(ArachnidsMod.MODID, "geo/mar1.geo.json");
	}

	@Override
	public Identifier getTextureLocation(MAR1Item object) {
		return new Identifier(ArachnidsMod.MODID, "textures/items/morita_assault_rifle.png");
	}

	@Override
	public Identifier getAnimationFileLocation(MAR1Item animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/mar1.animation.json");
	}
}
