package mod.azure.arachnids.client.render.weapons;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.client.models.weapons.MAR1Model;
import mod.azure.arachnids.items.weapons.MAR1Item;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.EnchantmentHelper;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class MAR1Render extends GeoItemRenderer<MAR1Item> {
	public MAR1Render() {
		super(new MAR1Model());
	}

	@Override
	public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float alpha) {
		if (bone.getName().equals("scope")) {
			bone.setHidden(
					EnchantmentHelper.get(currentItemStack).containsKey(ArachnidsMod.SNIPERATTACHMENT) ? false : true);
		}
		super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
}