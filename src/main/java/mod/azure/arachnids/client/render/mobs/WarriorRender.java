package mod.azure.arachnids.client.render.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.arachnids.client.models.mobs.WarriorModel;
import mod.azure.arachnids.entity.bugs.WarriorEntity;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class WarriorRender extends GeoEntityRenderer<WarriorEntity> {

	public WarriorRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new WarriorModel());
		this.shadowRadius = 1.5F;
	}

	@Override
	protected float getDeathMaxRotation(WarriorEntity entityLivingBaseIn) {
		return 0.0F;
	}

	@Override
	public boolean shouldShowName(WarriorEntity entity) {
		return false;
	}

	@Override
	public void preRender(PoseStack poseStack, WarriorEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();
		poseStack.scale(1.25F, 1.25F, 1.25F);
		poseStack.popPose();
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

}