package mod.azure.arachnids.client.models.mobs.projectiles;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.projectiles.BugPlasmaEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BugPlasmaModel extends AnimatedGeoModel<BugPlasmaEntity> {
	@Override
	public Identifier getModelResource(BugPlasmaEntity object) {
		return new Identifier(ArachnidsMod.MODID, "geo/bugplasma.geo.json");
	}

	@Override
	public Identifier getTextureResource(BugPlasmaEntity object) {
		return new Identifier(ArachnidsMod.MODID, "textures/entity/plasma_ball.png");
	}

	@Override
	public Identifier getAnimationResource(BugPlasmaEntity animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/bugplasma.animation.json");
	}
}
