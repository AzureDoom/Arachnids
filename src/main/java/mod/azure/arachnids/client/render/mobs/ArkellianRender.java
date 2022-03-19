package mod.azure.arachnids.client.render.mobs;

import mod.azure.arachnids.client.models.mobs.ArkellianModel;
import mod.azure.arachnids.entity.bugs.ArkellianEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ArkellianRender extends GeoEntityRenderer<ArkellianEntity> {

	public ArkellianRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new ArkellianModel());
		this.shadowRadius = 0.7F;
	}

	@Override
	public RenderLayer getRenderType(ArkellianEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}

	@Override
	protected float getDeathMaxRotation(ArkellianEntity entityLivingBaseIn) {
		return 0.0F;
	}

}