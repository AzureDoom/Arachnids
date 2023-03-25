package mod.azure.arachnids.client.render.mobs.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.projectiles.FlameFiring;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FlameFiringRender extends EntityRenderer<FlameFiring> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(ArachnidsMod.MODID, "textures/item/bullet.png");

	public FlameFiringRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public ResourceLocation getTextureLocation(FlameFiring entity) {
		return TEXTURE;
	}

	@Override
	public void render(FlameFiring persistentProjectileEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
		super.render(persistentProjectileEntity, f, g, matrixStack, vertexConsumerProvider, i);
		matrixStack.pushPose();
		matrixStack.scale(0, 0, 0);
		matrixStack.popPose();
	}

}