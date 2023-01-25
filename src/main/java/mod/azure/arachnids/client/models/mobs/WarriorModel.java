package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.WarriorEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;

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

	@Override
	public void setCustomAnimations(WarriorEntity animatable, long instanceId,
			AnimationState<WarriorEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);
	}

}
