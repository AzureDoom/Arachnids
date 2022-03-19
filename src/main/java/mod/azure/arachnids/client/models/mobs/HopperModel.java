package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.HopperEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class HopperModel extends AnimatedTickingGeoModel<HopperEntity> {

	@Override
	public Identifier getAnimationResource(HopperEntity animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/hopper.animation.json");
	}

	@Override
	public Identifier getModelResource(HopperEntity object) {
		return new Identifier(ArachnidsMod.MODID, "geo/hopper.geo.json");
	}

	@Override
	public Identifier getTextureResource(HopperEntity object) {
		return new Identifier(ArachnidsMod.MODID, "textures/entity/hopper_" + object.getVariant() + ".png");
	}

}
