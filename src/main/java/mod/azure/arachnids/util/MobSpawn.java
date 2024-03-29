package mod.azure.arachnids.util;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.BaseBugEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;

public class MobSpawn {

	public static void addSpawnEntries() {
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(ArachnidsMod.ARKELLIAN_BIOMES, context)), MobCategory.MONSTER, ArachnidsMobs.ARKELLIANBUG, ArachnidsMod.config.arkellian_spawn_weight, ArachnidsMod.config.arkellian_min_group, ArachnidsMod.config.arkellian_max_group);
//		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(ArachnidsMod.brain_biomes, context)),
//				MobCategory.MONSTER, ArachnidsMobs.BRAINBUG, ArachnidsMod.config.brain_spawn_weight, ArachnidsMod.config.brain_min_group,
//				ArachnidsMod.config.brain_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(ArachnidsMod.HOPPER_BIOMES, context)), MobCategory.MONSTER, ArachnidsMobs.HOOPERBUG, ArachnidsMod.config.hopper_spawn_weight, ArachnidsMod.config.hopper_min_group, ArachnidsMod.config.hopper_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(ArachnidsMod.PLASMA_BIOMES, context)), MobCategory.MONSTER, ArachnidsMobs.PLASMABUG, ArachnidsMod.config.plasma_spawn_weight, ArachnidsMod.config.plasma_min_group, ArachnidsMod.config.plasma_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(ArachnidsMod.SCORPION_BIOMES, context)), MobCategory.MONSTER, ArachnidsMobs.SCORPIONBUG, ArachnidsMod.config.scorpion_spawn_weight, ArachnidsMod.config.scorpion_min_group, ArachnidsMod.config.scorpion_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(ArachnidsMod.TANKER_BIOMES, context)), MobCategory.MONSTER, ArachnidsMobs.TANKERBUG, ArachnidsMod.config.tanker_spawn_weight, ArachnidsMod.config.tanker_min_group, ArachnidsMod.config.tanker_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(ArachnidsMod.WARRIOR_BIOMES, context)), MobCategory.MONSTER, ArachnidsMobs.WARRIORBUG, ArachnidsMod.config.warrior_spawn_weight, ArachnidsMod.config.warrior_min_group, ArachnidsMod.config.warrior_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(ArachnidsMod.WARRIOR_BIOMES, context)), MobCategory.MONSTER, ArachnidsMobs.WORKERBUG, ArachnidsMod.config.worker_spawn_weight, ArachnidsMod.config.worker_min_group, ArachnidsMod.config.worker_max_group);
		SpawnPlacements.register(ArachnidsMobs.ARKELLIANBUG, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
		SpawnPlacements.register(ArachnidsMobs.BRAINBUG, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
		SpawnPlacements.register(ArachnidsMobs.HOOPERBUG, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
		SpawnPlacements.register(ArachnidsMobs.PLASMABUG, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
		SpawnPlacements.register(ArachnidsMobs.SCORPIONBUG, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
		SpawnPlacements.register(ArachnidsMobs.TANKERBUG, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
		SpawnPlacements.register(ArachnidsMobs.WARRIORBUG, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
		SpawnPlacements.register(ArachnidsMobs.WORKERBUG, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
	}

	private static boolean parseBiomes(TagKey<Biome> biomes, BiomeSelectionContext biomeContext) {
		return biomeContext.hasTag(biomes);
	}
}