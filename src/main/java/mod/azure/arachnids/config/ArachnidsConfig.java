package mod.azure.arachnids.config;

import java.util.Arrays;
import java.util.List;

import eu.midnightdust.lib.config.MidnightConfig;

public class ArachnidsConfig extends MidnightConfig {

	@Entry
	public static List<String> arkellian_biomes = Arrays.asList("minecraft:desert", "minecraft:desert_hills", "minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau", "minecraft:eroded_badlands");
	@Entry
	public static int arkellian_spawn_weight = 1;
	@Entry
	public static int arkellian_min_group = 1;
	@Entry
	public static int arkellian_max_group = 4;

	@Entry
	public static List<String> brain_biomes = Arrays.asList("minecraft:desert", "minecraft:desert_hills", "minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau", "minecraft:eroded_badlands");
	@Entry
	public static int brain_spawn_weight = 1;
	@Entry
	public static int brain_min_group = 1;
	@Entry
	public static int brain_max_group = 1;

	@Entry
	public static List<String> hopper_biomes = Arrays.asList("minecraft:desert", "minecraft:desert_hills", "minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau", "minecraft:eroded_badlands");
	@Entry
	public static int hopper_spawn_weight = 3;
	@Entry
	public static int hopper_min_group = 1;
	@Entry
	public static int hopper_max_group = 2;

	@Entry
	public static List<String> plasma_biomes = Arrays.asList("minecraft:desert", "minecraft:desert_hills", "minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau", "minecraft:eroded_badlands");
	@Entry
	public static int plasma_spawn_weight = 2;
	@Entry
	public static int plasma_min_group = 1;
	@Entry
	public static int plasma_max_group = 2;

	@Entry
	public static List<String> scorpion_biomes = Arrays.asList("minecraft:desert", "minecraft:desert_hills", "minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau", "minecraft:eroded_badlands");
	@Entry
	public static int scorpion_spawn_weight = 2;
	@Entry
	public static int scorpion_min_group = 1;
	@Entry
	public static int scorpion_max_group = 1;

	@Entry
	public static List<String> tanker_biomes = Arrays.asList("minecraft:desert", "minecraft:desert_hills", "minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau", "minecraft:eroded_badlands");
	@Entry
	public static int tanker_spawn_weight = 2;
	@Entry
	public static int tanker_min_group = 1;
	@Entry
	public static int tanker_max_group = 2;

	@Entry
	public static List<String> warrior_biomes = Arrays.asList("minecraft:desert", "minecraft:desert_hills", "minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau", "minecraft:eroded_badlands");
	@Entry
	public static int warrior_spawn_weight = 3;
	@Entry
	public static int warrior_min_group = 1;
	@Entry
	public static int warrior_max_group = 4;

	@Entry
	public static List<String> worker_biomes = Arrays.asList("minecraft:desert", "minecraft:desert_hills", "minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau", "minecraft:eroded_badlands");
	@Entry
	public static int worker_spawn_weight = 3;
	@Entry
	public static int worker_min_group = 1;
	@Entry
	public static int worker_max_group = 4;

	@Entry
	public static double arkellian_health = 16;
	@Entry
	public static double arkellian_melee = 0;
	@Entry
	public static int arkellian_exp = 1;

	@Entry
	public static double brain_health = 45;
	@Entry
	public static double brain_melee = 2;
	@Entry
	public static int brain_exp = 7;

	@Entry
	public static double hopper_health = 35;
	@Entry
	public static int hopper_armor = 10;
	@Entry
	public static double hopper_melee = 10;
	@Entry
	public static float hopper_firefly_ranged = 10;
	@Entry
	public static int hopper_exp = 3;

	@Entry
	public static double plasma_health = 100;
	@Entry
	public static int plasma_armor = 6;
	@Entry
	public static double plasma_melee = 10;
	@Entry
	public static float plasma_ranged = 20;
	@Entry
	public static int plasma_exp = 6;

	@Entry
	public static double scorpion_health = 75;
	@Entry
	public static int scorpion_armor = 10;
	@Entry
	public static double scorpion_melee = 10;
	@Entry
	public static double scorpion_ranged = 10;
	@Entry
	public static int scorpion_exp = 5;

	@Entry
	public static double tanker_health = 60;
	@Entry
	public static int tanker_armor = 16;
	@Entry
	public static double tanker_melee = 15;
	@Entry
	public static double tanker_ranged = 20;
	@Entry
	public static int tanker_exp = 6;

	@Entry
	public static double warrior_health = 35;
	@Entry
	public static int warrior_armor = 10;
	@Entry
	public static double warrior_melee = 10;
	@Entry
	public static int warrior_exp = 4;

	@Entry
	public static double worker_health = 25;
	@Entry
	public static int worker_armor = 2;
	@Entry
	public static double worker_melee = 5;
	@Entry
	public static int worker_exp = 4;

	@Entry
	public static int MAR1_max_ammo = 1000;
	@Entry
	public static int MAR1_mag_size = 1000;
	@Entry
	public static float MAR1_bullet_damage = 2.5F;

	@Entry
	public static int MAR2_max_ammo = 800;
	@Entry
	public static int MAR2_mag_size = 800;
	@Entry
	public static float MAR2_bullet_damage = 4.5F;

	@Entry
	public static int HeavyMounted_max_ammo = 4096;
	@Entry
	public static int HeavyMounted_belt_size = 4096;
	@Entry
	public static float HeavyMounted_bullet_damage = 8F;

	@Entry
	public static float MZ90_explode_damage = 2F;
	@Entry
	public static float TON_damage = 10F;

	@Entry
	public static boolean break_blocks = true;
	@Entry
	public static boolean cause_fire = true;
}