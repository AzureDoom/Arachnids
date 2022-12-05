package mod.azure.arachnids.items.ammo;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import mod.azure.arachnids.client.render.mobs.projectiles.TonBlockItemRender;
import mod.azure.arachnids.entity.projectiles.TacticalOxygenNukeEntity;
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
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TONItem extends BlockItem implements GeoItem {
	
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public TONItem(Block block) {
		super(block, new Item.Properties());
	}

	// Utilise our own render hook to define our custom renderer
	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final TonBlockItemRender renderer = new TonBlockItemRender();

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return this.renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return this.renderProvider;
	}

	// Register our animation controllers
	@Override
	public void registerControllers(AnimatableManager<?> manager) {
		manager.addController(new AnimationController<>(this, "shoot_controller", event -> PlayState.CONTINUE));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return false;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		ItemStack itemStack = user.getItemInHand(hand);
		if (!user.getCooldowns().isOnCooldown(this)
				&& user.getMainHandItem().getItem() instanceof TONItem) {
			user.getCooldowns().addCooldown(this, 25);
			if (!world.isClientSide()) {
				TacticalOxygenNukeEntity snowballEntity = new TacticalOxygenNukeEntity(world, user, true);
				snowballEntity.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.0F, 1.0F);
				snowballEntity.moveTo(user.getX(), user.getY(0.95), user.getZ(), 0, 0);
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
		tooltip.add(Component.translatable("arachnids.tooltip.tontootip").withStyle(ChatFormatting.ITALIC));
	}

}