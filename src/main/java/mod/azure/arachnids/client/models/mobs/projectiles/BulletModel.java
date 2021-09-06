package mod.azure.arachnids.client.models.mobs.projectiles;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.projectiles.BulletEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BulletModel extends AnimatedGeoModel<BulletEntity> {
	@Override
	public Identifier getModelLocation(BulletEntity object) {
		return new Identifier(ArachnidsMod.MODID, "geo/bullet.geo.json");
	}

	@Override
	public Identifier getTextureLocation(BulletEntity object) {
		return new Identifier(ArachnidsMod.MODID, "textures/items/bullet.png");
	}

	@Override
	public Identifier getAnimationFileLocation(BulletEntity animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/bullet.animation.json");
	}
}
