package mod.azure.arachnids.config;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.azurelib.config.Config;
import mod.azure.azurelib.config.Configurable;

@Config(id = ArachnidsMod.MODID)
public class ArachnidsConfig {

	@Configurable
	public String[] arkellian_biomes = { "minecraft:desert", "minecraft:desert_hills", "minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau", "minecraft:eroded_badlands" };
	@Configurable
	public int arkellian_spawn_weight = 1;
	@Configurable
	public int arkellian_min_group = 1;
	@Configurable
	public int arkellian_max_group = 4;

	@Configurable
	public String[] brain_biomes = { "minecraft:desert", "minecraft:desert_hills", "minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau", "minecraft:eroded_badlands" };
	@Configurable
	public int brain_spawn_weight = 1;
	@Configurable
	public int brain_min_group = 1;
	@Configurable
	public int brain_max_group = 1;

	@Configurable
	public String[] hopper_biomes = { "minecraft:desert", "minecraft:desert_hills", "minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau", "minecraft:eroded_badlands" };
	@Configurable
	public int hopper_spawn_weight = 3;
	@Configurable
	public int hopper_min_group = 1;
	@Configurable
	public int hopper_max_group = 2;

	@Configurable
	public String[] plasma_biomes = { "minecraft:desert", "minecraft:desert_hills", "minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau", "minecraft:eroded_badlands" };
	@Configurable
	public int plasma_spawn_weight = 2;
	@Configurable
	public int plasma_min_group = 1;
	@Configurable
	public int plasma_max_group = 2;

	@Configurable
	public String[] scorpion_biomes = { "minecraft:desert", "minecraft:desert_hills", "minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau", "minecraft:eroded_badlands" };
	@Configurable
	public int scorpion_spawn_weight = 2;
	@Configurable
	public int scorpion_min_group = 1;
	@Configurable
	public int scorpion_max_group = 1;

	@Configurable
	public String[] tanker_biomes = { "minecraft:desert", "minecraft:desert_hills", "minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau", "minecraft:eroded_badlands" };
	@Configurable
	public int tanker_spawn_weight = 2;
	@Configurable
	public int tanker_min_group = 1;
	@Configurable
	public int tanker_max_group = 2;

	@Configurable
	public String[] warrior_biomes = { "minecraft:desert", "minecraft:desert_hills", "minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau", "minecraft:eroded_badlands" };
	@Configurable
	public int warrior_spawn_weight = 3;
	@Configurable
	public int warrior_min_group = 1;
	@Configurable
	public int warrior_max_group = 4;

	@Configurable
	public String[] worker_biomes = { "minecraft:desert", "minecraft:desert_hills", "minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau", "minecraft:eroded_badlands" };
	@Configurable
	public int worker_spawn_weight = 3;
	@Configurable
	public int worker_min_group = 1;
	@Configurable
	public int worker_max_group = 4;

	@Configurable
	public double arkellian_health = 16;
	@Configurable
	public double arkellian_melee = 0;
	@Configurable
	public int arkellian_exp = 1;

	@Configurable
	public double brain_health = 45;
	@Configurable
	public double brain_melee = 2;
	@Configurable
	public int brain_exp = 7;

	@Configurable
	public double hopper_health = 35;
	@Configurable
	public int hopper_armor = 10;
	@Configurable
	public double hopper_melee = 10;
	@Configurable
	public float hopper_firefly_ranged = 10;
	@Configurable
	public int hopper_exp = 3;

	@Configurable
	public double plasma_health = 100;
	@Configurable
	public int plasma_armor = 6;
	@Configurable
	public double plasma_melee = 10;
	@Configurable
	public float plasma_ranged = 20;
	@Configurable
	public int plasma_exp = 6;

	@Configurable
	public double scorpion_health = 75;
	@Configurable
	public int scorpion_armor = 10;
	@Configurable
	public double scorpion_melee = 10;
	@Configurable
	public double scorpion_ranged = 10;
	@Configurable
	public int scorpion_exp = 5;

	@Configurable
	public double tanker_health = 60;
	@Configurable
	public int tanker_armor = 16;
	@Configurable
	public double tanker_melee = 15;
	@Configurable
	public double tanker_ranged = 20;
	@Configurable
	public int tanker_exp = 6;

	@Configurable
	public double warrior_health = 35;
	@Configurable
	public int warrior_armor = 10;
	@Configurable
	public double warrior_melee = 10;
	@Configurable
	public int warrior_exp = 4;

	@Configurable
	public double worker_health = 25;
	@Configurable
	public int worker_armor = 2;
	@Configurable
	public double worker_melee = 5;
	@Configurable
	public int worker_exp = 4;

	@Configurable
	public int MAR1_max_ammo = 1000;
	@Configurable
	public int MAR1_mag_size = 1000;
	@Configurable
	public float MAR1_bullet_damage = 2.5F;

	@Configurable
	public int MAR2_max_ammo = 800;
	@Configurable
	public int MAR2_mag_size = 800;
	@Configurable
	public float MAR2_bullet_damage = 4.5F;

	@Configurable
	public int HeavyMounted_max_ammo = 4096;
	@Configurable
	public int HeavyMounted_belt_size = 4096;
	@Configurable
	public float HeavyMounted_bullet_damage = 8F;

	@Configurable
	public float MZ90_explode_damage = 2F;
	@Configurable
	public float TON_damage = 10F;

	@Configurable
	public boolean break_blocks = true;
	@Configurable
	public boolean cause_fire = true;
}