package mod.azure.arachnids.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.items.weapons.BaseGunItem;
import mod.azure.arachnids.items.weapons.M55Item;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

@Mixin(value = AnvilMenu.class)
public abstract class AnvilScreenHandlerMixin extends ItemCombinerMenu {

	public AnvilScreenHandlerMixin(MenuType<?> type, int syncId, Inventory playerInventory,
			ContainerLevelAccess context) {
		super(type, syncId, playerInventory, context);
	}

	@Inject(method = "createResult", at = @At(value = "RETURN"))
	private void blockEnchantments(CallbackInfo ci) {
		ItemStack leftStack = this.inputSlots.getItem(0).copy();
		ItemStack rightStack = this.inputSlots.getItem(1).copy();
		if ((leftStack.getItem() instanceof BaseGunItem || leftStack.getItem() instanceof M55Item)
				&& (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MENDING, rightStack) > 0
						|| EnchantmentHelper.getItemEnchantmentLevel(Enchantments.UNBREAKING, rightStack) > 0
						|| EnchantmentHelper.getEnchantments(rightStack).containsKey(Enchantments.MENDING)
						|| EnchantmentHelper.getEnchantments(rightStack).containsKey(Enchantments.UNBREAKING))) {
			ItemStack repaired = ItemStack.EMPTY;
			this.resultSlots.setItem(0, repaired);
			this.broadcastChanges();
		}
		if (!(leftStack.getItem() instanceof BaseGunItem)
				&& (EnchantmentHelper.getItemEnchantmentLevel(ArachnidsMod.FLAREATTACHMENT, rightStack) > 0
						|| EnchantmentHelper.getItemEnchantmentLevel(ArachnidsMod.GRENADEATTACHMENT, rightStack) > 0
						|| EnchantmentHelper.getItemEnchantmentLevel(ArachnidsMod.SNIPERATTACHMENT, rightStack) > 0
						|| EnchantmentHelper.getEnchantments(rightStack).containsKey(ArachnidsMod.FLAREATTACHMENT)
						|| EnchantmentHelper.getEnchantments(rightStack).containsKey(ArachnidsMod.GRENADEATTACHMENT)
						|| EnchantmentHelper.getEnchantments(rightStack).containsKey(ArachnidsMod.SNIPERATTACHMENT))) {
			ItemStack repaired = ItemStack.EMPTY;
			this.resultSlots.setItem(0, repaired);
			this.broadcastChanges();
		}
	}
}