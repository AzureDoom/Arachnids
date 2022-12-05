package mod.azure.arachnids.client.models.mobs.projectiles;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.projectiles.BugPlasmaEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BugPlasmaModel extends GeoModel<BugPlasmaEntity> {
	@Override
	public ResourceLocation getModelResource(BugPlasmaEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "geo/bugplasma.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BugPlasmaEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "textures/entity/plasma_ball.png");
	}

	@Override
	public ResourceLocation getAnimationResource(BugPlasmaEntity animatable) {
		return new ResourceLocation(ArachnidsMod.MODID, "animations/bugplasma.animation.json");
	}

	@Override
	public RenderType getRenderType(BugPlasmaEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
