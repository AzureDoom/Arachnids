package mod.azure.arachnids.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.util.ArachnidsItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

@Mixin(value = AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

	public AnvilScreenHandlerMixin(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory,
			ScreenHandlerContext context) {
		super(type, syncId, playerInventory, context);
	}

	@Inject(method = "updateResult", at = @At(value = "RETURN"))
	private void blockEnchantments(CallbackInfo ci) {
		ItemStack leftStack = this.input.getStack(0).copy();
		ItemStack rightStack = this.input.getStack(1).copy();
		if ((leftStack.isOf(ArachnidsItems.MAR1) || leftStack.isOf(ArachnidsItems.MAR2))
				&& (EnchantmentHelper.getLevel(Enchantments.MENDING, rightStack) > 0
						|| EnchantmentHelper.getLevel(Enchantments.UNBREAKING, rightStack) > 0
						|| EnchantmentHelper.get(rightStack).containsKey(Enchantments.MENDING)
						|| EnchantmentHelper.get(rightStack).containsKey(Enchantments.UNBREAKING))) {
			ItemStack repaired = ItemStack.EMPTY;
			this.output.setStack(0, repaired);
			this.sendContentUpdates();
		}
		if (!(leftStack.isOf(ArachnidsItems.MAR1) || leftStack.isOf(ArachnidsItems.MAR2))
				&& (EnchantmentHelper.getLevel(ArachnidsMod.FLAREATTACHMENT, rightStack) > 0
						|| EnchantmentHelper.getLevel(ArachnidsMod.GRENADEATTACHMENT, rightStack) > 0
						|| EnchantmentHelper.getLevel(ArachnidsMod.SNIPERATTACHMENT, rightStack) > 0
						|| EnchantmentHelper.get(rightStack).containsKey(ArachnidsMod.FLAREATTACHMENT)
						|| EnchantmentHelper.get(rightStack).containsKey(ArachnidsMod.GRENADEATTACHMENT)
						|| EnchantmentHelper.get(rightStack).containsKey(ArachnidsMod.SNIPERATTACHMENT))) {
			ItemStack repaired = ItemStack.EMPTY;
			this.output.setStack(0, repaired);
			this.sendContentUpdates();
		}
	}
}