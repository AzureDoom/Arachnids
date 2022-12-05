package mod.azure.arachnids.client.render.weapons;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.projectiles.FlareEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class FlareRender extends EntityRenderer<FlareEntity> {

	protected static final ResourceLocation TEXTURE = new ResourceLocation(ArachnidsMod.MODID,
			"textures/block/barrel_explode.png");

	public FlareRender(EntityRendererProvider.Context dispatcher) {
		super(dispatcher);
	}

	public void render(FlareEntity fireworkRocketEntity, float f, float g, PoseStack matrixStack,
			MultiBufferSource vertexConsumerProvider, int i) {
		matrixStack.pushPose();
		matrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
		matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F));

		matrixStack.popPose();
		super.render(fireworkRocketEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	@Override
	protected int getBlockLightLevel(FlareEntity entity, BlockPos blockPos) {
		return 15;
	}

	@Override
	public ResourceLocation getTextureLocation(FlareEntity var1) {
		return TEXTURE;
	}
}