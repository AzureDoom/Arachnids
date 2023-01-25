package mod.azure.arachnids.client.render.mobs.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.arachnids.client.models.mobs.projectiles.MZ90Model;
import mod.azure.arachnids.entity.projectiles.MZ90Entity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.util.RenderUtils;

public class MZ90Render extends GeoEntityRenderer<MZ90Entity> {

	public MZ90Render(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new MZ90Model());
	}

	@Override
	protected int getBlockLightLevel(MZ90Entity entityIn, BlockPos partialTicks) {
		return 15;
	}
	
	@Override
	public void preRender(PoseStack poseStack, MZ90Entity animatable, BakedGeoModel model,
			MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
			int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay,
				red, green, blue, alpha);
	}
	
}