package mod.azure.arachnids.client.render.mobs.projectiles;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.blocks.TONBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class TonBlockRender extends EntityRenderer<TONBlockEntity> {

	protected static final Identifier TEXTURE = new Identifier(ArachnidsMod.MODID,
			"textures/blocks/barrel_explode.png");

	public TonBlockRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn);
		this.shadowRadius = 0.5F;
	}

	public void render(TONBlockEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			VertexConsumerProvider bufferIn, int packedLightIn) {
		matrixStackIn.push();
		matrixStackIn.translate(0.0D, 0.5D, 0.0D);

		matrixStackIn.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
		matrixStackIn.translate(-0.5D, -0.5D, 0.5D);
		matrixStackIn.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
		matrixStackIn.pop();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public Identifier getTexture(TONBlockEntity entity) {
		return TEXTURE;
	}
}