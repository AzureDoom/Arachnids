package mod.azure.arachnids.enchantment;

import mod.azure.arachnids.util.ArachnidsItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SnipingEnchantment extends Enchantment {

	public SnipingEnchantment(Rarity weight, EquipmentSlot... slotTypes) {
		super(weight, EnchantmentCategory.BREAKABLE, slotTypes);
	}

	@Override
	public int getMaxCost(int level) {
		return 1;
	}

	@Override
	public int getMinCost(int level) {
		return 1;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public boolean isTreasureOnly() {
		return true;
	}

	@Override
	public boolean isTradeable() {
		return true;
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		return (stack.is(ArachnidsItems.MAR1) || stack.is(ArachnidsItems.MAR2)) ? true : false;
	}

	@Override
	public boolean isDiscoverable() {
		return false;
	}

}
