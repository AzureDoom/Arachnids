package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.BrainEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class BrainModel extends AnimatedTickingGeoModel<BrainEntity> {

	@Override
	public Identifier getAnimationResource(BrainEntity animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/brainbug.animation.json");
	}

	@Override
	public Identifier getModelResource(BrainEntity object) {
		return new Identifier(ArachnidsMod.MODID, "geo/brainbug.geo.json");
	}

	@Override
	public Identifier getTextureResource(BrainEntity object) {
		return new Identifier(ArachnidsMod.MODID, "textures/entity/brainbug.png");
	}

}
