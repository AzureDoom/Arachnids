package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.PlasmaEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class PlasmaModel extends AnimatedTickingGeoModel<PlasmaEntity> {

	@Override
	public Identifier getAnimationResource(PlasmaEntity animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/plasma.animation.json");
	}

	@Override
	public Identifier getModelResource(PlasmaEntity object) {
		return new Identifier(ArachnidsMod.MODID, "geo/plasma.geo.json");
	}

	@Override
	public Identifier getTextureResource(PlasmaEntity object) {
		return new Identifier(ArachnidsMod.MODID,
				"textures/entity/" + (object.getAttckingState() == 1 ? "plasma_glow" : "plasma") + ".png");
	}

}
