package mod.azure.arachnids.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.client.ArachnidsClientInit;
import mod.azure.arachnids.items.weapons.MAR1Item;
import mod.azure.arachnids.items.weapons.MAR2Item;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

@Mixin(Gui.class)
public abstract class SniperMixin extends GuiComponent {

	private static final ResourceLocation SNIPER = new ResourceLocation(ArachnidsMod.MODID, "textures/gui/scope.png");
	@Shadow
	private final Minecraft minecraft;
	@Shadow
	private int screenWidth;
	@Shadow
	private int screenHeight;
	private boolean scoped = true;

	public SniperMixin(Minecraft client) {
		this.minecraft = client;
	}

	@Inject(at = @At("TAIL"), method = "render")
	private void render(CallbackInfo info) {
		ItemStack itemStack = this.minecraft.player.getInventory().getSelected();
		if (this.minecraft.options.getCameraType().isFirstPerson()
				&& (itemStack.getItem() instanceof MAR1Item || itemStack.getItem() instanceof MAR2Item)
				&& EnchantmentHelper.getItemEnchantmentLevel(ArachnidsMod.SNIPERATTACHMENT, itemStack) > 0) {
			if (ArachnidsClientInit.scope.isDown()) {
				if (this.scoped == true) {
					this.scoped = false;
				}
				this.renderSniperOverlay();
			} else {
				if (!this.scoped) {
					this.scoped = true;
				}
			}
		}
	}

	private void renderSniperOverlay() {
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, SNIPER);
		Tesselator tessellator = Tesselator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferBuilder.vertex(0.0D, (double) this.screenHeight, -90.0D).uv(0.0F, 1.0F).endVertex();
		bufferBuilder.vertex((double) this.screenWidth, (double) this.screenHeight, -90.0D).uv(1.0F, 1.0F).endVertex();
		bufferBuilder.vertex((double) this.screenWidth, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
		bufferBuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
		tessellator.end();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
