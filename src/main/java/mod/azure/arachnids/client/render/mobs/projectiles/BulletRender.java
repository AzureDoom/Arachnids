package mod.azure.arachnids.client.render.mobs.projectiles;

import mod.azure.arachnids.client.models.mobs.projectiles.BulletModel;
import mod.azure.arachnids.entity.projectiles.BulletEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class BulletRender extends GeoProjectilesRenderer<BulletEntity> {

	public BulletRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new BulletModel());
	}

	protected int getBlockLight(BulletEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderLayer getRenderType(BulletEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}

	@Override
	public void renderEarly(BulletEntity animatable, MatrixStack stackIn, float ticks,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn,
				red, green, blue, partialTicks);
		stackIn.scale(animatable.age > 2 ? 0.5F : 0.0F, animatable.age > 2 ? 0.5F : 0.0F,
				animatable.age > 2 ? 0.5F : 0.0F);
	}

}