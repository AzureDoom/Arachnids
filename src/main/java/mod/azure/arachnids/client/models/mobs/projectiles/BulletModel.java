package mod.azure.arachnids.client.models.mobs.projectiles;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.projectiles.BulletEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BulletModel extends AnimatedGeoModel<BulletEntity> {
	@Override
	public Identifier getModelResource(BulletEntity object) {
		return new Identifier(ArachnidsMod.MODID, "geo/bullet.geo.json");
	}

	@Override
	public Identifier getTextureResource(BulletEntity object) {
		return new Identifier(ArachnidsMod.MODID, "textures/items/bullet.png");
	}

	@Override
	public Identifier getAnimationResource(BulletEntity animatable) {
		return new Identifier(ArachnidsMod.MODID, "animations/bullet.animation.json");
	}
}
