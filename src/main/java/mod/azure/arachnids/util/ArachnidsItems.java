package mod.azure.arachnids.util;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.items.BugSpawnEgg;
import mod.azure.arachnids.items.MIArmorItem;
import mod.azure.arachnids.items.ammo.BulletAmmo;
import mod.azure.arachnids.items.ammo.FlareItem;
import mod.azure.arachnids.items.ammo.MZ90Item;
import mod.azure.arachnids.items.ammo.TONItem;
import mod.azure.arachnids.items.weapons.M55Item;
import mod.azure.arachnids.items.weapons.MAR1Item;
import mod.azure.arachnids.items.weapons.MAR2Item;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

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
	public static BugSpawnEgg ARKELLIANBUG_SPAWN_EGG = item(
			new BugSpawnEgg(ArachnidsMobs.ARKELLIANBUG, 0x5a001e, 0x770016), "arkellian_spawn_egg");
	public static BugSpawnEgg BRAINBUG_SPAWN_EGG = item(new BugSpawnEgg(ArachnidsMobs.BRAINBUG, 0x634e33, 0xf8dec1),
			"brainbug_spawn_egg");
	public static BugSpawnEgg HOOPERBUG_SPAWN_EGG = item(new BugSpawnEgg(ArachnidsMobs.HOOPERBUG, 0x214c0e, 0x6f9e5a),
			"hooper_spawn_egg");
	public static BugSpawnEgg PLAMSABUG_SPAWN_EGG = item(new BugSpawnEgg(ArachnidsMobs.PLAMSABUG, 0x020408, 0x535a66),
			"plamsa_spawn_egg");
	public static BugSpawnEgg SCORPIONBUG_SPAWN_EGG = item(
			new BugSpawnEgg(ArachnidsMobs.SCORPIONBUG, 0x535a66, 0xaa887c), "scorpion_spawn_egg");
	public static BugSpawnEgg TANKERBUG_SPAWN_EGG = item(new BugSpawnEgg(ArachnidsMobs.TANKERBUG, 0x091a27, 0x96a8b5),
			"tanker_spawn_egg");
	public static BugSpawnEgg WARRIORBUG_SPAWN_EGG = item(new BugSpawnEgg(ArachnidsMobs.WARRIORBUG, 0xa6380a, 0xc15224),
			"warrior_spawn_egg");
	public static BugSpawnEgg WORKERBUG_SPAWN_EGG = item(new BugSpawnEgg(ArachnidsMobs.WORKERBUG, 0x3b2c80, 0x55469b),
			"worker_spawn_egg");

	// Armor
	public static final Item MI_HELMET = item(new MIArmorItem(EquipmentSlot.HEAD), "miarmor_helmet");
	public static final Item MI_CHESTPLATE = item(new MIArmorItem(EquipmentSlot.CHEST), "miarmor_chestplate");
	public static final Item MI_LEGGINGS = item(new MIArmorItem(EquipmentSlot.LEGS), "miarmor_leggings");
	public static final Item MI_BOOTS = item(new MIArmorItem(EquipmentSlot.FEET), "miarmor_boots");

	static <T extends Item> T item(T c, String id) {
		Registry.register(Registry.ITEM, new Identifier(ArachnidsMod.MODID, id), c);
		return c;
	}
}