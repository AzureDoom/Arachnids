package mod.azure.arachnids.client.models.mobs.projectiles;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.projectiles.TacticalOxygenNukeEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TONEntityModel extends AnimatedGeoModel<TacticalOxygenNukeEntity> {
	@Override
	public Identifier getModelLocation(TacticalOxygenNukeEntity object) {
		return new Identifier(ArachnidsMod.MODID, "geo/ton.geo.json");
	}

	@Override
	public Identifier getTextureLocation(TacticalOxygenNukeEntity object) {
		return new Identifier(ArachnidsMod.MODID, "textures/items/tactical_oxygen_nuke.png");
	}

	@Override
	public Identifier getAnimationFileLocation(TacticalOxygenNukeEntity animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/ton.animation.json");
	}
}
