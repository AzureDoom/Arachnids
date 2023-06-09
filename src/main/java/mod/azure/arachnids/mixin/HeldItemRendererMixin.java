package mod.azure.arachnids.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.arachnids.items.weapons.BaseGunItemExtended;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.ItemStack;

@Mixin(value = ItemInHandRenderer.class)
public abstract class HeldItemRendererMixin {

	@Shadow
	@Final
	private final Minecraft minecraft;
	@Shadow
	private float mainHandHeight;
	@Shadow
	private float offHandHeight;
	@Shadow
	private ItemStack mainHandItem;
	@Shadow
	private ItemStack offHandItem;

	public HeldItemRendererMixin(Minecraft client) {
		this.minecraft = client;
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void fguns$cancelAnimation(CallbackInfo ci) {
		var clientPlayerEntity = this.minecraft.player;
		var itemStack = clientPlayerEntity.getMainHandItem();
		var itemStack2 = clientPlayerEntity.getOffhandItem();
		if (this.mainHandItem.getItem() instanceof BaseGunItemExtended && itemStack.getItem() instanceof BaseGunItemExtended && ItemStack.isSameItem(mainHandItem, itemStack)) {
			this.mainHandHeight = 1;
			this.mainHandItem = itemStack;
		}
		if (this.offHandItem.getItem() instanceof BaseGunItemExtended && itemStack2.getItem() instanceof BaseGunItemExtended && ItemStack.isSameItem(offHandItem, itemStack2)) {
			this.offHandHeight = 1;
			this.offHandItem = itemStack2;
		}
	}
}
