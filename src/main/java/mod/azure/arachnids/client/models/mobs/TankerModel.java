package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.TankerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class TankerModel extends AnimatedTickingGeoModel<TankerEntity> {

	@Override
	public Identifier getAnimationFileLocation(TankerEntity animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/tanker.animation.json");
	}

	@Override
	public Identifier getModelLocation(TankerEntity object) {
		return new Identifier(ArachnidsMod.MODID, "geo/tanker.geo.json");
	}

	@Override
	public Identifier getTextureLocation(TankerEntity object) {
		return new Identifier(ArachnidsMod.MODID, "textures/entity/tanker.png");
	}

}
