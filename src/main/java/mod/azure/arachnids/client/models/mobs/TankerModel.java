package mod.azure.arachnids.client.models.mobs;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.bugs.TankerEntity;
import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class TankerModel extends GeoModel<TankerEntity> {

	@Override
	public ResourceLocation getAnimationResource(TankerEntity animatable) {
		return new ResourceLocation(ArachnidsMod.MODID, "animations/tanker.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TankerEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "geo/tanker.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TankerEntity object) {
		return new ResourceLocation(ArachnidsMod.MODID, "textures/entity/tanker.png");
	}

	@Override
	public RenderType getRenderType(TankerEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}

	@Override
	public void setCustomAnimations(TankerEntity animatable, long instanceId, AnimationState<TankerEntity> animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("main");

		if (head != null)
			head.setRotY(animationState.getData(DataTickets.ENTITY_MODEL_DATA).netHeadYaw() * Mth.DEG_TO_RAD);

		super.setCustomAnimations(animatable, instanceId, animationState);
	}

}
