package mod.azure.arachnids.items;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

public class BugSpawnEgg extends SpawnEggItem {

	public BugSpawnEgg(EntityType<? extends Mob> type, int primaryColor, int secondaryColor) {
		super(type, primaryColor, secondaryColor, new Item.Properties().stacksTo(1));
	}

}