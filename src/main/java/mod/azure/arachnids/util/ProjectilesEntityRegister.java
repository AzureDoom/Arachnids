package mod.azure.arachnids.util;

import java.util.LinkedList;
import java.util.List;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.projectiles.BugPlasmaEntity;
import mod.azure.arachnids.entity.projectiles.BulletEntity;
import mod.azure.arachnids.entity.projectiles.FlareEntity;
import mod.azure.arachnids.entity.projectiles.MZ90Entity;
import mod.azure.arachnids.entity.projectiles.TacticalOxygenNukeEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ProjectilesEntityRegister {

	public static List<EntityType<? extends Entity>> ENTITY_TYPES = new LinkedList();
	public static List<EntityType<? extends Entity>> ENTITY_THAT_USE_ITEM_RENDERS = new LinkedList();

	public static EntityType<BulletEntity> BULLETS = projectile(BulletEntity::new, "762");
	public static EntityType<TacticalOxygenNukeEntity> TON = projectile(TacticalOxygenNukeEntity::new, "ton");
	public static EntityType<MZ90Entity> MZ90 = projectile(MZ90Entity::new, "mz90");
	public static EntityType<BugPlasmaEntity> BUGPLASMA = projectile(BugPlasmaEntity::new, "bugplasma");
	public static EntityType<FlareEntity> FLARE = projectile(FlareEntity::new, "flare");

	private static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id) {
		return projectile(factory, id, true);
	}

	private static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id,
			boolean itemRender) {

		EntityType<T> type = FabricEntityTypeBuilder.<T>create(SpawnGroup.MISC, factory)
				.dimensions(new EntityDimensions(0.5F, 0.5F, true)).disableSummon().spawnableFarFromPlayer()
				.trackRangeBlocks(90).trackedUpdateRate(1).build();

		Registry.register(Registry.ENTITY_TYPE, new Identifier(ArachnidsMod.MODID, id), type);

		ENTITY_TYPES.add(type);

		if (itemRender) {
			ENTITY_THAT_USE_ITEM_RENDERS.add(type);
		}

		return type;
	}

}