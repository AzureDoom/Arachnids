package mod.azure.arachnids.client.render.mobs.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.arachnids.client.models.mobs.projectiles.BulletModel;
import mod.azure.arachnids.entity.projectiles.BulletEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BulletRender extends GeoEntityRenderer<BulletEntity> {

	public BulletRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new BulletModel());
	}

	@Override
	protected int getBlockLightLevel(BulletEntity entityIn, BlockPos partialTicks) {
		return 15;
	}
	
	@Override
	public void preRender(PoseStack poseStack, BulletEntity animatable, BakedGeoModel model,
			MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight,
			int packedOverlay, float red, float green, float blue, float alpha) {
		super.preRender(poseStack, animatable, model, bufferSource, buffer, partialTick, packedLight, packedOverlay, red, green,
				blue, alpha);
		poseStack.scale(animatable.tickCount > 2 ? 0.5F : 0.0F, animatable.tickCount > 2 ? 0.5F : 0.0F,
				animatable.tickCount > 2 ? 0.5F : 0.0F);
	}

}