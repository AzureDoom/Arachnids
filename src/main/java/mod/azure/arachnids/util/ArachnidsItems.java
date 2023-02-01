package mod.azure.arachnids.util;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.items.MIArmorItem;
import mod.azure.arachnids.items.ammo.BulletAmmo;
import mod.azure.arachnids.items.ammo.FlareItem;
import mod.azure.arachnids.items.ammo.MZ90Item;
import mod.azure.arachnids.items.ammo.TONItem;
import mod.azure.arachnids.items.weapons.M55Item;
import mod.azure.arachnids.items.weapons.MAR1Item;
import mod.azure.arachnids.items.weapons.MAR2Item;
import mod.azure.azurelib.items.AzureSpawnEgg;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class ArachnidsItems {

	// Weapons
	public static final MAR1Item MAR1 = item(new MAR1Item(), "mar1");
	public static final MAR2Item MAR2 = item(new MAR2Item(), "mar2");
	public static final M55Item M55 = item(new M55Item(), "m55");
	public static final BulletAmmo BULLETS = item(new BulletAmmo(1.2F), "762");
	public static final BlockItem TON = item(new TONItem(ArachnidsMod.TONBLOCK), "ton");
	public static final BlockItem MZ90 = item(new MZ90Item(ArachnidsMod.MZ90BLOCK), "mz90");
	public static FlareItem FLARE = item(new FlareItem(), "flare");

	// Spawn Eggs
	public static AzureSpawnEgg ARKELLIANBUG_SPAWN_EGG = item(
			new AzureSpawnEgg(ArachnidsMobs.ARKELLIANBUG, 0x5a001e, 0x770016), "arkellian_spawn_egg");
	public static AzureSpawnEgg BRAINBUG_SPAWN_EGG = item(new AzureSpawnEgg(ArachnidsMobs.BRAINBUG, 0x634e33, 0xf8dec1),
			"brainbug_spawn_egg");
	public static AzureSpawnEgg HOOPERBUG_SPAWN_EGG = item(
			new AzureSpawnEgg(ArachnidsMobs.HOOPERBUG, 0x214c0e, 0x6f9e5a), "hooper_spawn_egg");
	public static AzureSpawnEgg PLAMSABUG_SPAWN_EGG = item(
			new AzureSpawnEgg(ArachnidsMobs.PLASMABUG, 0x020408, 0x535a66), "plasma_spawn_egg");
	public static AzureSpawnEgg SCORPIONBUG_SPAWN_EGG = item(
			new AzureSpawnEgg(ArachnidsMobs.SCORPIONBUG, 0x535a66, 0xaa887c), "scorpion_spawn_egg");
	public static AzureSpawnEgg TANKERBUG_SPAWN_EGG = item(
			new AzureSpawnEgg(ArachnidsMobs.TANKERBUG, 0x091a27, 0x96a8b5), "tanker_spawn_egg");
	public static AzureSpawnEgg WARRIORBUG_SPAWN_EGG = item(
			new AzureSpawnEgg(ArachnidsMobs.WARRIORBUG, 0xa6380a, 0xc15224), "warrior_spawn_egg");
	public static AzureSpawnEgg WORKERBUG_SPAWN_EGG = item(
			new AzureSpawnEgg(ArachnidsMobs.WORKERBUG, 0x3b2c80, 0x55469b), "worker_spawn_egg");

	// Armor
	public static final Item MI_HELMET = item(new MIArmorItem(EquipmentSlot.HEAD), "miarmor_helmet");
	public static final Item MI_CHESTPLATE = item(new MIArmorItem(EquipmentSlot.CHEST), "miarmor_chestplate");
	public static final Item MI_LEGGINGS = item(new MIArmorItem(EquipmentSlot.LEGS), "miarmor_leggings");
	public static final Item MI_BOOTS = item(new MIArmorItem(EquipmentSlot.FEET), "miarmor_boots");

	static <T extends Item> T item(T c, String id) {
		Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(ArachnidsMod.MODID, id), c);
		return c;
	}
}