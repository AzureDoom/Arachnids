package mod.azure.arachnids.items.ammo;

import java.util.List;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.projectiles.MZ90Entity;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class MZ90Item extends BlockItem implements IAnimatable {

	public AnimationFactory factory = new AnimationFactory(this);
	public String controllerName = "controller";

	public MZ90Item(Block block) {
		super(block, new Item.Settings().group(ArachnidsMod.ArachnidsItemGroup));
	}

	public <P extends BlockItem & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		return PlayState.CONTINUE;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController(this, controllerName, 1, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		if (!user.getItemCooldownManager().isCoolingDown(this)
				&& user.getMainHandStack().getItem() instanceof MZ90Item) {
			user.getItemCooldownManager().set(this, 25);
			if (!world.isClient) {
				MZ90Entity snowballEntity = new MZ90Entity(world, user, true);
				snowballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.0F, 1.0F);
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
		tooltip.add(Text.translatable("arachnids.tooltip.mz90tootip").formatted(Formatting.ITALIC));
	}

}
