package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.BrainEntity;
import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BrainModel extends GeoModel<BrainEntity> {

	@Override
	public ResourceLocation getAnimationResource(BrainEntity animatable) {
		return new ResourceLocation(ArachnidsMod.MODID, "animations/brainbug.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(BrainEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "geo/brainbug.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BrainEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "textures/entity/brainbug.png");
	}

	@Override
	public RenderType getRenderType(BrainEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}

	@Override
	public void setCustomAnimations(BrainEntity animatable, long instanceId, AnimationState<BrainEntity> animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("main");

		if (head != null)
			head.setRotY(animationState.getData(DataTickets.ENTITY_MODEL_DATA).netHeadYaw() * Mth.DEG_TO_RAD);

		super.setCustomAnimations(animatable, instanceId, animationState);
	}

}
