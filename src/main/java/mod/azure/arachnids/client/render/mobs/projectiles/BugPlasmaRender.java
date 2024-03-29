package mod.azure.arachnids.client.render.mobs.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.arachnids.client.models.mobs.projectiles.BugPlasmaModel;
import mod.azure.arachnids.entity.projectiles.BugPlasmaEntity;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.util.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;

public class BugPlasmaRender extends GeoEntityRenderer<BugPlasmaEntity> {

	public BugPlasmaRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new BugPlasmaModel());
	}

	@Override
	protected int getBlockLightLevel(BugPlasmaEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public void preRender(PoseStack poseStack, BugPlasmaEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.scale(3.0F, 3.0F, 3.0F);
		RenderUtils.faceRotation(poseStack, animatable, partialTick);
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

}