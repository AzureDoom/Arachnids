package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.HopperEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HopperModel extends AnimatedGeoModel<HopperEntity> {

	@Override
	public Identifier getAnimationFileLocation(HopperEntity animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/hopper.animation.json");
	}

	@Override
	public Identifier getModelLocation(HopperEntity object) {
		return new Identifier(ArachnidsMod.MODID, "geo/hopper.geo.json");
	}

	@Override
	public Identifier getTextureLocation(HopperEntity object) {
		return new Identifier(ArachnidsMod.MODID, "textures/entity/hopper_" + object.getVariant() + ".png");
	}

}
