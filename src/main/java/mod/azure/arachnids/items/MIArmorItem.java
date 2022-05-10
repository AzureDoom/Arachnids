package mod.azure.arachnids.items;

import java.util.List;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.armor.MIArmorMateral;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class MIArmorItem extends ArmorItem {

	public MIArmorItem(EquipmentSlot slot) {
		super(MIArmorMateral.MIARMOR, slot, new Item.Settings().group(ArachnidsMod.ArachnidsItemGroup));
	}

	@Override
	public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
		ItemStack stack = new ItemStack(this);
		stack.hasNbt();
		if (slot == EquipmentSlot.HEAD)
			stack.addEnchantment(Enchantments.PROTECTION, 5);
		if ((group == ArachnidsMod.ArachnidsItemGroup) || (group == ItemGroup.SEARCH)) {
			stacks.add(stack);
		}
	}

	@Override
	public void onCraft(ItemStack stack, World world, PlayerEntity player) {
		stack.hasNbt();
		if (slot == EquipmentSlot.HEAD)
			stack.addEnchantment(Enchantments.PROTECTION, 5);
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return false;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("arachnids.tooltip.miarmor").formatted(Formatting.ITALIC));
	}

}
