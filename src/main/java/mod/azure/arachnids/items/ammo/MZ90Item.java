package mod.azure.arachnids.items.ammo;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import mod.azure.arachnids.client.render.mobs.projectiles.MZ90BlockItemRender;
import mod.azure.arachnids.entity.projectiles.MZ90Entity;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class MZ90Item extends BlockItem implements GeoItem {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public MZ90Item(Block block) {
		super(block, new Item.Properties());
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private MZ90BlockItemRender renderer = null;

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				if (renderer == null)
					return new MZ90BlockItemRender();
				return this.renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return this.renderProvider;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "shoot_controller", event -> PlayState.CONTINUE));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		var itemStack = user.getItemInHand(hand);
		if (!user.getCooldowns().isOnCooldown(this) && user.getMainHandItem().getItem() instanceof MZ90Item) {
			user.getCooldowns().addCooldown(this, 25);
			if (!world.isClientSide()) {
				var nade = new MZ90Entity(world, user, true);
				nade.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.0F, 1.0F);
				world.addFreshEntity(nade);
			}
			if (!user.getAbilities().instabuild)
				itemStack.shrink(1);
			return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
		} else
			return InteractionResultHolder.fail(itemStack);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
		tooltip.add(Component.translatable("arachnids.tooltip.mz90tootip").withStyle(ChatFormatting.ITALIC));
	}

}
