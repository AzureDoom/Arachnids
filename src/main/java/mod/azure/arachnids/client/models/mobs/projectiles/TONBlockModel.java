package mod.azure.arachnids.client.models.mobs.projectiles;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.items.ammo.TONItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TONBlockModel extends AnimatedGeoModel<TONItem> {
	@Override
	public Identifier getModelLocation(TONItem object) {
		return new Identifier(ArachnidsMod.MODID, "geo/ton.geo.json");
	}

	@Override
	public Identifier getTextureLocation(TONItem object) {
		return new Identifier(ArachnidsMod.MODID, "textures/items/tactical_oxygen_nuke.png");
	}

	@Override
	public Identifier getAnimationFileLocation(TONItem animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/ton.animation.json");
	}
}
