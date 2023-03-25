package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.WarriorEntity;
import mod.azure.azurelib.model.GeoModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class WarriorModel extends GeoModel<WarriorEntity> {

	@Override
	public ResourceLocation getAnimationResource(WarriorEntity animatable) {
		return new ResourceLocation(ArachnidsMod.MODID, "animations/warriorworker.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(WarriorEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "geo/warriorworker.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(WarriorEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "textures/entity/soldier_" + object.getVariant() + ".png");
	}

	@Override
	public RenderType getRenderType(WarriorEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}

}
