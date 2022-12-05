package mod.azure.arachnids.items.ammo;

import java.util.List;

import mod.azure.arachnids.entity.projectiles.FlareEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class FlareItem extends Item {

	public FlareItem() {
		super(new Item.Properties());
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		ItemStack itemStack = user.getItemInHand(hand);
		if (!user.getCooldowns().isOnCooldown(this)
				&& user.getMainHandItem().getItem() instanceof FlareItem) {
			user.getCooldowns().addCooldown(this, 25);
			if (!world.isClientSide()) {
				FlareEntity snowballEntity = new FlareEntity(world, itemStack, user, false);
				snowballEntity.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 0.5F * 3.0F, 1.0F);
				world.addFreshEntity(snowballEntity);
			}
			if (!user.getAbilities().instabuild) {
				itemStack.shrink(1);
			}
			return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
		} else {
			return InteractionResultHolder.fail(itemStack);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
		tooltip.add(Component.translatable("arachnids.tooltip.flaretootip").withStyle(ChatFormatting.ITALIC));
	}

}
