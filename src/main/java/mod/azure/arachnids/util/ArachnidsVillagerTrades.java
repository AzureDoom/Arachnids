package mod.azure.arachnids.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.Items;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;

public class ArachnidsVillagerTrades {

	public static void addTrades() {
		List<TradeOffers.Factory> lvl1weapon_trades = new ArrayList<>(
				Arrays.asList(TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.WEAPONSMITH).get(1)));
		lvl1weapon_trades
				.add(new TradeOffers.ProcessItemFactory(Items.IRON_NUGGET, 1, 1, ArachnidsItems.BULLETS, 6, 120, 5));
		TradeOffers.Factory[] resultlvl1 = new TradeOffers.Factory[] {};
		TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.WEAPONSMITH).put(1,
				lvl1weapon_trades.toArray(resultlvl1));

		List<TradeOffers.Factory> lvl2weapon_trades = new ArrayList<>(
				Arrays.asList(TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.WEAPONSMITH).get(2)));
		lvl2weapon_trades
				.add(new TradeOffers.ProcessItemFactory(Items.IRON_INGOT, 2, 4, ArachnidsItems.MAR1, 1, 120, 15));
		lvl2weapon_trades
				.add(new TradeOffers.ProcessItemFactory(Items.GUNPOWDER, 2, 4, ArachnidsItems.MZ90, 6, 120, 15));
		TradeOffers.Factory[] resultlvl2 = new TradeOffers.Factory[] {};
		TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.WEAPONSMITH).put(2,
				lvl2weapon_trades.toArray(resultlvl2));

		List<TradeOffers.Factory> lvl3weapon_trades = new ArrayList<>(
				Arrays.asList(TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.WEAPONSMITH).get(3)));
		lvl3weapon_trades
				.add(new TradeOffers.ProcessItemFactory(Items.IRON_BARS, 4, 6, ArachnidsItems.MAR2, 1, 120, 25));
		TradeOffers.Factory[] resultlvl3 = new TradeOffers.Factory[] {};
		TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.WEAPONSMITH).put(3,
				lvl3weapon_trades.toArray(resultlvl3));

		List<TradeOffers.Factory> lvl4weapon_trades = new ArrayList<>(
				Arrays.asList(TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.WEAPONSMITH).get(4)));
		lvl4weapon_trades
				.add(new TradeOffers.ProcessItemFactory(Items.IRON_BLOCK, 15, 9, ArachnidsItems.M55, 1, 120, 35));
		lvl4weapon_trades.add(new TradeOffers.ProcessItemFactory(Items.TNT, 7, 4, ArachnidsItems.TON, 6, 120, 35));
		TradeOffers.Factory[] resultlvl4 = new TradeOffers.Factory[] {};
		TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.WEAPONSMITH).put(4,
				lvl4weapon_trades.toArray(resultlvl4));

		List<TradeOffers.Factory> lvl1leather_trades = new ArrayList<>(
				Arrays.asList(TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.LEATHERWORKER).get(1)));
		lvl1leather_trades.add(
				new TradeOffers.ProcessItemFactory(Items.LEATHER_HELMET, 4, 1, ArachnidsItems.MI_HELMET, 1, 120, 5));
		lvl1leather_trades.add(new TradeOffers.ProcessItemFactory(Items.LEATHER_CHESTPLATE, 1, 1,
				ArachnidsItems.MI_CHESTPLATE, 1, 120, 5));
		lvl1leather_trades.add(new TradeOffers.ProcessItemFactory(Items.LEATHER_LEGGINGS, 1, 1,
				ArachnidsItems.MI_LEGGINGS, 1, 120, 5));
		lvl1leather_trades
				.add(new TradeOffers.ProcessItemFactory(Items.LEATHER_BOOTS, 1, 1, ArachnidsItems.MI_BOOTS, 1, 120, 5));
		TradeOffers.Factory[] resultleather = new TradeOffers.Factory[] {};
		TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.LEATHERWORKER).put(1,
				lvl1leather_trades.toArray(resultleather));
	}

}
