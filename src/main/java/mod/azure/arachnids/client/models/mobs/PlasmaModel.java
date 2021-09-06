package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.PlasmaEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PlasmaModel extends AnimatedGeoModel<PlasmaEntity> {

	@Override
	public Identifier getAnimationFileLocation(PlasmaEntity animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/plasma.animation.json");
	}

	@Override
	public Identifier getModelLocation(PlasmaEntity object) {
		return new Identifier(ArachnidsMod.MODID, "geo/plasma.geo.json");
	}

	@Override
	public Identifier getTextureLocation(PlasmaEntity object) {
		return new Identifier(ArachnidsMod.MODID, "textures/entity/" + (object.getAttckingState() == 1 ? "plasma_glow"
				: "plasma") + ".png");
	}

}
