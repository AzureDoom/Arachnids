package mod.azure.arachnids.items;

import java.util.List;

import mod.azure.arachnids.armor.MIArmorMateral;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class MIArmorItem extends ArmorItem {

	public MIArmorItem(Type slot) {
		super(MIArmorMateral.MIARMOR, slot, new Item.Properties());
	}

	@Override
	public void onCraftedBy(ItemStack stack, Level world, Player player) {
		stack.hasTag();
		if (type.getSlot() == EquipmentSlot.HEAD)
			stack.enchant(Enchantments.FIRE_PROTECTION, 5);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
		tooltip.add(Component.translatable("arachnids.tooltip.miarmor").withStyle(ChatFormatting.ITALIC));
	}

}
