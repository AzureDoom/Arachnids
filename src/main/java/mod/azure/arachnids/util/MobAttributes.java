package mod.azure.arachnids.util;

import mod.azure.arachnids.entity.bugs.ArkellianEntity;
import mod.azure.arachnids.entity.bugs.BrainEntity;
import mod.azure.arachnids.entity.bugs.HopperEntity;
import mod.azure.arachnids.entity.bugs.PlasmaEntity;
import mod.azure.arachnids.entity.bugs.ScorpionEntity;
import mod.azure.arachnids.entity.bugs.TankerEntity;
import mod.azure.arachnids.entity.bugs.WarriorEntity;
import mod.azure.arachnids.entity.bugs.WorkerEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class MobAttributes {

	public static void init() {
		FabricDefaultAttributeRegistry.register(ArachnidsMobs.ARKELLIANBUG, ArkellianEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ArachnidsMobs.BRAINBUG, BrainEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ArachnidsMobs.HOOPERBUG, HopperEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ArachnidsMobs.PLASMABUG, PlasmaEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ArachnidsMobs.SCORPIONBUG, ScorpionEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ArachnidsMobs.TANKERBUG, TankerEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ArachnidsMobs.WARRIORBUG, WarriorEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ArachnidsMobs.WORKERBUG, WorkerEntity.createMobAttributes());
	}

}