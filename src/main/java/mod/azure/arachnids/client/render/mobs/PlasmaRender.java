package mod.azure.arachnids.client.render.mobs;

import mod.azure.arachnids.client.models.mobs.PlasmaModel;
import mod.azure.arachnids.entity.bugs.PlasmaEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PlasmaRender extends GeoEntityRenderer<PlasmaEntity> {

	public PlasmaRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new PlasmaModel());
		this.shadowRadius = 6.8F;
	}

	@Override
	public RenderLayer getRenderType(PlasmaEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(PlasmaEntity entityLivingBaseIn) {
		return 0.0F;
	}

}