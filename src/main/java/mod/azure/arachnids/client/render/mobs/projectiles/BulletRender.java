package mod.azure.arachnids.client.render.mobs.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.arachnids.client.models.mobs.projectiles.BulletModel;
import mod.azure.arachnids.entity.projectiles.BulletEntity;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.util.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;

public class BulletRender extends GeoEntityRenderer<BulletEntity> {

	public BulletRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new BulletModel());
	}

	@Override
	protected int getBlockLightLevel(BulletEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public void preRender(PoseStack poseStack, BulletEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.scale(0.0F, 0.0F, 0.0F);
		RenderUtils.faceRotation(poseStack, animatable, partialTick);
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

}