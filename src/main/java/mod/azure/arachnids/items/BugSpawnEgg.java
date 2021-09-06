package mod.azure.arachnids.items;

import mod.azure.arachnids.ArachnidsMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;

public class BugSpawnEgg extends SpawnEggItem {

	public BugSpawnEgg(EntityType<? extends MobEntity> type, int primaryColor, int secondaryColor) {
		super(type, primaryColor, secondaryColor, new Item.Settings().maxCount(1).group(ArachnidsMod.ArachnidsItemGroup));
	}

}