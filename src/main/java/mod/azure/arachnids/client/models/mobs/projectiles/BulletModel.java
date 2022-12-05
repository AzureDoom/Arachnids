package mod.azure.arachnids.client.models.mobs.projectiles;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.projectiles.BulletEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BulletModel extends GeoModel<BulletEntity> {
	@Override
	public ResourceLocation getModelResource(BulletEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "geo/bullet.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BulletEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "textures/item/bullet.png");
	}

	@Override
	public ResourceLocation getAnimationResource(BulletEntity animatable) {
		return new ResourceLocation(ArachnidsMod.MODID, "animations/bullet.animation.json");
	}

	@Override
	public RenderType getRenderType(BulletEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
