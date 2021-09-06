package mod.azure.arachnids.client.models.mobs.projectiles;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.projectiles.MZ90Entity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MZ90Model extends AnimatedGeoModel<MZ90Entity> {
	@Override
	public Identifier getModelLocation(MZ90Entity object) {
		return new Identifier(ArachnidsMod.MODID, "geo/mz90.geo.json");
	}

	@Override
	public Identifier getTextureLocation(MZ90Entity object) {
		return new Identifier(ArachnidsMod.MODID, "textures/items/mz-90_fragmentation_grenade.png");
	}

	@Override
	public Identifier getAnimationFileLocation(MZ90Entity animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/mz90.animation.json");
	}
}
