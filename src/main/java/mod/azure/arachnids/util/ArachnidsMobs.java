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
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ArachnidsMobs {

	public static final EntityType<MZ90BlockEntity> MZ90 = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(ArachnidsMod.MODID, "mz90_block"),
			FabricEntityTypeBuilder.<MZ90BlockEntity>create(MobCategory.MISC, MZ90BlockEntity::new)
					.dimensions(EntityDimensions.fixed(0.98F, 0.98F)).trackRangeBlocks(90).trackedUpdateRate(1)
					.build());

	public static final EntityType<TONBlockEntity> TON = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(ArachnidsMod.MODID, "ton_block"),
			FabricEntityTypeBuilder.<TONBlockEntity>create(MobCategory.MISC, TONBlockEntity::new)
					.dimensions(EntityDimensions.fixed(0.98F, 0.98F)).trackRangeBlocks(90).trackedUpdateRate(1)
					.build());

	public static final EntityType<ArkellianEntity> ARKELLIANBUG = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(ArachnidsMod.MODID, "arkellian"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, ArkellianEntity::new)
					.dimensions(EntityDimensions.scalable(1.1f, 0.85F)).trackRangeBlocks(90).trackedUpdateRate(1)
					.build());

	public static final EntityType<BrainEntity> BRAINBUG = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(ArachnidsMod.MODID, "brainbug"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, BrainEntity::new)
					.dimensions(EntityDimensions.scalable(5.6f, 4.05F)).trackRangeBlocks(90).trackedUpdateRate(1)
					.build());

	public static final EntityType<HopperEntity> HOOPERBUG = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(ArachnidsMod.MODID, "hopper"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, HopperEntity::new)
					.dimensions(EntityDimensions.fixed(1.6f, 1.15F)).trackRangeBlocks(90).trackedUpdateRate(1).build());

	public static final EntityType<PlasmaEntity> PLASMABUG = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(ArachnidsMod.MODID, "plasma"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, PlasmaEntity::new)
					.dimensions(EntityDimensions.scalable(12.05f, 8.25F)).trackRangeBlocks(90).trackedUpdateRate(1)
					.build());

	public static final EntityType<ScorpionEntity> SCORPIONBUG = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(ArachnidsMod.MODID, "scorpion"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, ScorpionEntity::new)
					.dimensions(EntityDimensions.scalable(12.05f, 8.25F)).trackRangeBlocks(90).trackedUpdateRate(1).build());

	public static final EntityType<TankerEntity> TANKERBUG = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(ArachnidsMod.MODID, "tanker"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, TankerEntity::new)
					.dimensions(EntityDimensions.scalable(12.6f, 4.95F)).trackRangeBlocks(90).trackedUpdateRate(1)
					.build());

	public static final EntityType<WarriorEntity> WARRIORBUG = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(ArachnidsMod.MODID, "warrior"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, WarriorEntity::new)
					.dimensions(EntityDimensions.fixed(2.3f, 2.15F)).trackRangeBlocks(90).trackedUpdateRate(1).build());

	public static final EntityType<WorkerEntity> WORKERBUG = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(ArachnidsMod.MODID, "worker"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, WorkerEntity::new)
					.dimensions(EntityDimensions.fixed(2.3f, 2.15F)).trackRangeBlocks(90).trackedUpdateRate(1).build());
}