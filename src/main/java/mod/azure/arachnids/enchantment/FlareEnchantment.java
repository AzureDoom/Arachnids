package mod.azure.arachnids.enchantment;

import mod.azure.arachnids.items.weapons.MAR1Item;
import mod.azure.arachnids.items.weapons.MAR2Item;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class FlareEnchantment extends Enchantment {

	public FlareEnchantment(Rarity weight, EquipmentSlot... slotTypes) {
		super(weight, EnchantmentTarget.BREAKABLE, slotTypes);
	}

	@Override
	public int getMaxPower(int level) {
		return 1;
	}

	@Override
	public int getMinPower(int level) {
		return 1;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public boolean isTreasure() {
		return true;
	}

	@Override
	public boolean isAvailableForEnchantedBookOffer() {
		return true;
	}

	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		return (stack.getItem() instanceof MAR1Item || stack.getItem() instanceof MAR2Item) ? true : false;
	}

	public boolean isAvailableForRandomSelection() {
		return true;
	}

}
