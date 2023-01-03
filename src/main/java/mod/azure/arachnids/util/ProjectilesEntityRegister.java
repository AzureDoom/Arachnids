package mod.azure.arachnids.util;

import java.util.LinkedList;
import java.util.List;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.projectiles.BugPlasmaEntity;
import mod.azure.arachnids.entity.projectiles.BulletEntity;
import mod.azure.arachnids.entity.projectiles.FlameFiring;
import mod.azure.arachnids.entity.projectiles.FlareEntity;
import mod.azure.arachnids.entity.projectiles.MZ90Entity;
import mod.azure.arachnids.entity.projectiles.TacticalOxygenNukeEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ProjectilesEntityRegister {

	public static List<EntityType<? extends Entity>> ENTITY_TYPES = new LinkedList();
	public static List<EntityType<? extends Entity>> ENTITY_THAT_USE_ITEM_RENDERS = new LinkedList();

	public static EntityType<BulletEntity> BULLETS = projectile(BulletEntity::new, "762");
	public static EntityType<TacticalOxygenNukeEntity> TON = projectile(TacticalOxygenNukeEntity::new, "ton");
	public static EntityType<MZ90Entity> MZ90 = projectile(MZ90Entity::new, "mz90");
	public static EntityType<BugPlasmaEntity> BUGPLASMA = projectile(BugPlasmaEntity::new, "bugplasma");
	public static EntityType<FlareEntity> FLARE = projectile(FlareEntity::new, "flare");
	public static EntityType<FlameFiring> FIRING = projectile(FlameFiring::new, "flame_firing");

	private static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id) {
		return projectile(factory, id, true);
	}

	private static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id,
			boolean itemRender) {

		EntityType<T> type = FabricEntityTypeBuilder.<T>create(MobCategory.MISC, factory)
				.dimensions(new EntityDimensions(0.5F, 0.5F, true)).disableSummon().spawnableFarFromPlayer()
				.trackRangeBlocks(90).trackedUpdateRate(1).build();

		Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(ArachnidsMod.MODID, id), type);

		ENTITY_TYPES.add(type);

		if (itemRender) {
			ENTITY_THAT_USE_ITEM_RENDERS.add(type);
		}

		return type;
	}

}