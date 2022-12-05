package mod.azure.arachnids.client.render.mobs.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.blocks.MZ90BlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MZ90BlockRender extends EntityRenderer<MZ90BlockEntity> {

	protected static final ResourceLocation TEXTURE = new ResourceLocation(ArachnidsMod.MODID,
			"textures/block/barrel_explode.png");

	public MZ90BlockRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn);
		this.shadowRadius = 0.5F;
	}

	public void render(MZ90BlockEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		matrixStackIn.pushPose();
		matrixStackIn.translate(0.0D, 0.5D, 0.0D);

		matrixStackIn.mulPose(Axis.YP.rotationDegrees(-90.0F));
		matrixStackIn.translate(-0.5D, -0.5D, 0.5D);
		matrixStackIn.mulPose(Axis.YP.rotationDegrees(90.0F));
		matrixStackIn.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(MZ90BlockEntity entity) {
		return TEXTURE;
	}
}