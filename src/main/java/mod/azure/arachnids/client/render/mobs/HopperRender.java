package mod.azure.arachnids.client.render.mobs;

import mod.azure.arachnids.client.models.mobs.HopperModel;
import mod.azure.arachnids.entity.bugs.HopperEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class HopperRender extends GeoEntityRenderer<HopperEntity> {

	public HopperRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new HopperModel());
		this.shadowRadius = 1.5F;
	}

	@Override
	public RenderLayer getRenderType(HopperEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}

	@Override
	protected float getDeathMaxRotation(HopperEntity entityLivingBaseIn) {
		return 0.0F;
	}

	@Override
	protected boolean hasLabel(HopperEntity entity) {
		return false;
	}

	@Override
	public void renderEarly(HopperEntity animatable, MatrixStack stackIn, float ticks,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn,
				red, green, blue, partialTicks);
		stackIn.scale(1.25F, 1.25F, 1.25F);
	}

}