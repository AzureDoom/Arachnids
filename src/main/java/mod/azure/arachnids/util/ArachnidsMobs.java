package mod.azure.arachnids.util;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.blocks.MZ90BlockEntity;
import mod.azure.arachnids.blocks.TONBlockEntity;
import mod.azure.arachnids.entity.bugs.ArkellianEntity;
import mod.azure.arachnids.entity.bugs.BrainEntity;
import mod.azure.arachnids.entity.bugs.HopperEntity;
import mod.azure.arachnids.entity.bugs.PlasmaEntity;
import mod.azure.arachnids.entity.bugs.ScorpionEntity;
import mod.azure.arachnids.entity.bugs.TankerEntity;
import mod.azure.arachnids.entity.bugs.WarriorEntity;
import mod.azure.arachnids.entity.bugs.WorkerEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ArachnidsMobs {

  public static final EntityType<MZ90BlockEntity> MZ90 = Registry.register(
      Registry.ENTITY_TYPE, new Identifier(ArachnidsMod.MODID, "mz90_block"),
      FabricEntityTypeBuilder
          .<MZ90BlockEntity>create(SpawnGroup.MISC, MZ90BlockEntity::new)
          .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
          .trackRangeBlocks(90)
          .trackedUpdateRate(4)
          .build());

  public static final EntityType<TONBlockEntity> TON = Registry.register(
      Registry.ENTITY_TYPE, new Identifier(ArachnidsMod.MODID, "ton_block"),
      FabricEntityTypeBuilder
          .<TONBlockEntity>create(SpawnGroup.MISC, TONBlockEntity::new)
          .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
          .trackRangeBlocks(90)
          .trackedUpdateRate(4)
          .build());

  public static final EntityType<ArkellianEntity> ARKELLIANBUG =
      Registry.register(Registry.ENTITY_TYPE,
                        new Identifier(ArachnidsMod.MODID, "arkellian"),
                        FabricEntityTypeBuilder
                            .create(SpawnGroup.MONSTER, ArkellianEntity::new)
                            .dimensions(EntityDimensions.changing(1.1f, 0.85F))
                            .trackRangeBlocks(90)
                            .trackedUpdateRate(4)
                            .build());

  public static final EntityType<BrainEntity> BRAINBUG = Registry.register(
      Registry.ENTITY_TYPE, new Identifier(ArachnidsMod.MODID, "brainbug"),
      FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BrainEntity::new)
          .dimensions(EntityDimensions.changing(5.6f, 4.05F))
          .trackRangeBlocks(90)
          .trackedUpdateRate(4)
          .build());

  public static final EntityType<HopperEntity> HOOPERBUG = Registry.register(
      Registry.ENTITY_TYPE, new Identifier(ArachnidsMod.MODID, "hopper"),
      FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HopperEntity::new)
          .dimensions(EntityDimensions.fixed(2.3f, 2.15F))
          .trackRangeBlocks(90)
          .trackedUpdateRate(4)
          .build());

  public static final EntityType<PlasmaEntity> PLASMABUG = Registry.register(
      Registry.ENTITY_TYPE, new Identifier(ArachnidsMod.MODID, "plasma"),
      FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, PlasmaEntity::new)
          .dimensions(EntityDimensions.changing(12.05f, 8.25F))
          .trackRangeBlocks(90)
          .trackedUpdateRate(4)
          .build());

  public static final EntityType<ScorpionEntity> SCORPIONBUG =
      Registry.register(
          Registry.ENTITY_TYPE, new Identifier(ArachnidsMod.MODID, "scorpion"),
          FabricEntityTypeBuilder
              .create(SpawnGroup.MONSTER, ScorpionEntity::new)
              .dimensions(EntityDimensions.changing(12.05f, 8.25F))
              .trackRangeBlocks(90)
              .trackedUpdateRate(4)
              .build());

  public static final EntityType<TankerEntity> TANKERBUG = Registry.register(
      Registry.ENTITY_TYPE, new Identifier(ArachnidsMod.MODID, "tanker"),
      FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TankerEntity::new)
          .dimensions(EntityDimensions.changing(12.6f, 4.95F))
          .trackRangeBlocks(90)
          .trackedUpdateRate(4)
          .build());

  public static final EntityType<WarriorEntity> WARRIORBUG = Registry.register(
      Registry.ENTITY_TYPE, new Identifier(ArachnidsMod.MODID, "warrior"),
      FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WarriorEntity::new)
          .dimensions(EntityDimensions.fixed(2.3f, 2.15F))
          .trackRangeBlocks(90)
          .trackedUpdateRate(4)
          .build());

  public static final EntityType<WorkerEntity> WORKERBUG = Registry.register(
      Registry.ENTITY_TYPE, new Identifier(ArachnidsMod.MODID, "worker"),
      FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WorkerEntity::new)
          .dimensions(EntityDimensions.fixed(2.3f, 2.15F))
          .trackRangeBlocks(90)
          .trackedUpdateRate(4)
          .build());
}