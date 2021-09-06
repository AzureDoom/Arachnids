package mod.azure.arachnids.config;

import java.util.Arrays;
import java.util.List;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import mod.azure.arachnids.ArachnidsMod;

@Config(name = ArachnidsMod.MODID)
public class ArachnidsConfig implements ConfigData {
	@ConfigEntry.Gui.CollapsibleObject
	public Spawning spawn = new Spawning();

	@ConfigEntry.Gui.CollapsibleObject
	public MobStats stats = new MobStats();

	@ConfigEntry.Gui.CollapsibleObject
	public Weapons weapons = new Weapons();

	public static class Spawning {
		@ConfigEntry.Gui.Tooltip(count = 1)
		public List<String> arkellian_biomes = Arrays.asList("minecraft:desert", "minecraft:desert_hills",
				"minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau",
				"minecraft:eroded_badlands");
		public int arkellian_spawn_weight = 1;
		public int arkellian_min_group = 1;
		public int arkellian_max_group = 4;

		@ConfigEntry.Gui.Tooltip(count = 1)
		public List<String> brain_biomes = Arrays.asList("minecraft:desert", "minecraft:desert_hills",
				"minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau",
				"minecraft:eroded_badlands");
		public int brain_spawn_weight = 1;
		public int brain_min_group = 1;
		public int brain_max_group = 1;

		@ConfigEntry.Gui.Tooltip(count = 1)
		public List<String> hopper_biomes = Arrays.asList("minecraft:desert", "minecraft:desert_hills",
				"minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau",
				"minecraft:eroded_badlands");
		public int hopper_spawn_weight = 3;
		public int hopper_min_group = 1;
		public int hopper_max_group = 2;

		@ConfigEntry.Gui.Tooltip(count = 1)
		public List<String> plasma_biomes = Arrays.asList("minecraft:desert", "minecraft:desert_hills",
				"minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau",
				"minecraft:eroded_badlands");
		public int plasma_spawn_weight = 2;
		public int plasma_min_group = 1;
		public int plasma_max_group = 2;

		@ConfigEntry.Gui.Tooltip(count = 1)
		public List<String> scorpion_biomes = Arrays.asList("minecraft:desert", "minecraft:desert_hills",
				"minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau",
				"minecraft:eroded_badlands");
		public int scorpion_spawn_weight = 2;
		public int scorpion_min_group = 1;
		public int scorpion_max_group = 1;

		@ConfigEntry.Gui.Tooltip(count = 1)
		public List<String> tanker_biomes = Arrays.asList("minecraft:desert", "minecraft:desert_hills",
				"minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau",
				"minecraft:eroded_badlands");
		public int tanker_spawn_weight = 2;
		public int tanker_min_group = 1;
		public int tanker_max_group = 2;

		@ConfigEntry.Gui.Tooltip(count = 1)
		public List<String> warrior_biomes = Arrays.asList("minecraft:desert", "minecraft:desert_hills",
				"minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau",
				"minecraft:eroded_badlands");
		public int warrior_spawn_weight = 3;
		public int warrior_min_group = 1;
		public int warrior_max_group = 4;

		@ConfigEntry.Gui.Tooltip(count = 1)
		public List<String> worker_biomes = Arrays.asList("minecraft:desert", "minecraft:desert_hills",
				"minecraft:desert_lakes", "minecraft:badlands", "minecraft:badlands_plateau",
				"minecraft:eroded_badlands");
		public int worker_spawn_weight = 3;
		public int worker_min_group = 1;
		public int worker_max_group = 4;
	}

	public static class MobStats {
		public double arkellian_health = 16;
		public double arkellian_melee = 0;
		public int arkellian_exp = 1;

		public double brain_health = 45;
		public double brain_melee = 2;
		public int brain_exp = 7;

		public double hopper_health = 35;
		public int hopper_armor = 10;
		public double hopper_melee = 10;
		public int hopper_exp = 3;

		public double plasma_health = 100;
		public int plasma_armor = 6;
		public double plasma_melee = 10;
		public double plasma_ranged = 20;
		public int plasma_exp = 6;

		public double scorpion_health = 75;
		public int scorpion_armor = 10;
		public double scorpion_melee = 10;
		public double scorpion_ranged = 10;
		public int scorpion_exp = 5;

		public double tanker_health = 60;
		public int tanker_armor = 16;
		public double tanker_melee = 15;
		public double tanker_ranged = 20;
		public int tanker_exp = 6;

		public double warrior_health = 35;
		public int warrior_armor = 10;
		public double warrior_melee = 10;
		public int warrior_exp = 4;

		public double worker_health = 25;
		public int worker_armor = 2;
		public double worker_melee = 5;
		public int worker_exp = 4;
	}

	public static class Weapons {
		public int MAR1_max_ammo = 1000;
		public int MAR1_mag_size = 1000;
		public float MAR1_bullet_damage = 2.5F;

		public int MAR2_max_ammo = 800;
		public int MAR2_mag_size = 800;
		public float MAR2_bullet_damage = 4.5F;

		public int HeavyMounted_max_ammo = 4096;
		public int HeavyMounted_belt_size = 4096;
		public float HeavyMounted_bullet_damage = 8F;

		public float MZ90_explode_damage = 2F;
		public float TON_damage = 10F;

		public boolean break_blocks = true;
		public boolean cause_fire = true;
	}
}