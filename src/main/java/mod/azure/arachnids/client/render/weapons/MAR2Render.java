package mod.azure.arachnids.client.render.weapons;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.client.models.weapons.MAR2Model;
import mod.azure.arachnids.items.weapons.MAR2Item;
import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoItemRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class MAR2Render extends GeoItemRenderer<MAR2Item> {
	public MAR2Render() {
		super(new MAR2Model());
	}

	@Override
	public void renderRecursively(PoseStack poseStack, MAR2Item animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean skipGeoLayers, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (bone.getName().equals("scope"))
			bone.setHidden(EnchantmentHelper.getEnchantments(currentItemStack).containsKey(ArachnidsMod.SNIPERATTACHMENT) ? false : true);
		super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, skipGeoLayers, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}
}