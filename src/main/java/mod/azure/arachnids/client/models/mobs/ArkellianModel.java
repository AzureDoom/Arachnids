package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.ArkellianEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class ArkellianModel extends AnimatedTickingGeoModel<ArkellianEntity> {

	@Override
	public Identifier getAnimationFileLocation(ArkellianEntity animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/arkellian.animation.json");
	}

	@Override
	public Identifier getModelLocation(ArkellianEntity object) {
		return new Identifier(ArachnidsMod.MODID, "geo/arkellian.geo.json");
	}

	@Override
	public Identifier getTextureLocation(ArkellianEntity object) {
		return new Identifier(ArachnidsMod.MODID, "textures/entity/arkellian.png");
	}

}
