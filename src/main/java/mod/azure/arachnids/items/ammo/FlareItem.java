package mod.azure.arachnids.items.ammo;

import java.util.List;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.projectiles.FlareEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class FlareItem extends Item {

	public FlareItem() {
		super(new Item.Settings().group(ArachnidsMod.ArachnidsItemGroup));
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		if (!user.getItemCooldownManager().isCoolingDown(this)
				&& user.getMainHandStack().getItem() instanceof FlareItem) {
			user.getItemCooldownManager().set(this, 25);
			if (!world.isClient) {
				FlareEntity snowballEntity = new FlareEntity(world, itemStack, user, false);
				snowballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 0.5F * 3.0F, 1.0F);
				world.spawnEntity(snowballEntity);
			}
			if (!user.getAbilities().creativeMode) {
				itemStack.decrement(1);
			}
			return TypedActionResult.success(itemStack, world.isClient());
		} else {
			return TypedActionResult.fail(itemStack);
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("arachnids.tooltip.flaretootip").formatted(Formatting.ITALIC));
	}

}
