package mod.azure.arachnids.client.render.mobs;

import mod.azure.arachnids.client.models.mobs.ScorpionModel;
import mod.azure.arachnids.entity.bugs.ScorpionEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ScorpionRender extends GeoEntityRenderer<ScorpionEntity> {

	public ScorpionRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new ScorpionModel());
		this.shadowRadius = 6.8F;
	}

	@Override
	public RenderLayer getRenderType(ScorpionEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}

	@Override
	protected float getDeathMaxRotation(ScorpionEntity entityLivingBaseIn) {
		return 0.0F;
	}

}