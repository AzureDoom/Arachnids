package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.WarriorEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class WarriorModel extends AnimatedTickingGeoModel<WarriorEntity> {

	@Override
	public Identifier getAnimationResource(WarriorEntity animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/warriorworker.animation.json");
	}

	@Override
	public Identifier getModelResource(WarriorEntity object) {
		return new Identifier(ArachnidsMod.MODID, "geo/warriorworker.geo.json");
	}

	@Override
	public Identifier getTextureResource(WarriorEntity object) {
		return new Identifier(ArachnidsMod.MODID, "textures/entity/soldier_" + object.getVariant() + ".png");
	}

}
