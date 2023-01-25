package mod.azure.arachnids.client.render.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.arachnids.client.models.mobs.HopperModel;
import mod.azure.arachnids.entity.bugs.HopperEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.renderer.GeoEntityRenderer;

public class HopperRender extends GeoEntityRenderer<HopperEntity> {

	public HopperRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new HopperModel());
		this.shadowRadius = 1.5F;
	}

	@Override
	protected float getDeathMaxRotation(HopperEntity entityLivingBaseIn) {
		return 0.0F;
	}

	@Override
	public boolean shouldShowName(HopperEntity entity) {
		return false;
	}

	@Override
	public void preRender(PoseStack poseStack, HopperEntity animatable, BakedGeoModel model,
			MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
			int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();
		poseStack.scale(1.25F, 1.25F, 1.25F);
		poseStack.popPose();
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight,
				packedOverlay, red, green, blue, alpha);
	}

}