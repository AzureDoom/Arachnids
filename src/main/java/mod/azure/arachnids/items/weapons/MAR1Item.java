package mod.azure.arachnids.items.weapons;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.client.render.weapons.MAR1Render;
import mod.azure.arachnids.config.ArachnidsConfig;
import mod.azure.arachnids.entity.projectiles.BulletEntity;
import mod.azure.arachnids.util.ArachnidsItems;
import mod.azure.arachnids.util.ArachnidsSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;

public class MAR1Item extends BaseGunItem {
	
	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public MAR1Item() {
		super(new Item.Properties().stacksTo(1).durability((ArachnidsConfig.MAR1_max_ammo + 1)));

		// Register our item as server-side handled.
		// This enables both animation data syncing and server-side animation triggering
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public void onUseTick(Level worldIn, LivingEntity entityLiving, ItemStack stack, int count) {
		if (entityLiving instanceof Player) {
			Player playerentity = (Player) entityLiving;
			if (stack.getDamageValue() < (stack.getMaxDamage() - 1)
					&& !playerentity.getCooldowns().isOnCooldown(this)) {
				if (!worldIn.isClientSide()) {
					AbstractArrow abstractarrowentity;
					if (playerentity.getOffhandItem().getItem() == ArachnidsItems.MZ90
							&& EnchantmentHelper.getItemEnchantmentLevel(ArachnidsMod.GRENADEATTACHMENT, stack) > 0) {
						abstractarrowentity = createMZ90(worldIn, stack, playerentity);
						abstractarrowentity.shootFromRotation(playerentity, playerentity.getXRot(),
								playerentity.getYRot(), 0.0F, 0.5F * 3.0F, 1.0F);
						worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(),
								ArachnidsSounds.GRENADELAUNCHER, SoundSource.PLAYERS, 0.25F,
								1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
						removeOffHandItem(ArachnidsItems.MZ90, playerentity);
						playerentity.getCooldowns().addCooldown(this, 8);
					} else if (playerentity.getOffhandItem().getItem() == ArachnidsItems.FLARE
							&& EnchantmentHelper.getItemEnchantmentLevel(ArachnidsMod.FLAREATTACHMENT, stack) > 0) {
						abstractarrowentity = createFlare(worldIn, stack, playerentity);
						abstractarrowentity.shootFromRotation(playerentity, playerentity.getXRot(),
								playerentity.getYRot(), 0.0F, 0.5F * 3.0F, 1.0F);
						worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(),
								ArachnidsSounds.FLAREGUN, SoundSource.PLAYERS, 0.25F,
								1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
						removeOffHandItem(ArachnidsItems.FLARE, playerentity);
						playerentity.getCooldowns().addCooldown(this, 8);
					} else {
						abstractarrowentity = createBullet(worldIn, stack, playerentity);
						abstractarrowentity.shootFromRotation(playerentity, playerentity.getXRot(),
								playerentity.getYRot(), 0.0F, 1.0F * 3.0F, 1.0F);
						if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) > 0) {
							abstractarrowentity.setSecondsOnFire(100);
						}
						worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(),
								ArachnidsSounds.MAR1FIRE, SoundSource.PLAYERS, 0.25F,
								1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
						playerentity.getCooldowns().addCooldown(this, 3);
						stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));

						// Trigger our animation
						// We could trigger this outside of the client-side check if only wanted the animation to play for the shooter
						// But we'll fire it on the server so all nearby players can see it
						triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel)worldIn), "shoot_controller", "firing");
					}
					worldIn.addFreshEntity(abstractarrowentity);
				}
				boolean isInsideWaterBlock = playerentity.level.isWaterAt(playerentity.blockPosition());
				spawnLightSource(entityLiving, isInsideWaterBlock);
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
		float j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
		super.appendHoverText(stack, world, tooltip, context);
		tooltip.add(
				Component.translatable("Damage: " + (j > 0 ? (ArachnidsConfig.MAR1_bullet_damage + (j * 1.5F + 0.5F))
						: ArachnidsConfig.MAR2_bullet_damage)).withStyle(ChatFormatting.ITALIC));
	}

	public BulletEntity createBullet(Level worldIn, ItemStack stack, LivingEntity shooter) {
		float j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
		BulletEntity arrowentity = new BulletEntity(worldIn, shooter,
				j > 0 ? (ArachnidsConfig.MAR1_bullet_damage + (j * 1.5F + 0.5F)) : ArachnidsConfig.MAR2_bullet_damage);
		return arrowentity;
	}
	
	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final MAR1Render renderer = new MAR1Render();

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

}
