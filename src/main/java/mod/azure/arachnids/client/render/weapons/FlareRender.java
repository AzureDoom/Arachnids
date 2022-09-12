package mod.azure.arachnids.client.render.weapons;

import mod.azure.arachnids.entity.projectiles.FlareEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;

public class FlareRender extends EntityRenderer<FlareEntity> {

  public FlareRender(EntityRendererFactory.Context dispatcher) {
    super(dispatcher);
  }

  public void render(FlareEntity fireworkRocketEntity, float f, float g,
                     MatrixStack matrixStack,
                     VertexConsumerProvider vertexConsumerProvider, int i) {
    matrixStack.push();
    matrixStack.multiply(this.dispatcher.getRotation());
    matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));

    matrixStack.pop();
    super.render(fireworkRocketEntity, f, g, matrixStack,
                 vertexConsumerProvider, i);
  }

  public Identifier getTexture(FlareEntity fireworkRocketEntity) {
    return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
  }

  @Override
  protected int getBlockLight(FlareEntity entity, BlockPos blockPos) {
    return 15;
  }
}
