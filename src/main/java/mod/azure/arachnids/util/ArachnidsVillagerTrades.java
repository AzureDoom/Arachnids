package mod.azure.arachnids.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;

public class ArachnidsVillagerTrades {

	public static void addTrades() {
		List<VillagerTrades.ItemListing> lvl1weapon_trades = new ArrayList<>(
				Arrays.asList(VillagerTrades.TRADES.get(VillagerProfession.WEAPONSMITH).get(1)));
		lvl1weapon_trades
				.add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.IRON_NUGGET, 1, 1, ArachnidsItems.BULLETS, 6, 120, 5));
		VillagerTrades.ItemListing[] resultlvl1 = new VillagerTrades.ItemListing[] {};
		VillagerTrades.TRADES.get(VillagerProfession.WEAPONSMITH).put(1,
				lvl1weapon_trades.toArray(resultlvl1));

		List<VillagerTrades.ItemListing> lvl2weapon_trades = new ArrayList<>(
				Arrays.asList(VillagerTrades.TRADES.get(VillagerProfession.WEAPONSMITH).get(2)));
		lvl2weapon_trades
				.add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.IRON_INGOT, 2, 4, ArachnidsItems.MAR1, 1, 120, 15));
		lvl2weapon_trades
				.add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.GUNPOWDER, 2, 4, ArachnidsItems.MZ90, 6, 120, 15));
		VillagerTrades.ItemListing[] resultlvl2 = new VillagerTrades.ItemListing[] {};
		VillagerTrades.TRADES.get(VillagerProfession.WEAPONSMITH).put(2,
				lvl2weapon_trades.toArray(resultlvl2));

		List<VillagerTrades.ItemListing> lvl3weapon_trades = new ArrayList<>(
				Arrays.asList(VillagerTrades.TRADES.get(VillagerProfession.WEAPONSMITH).get(3)));
		lvl3weapon_trades
				.add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.IRON_BARS, 4, 6, ArachnidsItems.MAR2, 1, 120, 25));
		VillagerTrades.ItemListing[] resultlvl3 = new VillagerTrades.ItemListing[] {};
		VillagerTrades.TRADES.get(VillagerProfession.WEAPONSMITH).put(3,
				lvl3weapon_trades.toArray(resultlvl3));

		List<VillagerTrades.ItemListing> lvl4weapon_trades = new ArrayList<>(
				Arrays.asList(VillagerTrades.TRADES.get(VillagerProfession.WEAPONSMITH).get(4)));
		lvl4weapon_trades
				.add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.IRON_BLOCK, 15, 9, ArachnidsItems.M55, 1, 120, 35));
		lvl4weapon_trades.add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.TNT, 7, 4, ArachnidsItems.TON, 6, 120, 35));
		VillagerTrades.ItemListing[] resultlvl4 = new VillagerTrades.ItemListing[] {};
		VillagerTrades.TRADES.get(VillagerProfession.WEAPONSMITH).put(4,
				lvl4weapon_trades.toArray(resultlvl4));

		List<VillagerTrades.ItemListing> lvl1leather_trades = new ArrayList<>(
				Arrays.asList(VillagerTrades.TRADES.get(VillagerProfession.LEATHERWORKER).get(1)));
		lvl1leather_trades.add(
				new VillagerTrades.ItemsAndEmeraldsToItems(Items.LEATHER_HELMET, 4, 1, ArachnidsItems.MI_HELMET, 1, 120, 5));
		lvl1leather_trades.add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.LEATHER_CHESTPLATE, 1, 1,
				ArachnidsItems.MI_CHESTPLATE, 1, 120, 5));
		lvl1leather_trades.add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.LEATHER_LEGGINGS, 1, 1,
				ArachnidsItems.MI_LEGGINGS, 1, 120, 5));
		lvl1leather_trades
				.add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.LEATHER_BOOTS, 1, 1, ArachnidsItems.MI_BOOTS, 1, 120, 5));
		VillagerTrades.ItemListing[] resultleather = new VillagerTrades.ItemListing[] {};
		VillagerTrades.TRADES.get(VillagerProfession.LEATHERWORKER).put(1,
				lvl1leather_trades.toArray(resultleather));
	}

}
