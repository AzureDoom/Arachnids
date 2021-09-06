package mod.azure.arachnids.client.models.mobs.projectiles;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.items.ammo.MZ90Item;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MZ90BlockModel extends AnimatedGeoModel<MZ90Item> {
	@Override
	public Identifier getModelLocation(MZ90Item object) {
		return new Identifier(ArachnidsMod.MODID, "geo/mz90.geo.json");
	}

	@Override
	public Identifier getTextureLocation(MZ90Item object) {
		return new Identifier(ArachnidsMod.MODID, "textures/items/mz-90_fragmentation_grenade.png");
	}

	@Override
	public Identifier getAnimationFileLocation(MZ90Item animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/mz90.animation.json");
	}
}
