package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.BrainEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BrainModel extends AnimatedGeoModel<BrainEntity> {

	@Override
	public Identifier getAnimationFileLocation(BrainEntity animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/brainbug.animation.json");
	}

	@Override
	public Identifier getModelLocation(BrainEntity object) {
		return new Identifier(ArachnidsMod.MODID, "geo/brainbug.geo.json");
	}

	@Override
	public Identifier getTextureLocation(BrainEntity object) {
		return new Identifier(ArachnidsMod.MODID, "textures/entity/brainbug.png");
	}

}
