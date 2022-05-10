package mod.azure.arachnids.util;

import java.util.List;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.config.ArachnidsConfig.Spawning;
import mod.azure.arachnids.entity.bugs.ArkellianEntity;
import mod.azure.arachnids.entity.bugs.BrainEntity;
import mod.azure.arachnids.entity.bugs.HopperEntity;
import mod.azure.arachnids.entity.bugs.PlasmaEntity;
import mod.azure.arachnids.entity.bugs.ScorpionEntity;
import mod.azure.arachnids.entity.bugs.TankerEntity;
import mod.azure.arachnids.entity.bugs.WarriorEntity;
import mod.azure.arachnids.entity.bugs.WorkerEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;

public class MobSpawn {

	private static Spawning config = ArachnidsMod.config.spawn;

	public static void addSpawnEntries() {
//		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.arkellian_biomes, context)),
//				SpawnGroup.MONSTER, ArachnidsMobs.ARKELLIANBUG, config.arkellian_spawn_weight,
//				config.arkellian_min_group, config.arkellian_max_group);
//		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.brain_biomes, context)),
//				SpawnGroup.MONSTER, ArachnidsMobs.BRAINBUG, config.brain_spawn_weight, config.brain_min_group,
//				config.brain_max_group);
//		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.hopper_biomes, context)),
//				SpawnGroup.MONSTER, ArachnidsMobs.HOOPERBUG, config.hopper_spawn_weight, config.hopper_min_group,
//				config.hopper_max_group);
//		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.plasma_biomes, context)),
//				SpawnGroup.MONSTER, ArachnidsMobs.PLAMSABUG, config.plasma_spawn_weight, config.plasma_min_group,
//				config.plasma_max_group);
//		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.scorpion_biomes, context)),
//				SpawnGroup.MONSTER, ArachnidsMobs.SCORPIONBUG, config.scorpion_spawn_weight, config.scorpion_min_group,
//				config.scorpion_max_group);
//		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.tanker_biomes, context)),
//				SpawnGroup.MONSTER, ArachnidsMobs.TANKERBUG, config.tanker_spawn_weight, config.tanker_min_group,
//				config.tanker_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.warrior_biomes, context)),
				SpawnGroup.MONSTER, ArachnidsMobs.WARRIORBUG, config.warrior_spawn_weight, config.warrior_min_group,
				config.warrior_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.warrior_biomes, context)),
				SpawnGroup.MONSTER, ArachnidsMobs.WORKERBUG, config.worker_spawn_weight, config.worker_min_group,
				config.worker_max_group);
		SpawnRestrictionAccessor.callRegister(ArachnidsMobs.ARKELLIANBUG, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ArkellianEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(ArachnidsMobs.BRAINBUG, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BrainEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(ArachnidsMobs.HOOPERBUG, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HopperEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(ArachnidsMobs.PLASMABUG, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PlasmaEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(ArachnidsMobs.SCORPIONBUG, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ScorpionEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(ArachnidsMobs.TANKERBUG, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TankerEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(ArachnidsMobs.WARRIORBUG, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WarriorEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(ArachnidsMobs.WORKERBUG, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WorkerEntity::canSpawn);
	}

	private static boolean parseBiomes(List<String> biomes, BiomeSelectionContext biomeContext) {
		return biomes.contains(biomeContext.getBiomeKey().getValue().toString())
				|| biomes.contains("#" + biomeContext.getBiomeRegistryEntry().toString());
	}
}