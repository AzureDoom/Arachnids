package mod.azure.arachnids.util;

import java.util.List;

import mod.azure.arachnids.config.ArachnidsConfig;
import mod.azure.arachnids.entity.BaseBugEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class MobSpawn {

	public static void addSpawnEntries() {
//		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(ArachnidsConfig.arkellian_biomes, context)),
//				MobCategory.MONSTER, ArachnidsMobs.ARKELLIANBUG, ArachnidsConfig.arkellian_spawn_weight,
//				ArachnidsConfig.arkellian_min_group, ArachnidsConfig.arkellian_max_group);
//		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(ArachnidsConfig.brain_biomes, context)),
//				MobCategory.MONSTER, ArachnidsMobs.BRAINBUG, ArachnidsConfig.brain_spawn_weight, ArachnidsConfig.brain_min_group,
//				ArachnidsConfig.brain_max_group);
//		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(ArachnidsConfig.hopper_biomes, context)),
//				MobCategory.MONSTER, ArachnidsMobs.HOOPERBUG, ArachnidsConfig.hopper_spawn_weight, ArachnidsConfig.hopper_min_group,
//				ArachnidsConfig.hopper_max_group);
//		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(ArachnidsConfig.plasma_biomes, context)),
//				MobCategory.MONSTER, ArachnidsMobs.PLAMSABUG, ArachnidsConfig.plasma_spawn_weight, ArachnidsConfig.plasma_min_group,
//				ArachnidsConfig.plasma_max_group);
//		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(ArachnidsConfig.scorpion_biomes, context)),
//				MobCategory.MONSTER, ArachnidsMobs.SCORPIONBUG, ArachnidsConfig.scorpion_spawn_weight, ArachnidsConfig.scorpion_min_group,
//				ArachnidsConfig.scorpion_max_group);
//		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(ArachnidsConfig.tanker_biomes, context)),
//				MobCategory.MONSTER, ArachnidsMobs.TANKERBUG, ArachnidsConfig.tanker_spawn_weight, ArachnidsConfig.tanker_min_group,
//				ArachnidsConfig.tanker_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(ArachnidsConfig.warrior_biomes, context)),
				MobCategory.MONSTER, ArachnidsMobs.WARRIORBUG, ArachnidsConfig.warrior_spawn_weight,
				ArachnidsConfig.warrior_min_group, ArachnidsConfig.warrior_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(ArachnidsConfig.warrior_biomes, context)),
				MobCategory.MONSTER, ArachnidsMobs.WORKERBUG, ArachnidsConfig.worker_spawn_weight,
				ArachnidsConfig.worker_min_group, ArachnidsConfig.worker_max_group);
		SpawnPlacements.register(ArachnidsMobs.ARKELLIANBUG, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
		SpawnPlacements.register(ArachnidsMobs.BRAINBUG, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
		SpawnPlacements.register(ArachnidsMobs.HOOPERBUG, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
		SpawnPlacements.register(ArachnidsMobs.PLASMABUG, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
		SpawnPlacements.register(ArachnidsMobs.SCORPIONBUG, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
		SpawnPlacements.register(ArachnidsMobs.TANKERBUG, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
		SpawnPlacements.register(ArachnidsMobs.WARRIORBUG, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
		SpawnPlacements.register(ArachnidsMobs.WORKERBUG, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
	}

	private static boolean parseBiomes(List<String> biomes, BiomeSelectionContext biomeContext) {
		return biomes.contains(biomeContext.getBiomeKey().registry().toString());
	}
}