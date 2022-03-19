package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.ScorpionEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class ScorpionModel extends AnimatedTickingGeoModel<ScorpionEntity> {

	@Override
	public Identifier getAnimationResource(ScorpionEntity animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/scorpion.animation.json");
	}

	@Override
	public Identifier getModelResource(ScorpionEntity object) {
		return new Identifier(ArachnidsMod.MODID, "geo/scorpion.geo.json");
	}

	@Override
	public Identifier getTextureResource(ScorpionEntity object) {
		return new Identifier(ArachnidsMod.MODID, "textures/entity/scorpion.png");
	}

}
