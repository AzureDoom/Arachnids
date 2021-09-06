package mod.azure.arachnids.client.render.mobs;

import mod.azure.arachnids.client.models.mobs.WarriorModel;
import mod.azure.arachnids.entity.bugs.WarriorEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WarriorRender extends GeoEntityRenderer<WarriorEntity> {

	public WarriorRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new WarriorModel());
		this.shadowRadius = 1.5F;
	}

	@Override
	public RenderLayer getRenderType(WarriorEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(WarriorEntity entityLivingBaseIn) {
		return 0.0F;
	}
	
	@Override
	protected boolean hasLabel(WarriorEntity entity) {
		return false;
	}
	
	@Override
	public void renderEarly(WarriorEntity animatable, MatrixStack stackIn, float ticks,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red,
				green, blue, partialTicks);
		stackIn.scale(1.25F, 1.25F, 1.25F);
	}

}