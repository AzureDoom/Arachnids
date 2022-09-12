package mod.azure.arachnids.util;

import java.util.List;
import mod.azure.arachnids.config.ArachnidsConfig;
import mod.azure.arachnids.entity.BaseBugEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;

public class MobSpawn {

  public static void addSpawnEntries() {
    //		BiomeModifications.addSpawn(BiomeSelectors.all().and(context ->
    //parseBiomes(ArachnidsConfig.arkellian_biomes, context)),
    //				SpawnGroup.MONSTER, ArachnidsMobs.ARKELLIANBUG,
    //ArachnidsConfig.arkellian_spawn_weight,
    //				ArachnidsConfig.arkellian_min_group,
    //ArachnidsConfig.arkellian_max_group);
    //		BiomeModifications.addSpawn(BiomeSelectors.all().and(context ->
    //parseBiomes(ArachnidsConfig.brain_biomes, context)), 				SpawnGroup.MONSTER,
    //ArachnidsMobs.BRAINBUG, ArachnidsConfig.brain_spawn_weight,
    //ArachnidsConfig.brain_min_group, 				ArachnidsConfig.brain_max_group);
    //		BiomeModifications.addSpawn(BiomeSelectors.all().and(context ->
    //parseBiomes(ArachnidsConfig.hopper_biomes, context)), 				SpawnGroup.MONSTER,
    //ArachnidsMobs.HOOPERBUG, ArachnidsConfig.hopper_spawn_weight,
    //ArachnidsConfig.hopper_min_group, 				ArachnidsConfig.hopper_max_group);
    //		BiomeModifications.addSpawn(BiomeSelectors.all().and(context ->
    //parseBiomes(ArachnidsConfig.plasma_biomes, context)), 				SpawnGroup.MONSTER,
    //ArachnidsMobs.PLAMSABUG, ArachnidsConfig.plasma_spawn_weight,
    //ArachnidsConfig.plasma_min_group, 				ArachnidsConfig.plasma_max_group);
    //		BiomeModifications.addSpawn(BiomeSelectors.all().and(context ->
    //parseBiomes(ArachnidsConfig.scorpion_biomes, context)),
    //				SpawnGroup.MONSTER, ArachnidsMobs.SCORPIONBUG,
    //ArachnidsConfig.scorpion_spawn_weight, ArachnidsConfig.scorpion_min_group,
    //				ArachnidsConfig.scorpion_max_group);
    //		BiomeModifications.addSpawn(BiomeSelectors.all().and(context ->
    //parseBiomes(ArachnidsConfig.tanker_biomes, context)), 				SpawnGroup.MONSTER,
    //ArachnidsMobs.TANKERBUG, ArachnidsConfig.tanker_spawn_weight,
    //ArachnidsConfig.tanker_min_group, 				ArachnidsConfig.tanker_max_group);
    BiomeModifications.addSpawn(
        BiomeSelectors.all().and(
            context -> parseBiomes(ArachnidsConfig.warrior_biomes, context)),
        SpawnGroup.MONSTER, ArachnidsMobs.WARRIORBUG,
        ArachnidsConfig.warrior_spawn_weight, ArachnidsConfig.warrior_min_group,
        ArachnidsConfig.warrior_max_group);
    BiomeModifications.addSpawn(
        BiomeSelectors.all().and(
            context -> parseBiomes(ArachnidsConfig.warrior_biomes, context)),
        SpawnGroup.MONSTER, ArachnidsMobs.WORKERBUG,
        ArachnidsConfig.worker_spawn_weight, ArachnidsConfig.worker_min_group,
        ArachnidsConfig.worker_max_group);
    SpawnRestriction.register(
        ArachnidsMobs.ARKELLIANBUG, SpawnRestriction.Location.ON_GROUND,
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
    SpawnRestriction.register(
        ArachnidsMobs.BRAINBUG, SpawnRestriction.Location.ON_GROUND,
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
    SpawnRestriction.register(
        ArachnidsMobs.HOOPERBUG, SpawnRestriction.Location.ON_GROUND,
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
    SpawnRestriction.register(
        ArachnidsMobs.PLASMABUG, SpawnRestriction.Location.ON_GROUND,
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
    SpawnRestriction.register(
        ArachnidsMobs.SCORPIONBUG, SpawnRestriction.Location.ON_GROUND,
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
    SpawnRestriction.register(
        ArachnidsMobs.TANKERBUG, SpawnRestriction.Location.ON_GROUND,
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
    SpawnRestriction.register(
        ArachnidsMobs.WARRIORBUG, SpawnRestriction.Location.ON_GROUND,
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
    SpawnRestriction.register(
        ArachnidsMobs.WORKERBUG, SpawnRestriction.Location.ON_GROUND,
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BaseBugEntity::canSpawn);
  }

  private static boolean parseBiomes(List<String> biomes,
                                     BiomeSelectionContext biomeContext) {
    return biomes.contains(biomeContext.getBiomeKey().getValue().toString());
  }
}
