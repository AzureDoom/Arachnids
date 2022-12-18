package mod.azure.arachnids.client.render.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.arachnids.client.models.mobs.WorkerModel;
import mod.azure.arachnids.entity.bugs.WorkerEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WorkerRender extends GeoEntityRenderer<WorkerEntity> {

	public WorkerRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new WorkerModel());
		this.shadowRadius = 1.5F;
	}

	@Override
	protected float getDeathMaxRotation(WorkerEntity entityLivingBaseIn) {
		return 0.0F;
	}

	@Override
	public void preRender(PoseStack poseStack, WorkerEntity animatable, BakedGeoModel model,
			MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
			int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();
		poseStack.scale(1.25F, 1.25F, 1.25F);
		poseStack.popPose();
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight,
				packedOverlay, red, green, blue, alpha);
	}

}