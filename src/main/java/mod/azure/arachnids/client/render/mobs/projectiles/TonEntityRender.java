package mod.azure.arachnids.client.render.mobs.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.arachnids.client.models.mobs.projectiles.TONEntityModel;
import mod.azure.arachnids.entity.projectiles.TacticalOxygenNukeEntity;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.util.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;

public class TonEntityRender extends GeoEntityRenderer<TacticalOxygenNukeEntity> {

	public TonEntityRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new TONEntityModel());
	}

	@Override
	protected int getBlockLightLevel(TacticalOxygenNukeEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public void preRender(PoseStack poseStack, TacticalOxygenNukeEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

}