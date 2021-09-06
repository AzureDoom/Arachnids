package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.ScorpionEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ScorpionModel extends AnimatedGeoModel<ScorpionEntity> {

	@Override
	public Identifier getAnimationFileLocation(ScorpionEntity animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/scorpion.animation.json");
	}

	@Override
	public Identifier getModelLocation(ScorpionEntity object) {
		return new Identifier(ArachnidsMod.MODID, "geo/scorpion.geo.json");
	}

	@Override
	public Identifier getTextureLocation(ScorpionEntity object) {
		return new Identifier(ArachnidsMod.MODID, "textures/entity/scorpion.png");
	}

}
