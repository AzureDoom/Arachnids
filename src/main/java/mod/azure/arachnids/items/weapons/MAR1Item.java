package mod.azure.arachnids.items.weapons;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.client.render.weapons.MAR1Render;
import mod.azure.arachnids.util.ArachnidsItems;
import mod.azure.arachnids.util.ArachnidsSounds;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.items.BaseGunItem;
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

public class MAR1Item extends BaseGunItemExtended {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public MAR1Item() {
		super(new Item.Properties().stacksTo(1).durability((ArachnidsMod.config.MAR1_max_ammo + 1)));
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public void onUseTick(Level worldIn, LivingEntity entityLiving, ItemStack stack, int count) {
		if (entityLiving instanceof Player) {
			var playerentity = (Player) entityLiving;
			if (stack.getDamageValue() < (stack.getMaxDamage() - 1) && !playerentity.getCooldowns().isOnCooldown(this)) {
				if (!worldIn.isClientSide()) {
					AbstractArrow projectile;
					if (playerentity.getOffhandItem().getItem() == ArachnidsItems.MZ90 && EnchantmentHelper.getItemEnchantmentLevel(ArachnidsMod.GRENADEATTACHMENT, stack) > 0) {
						projectile = createMZ90(worldIn, stack, playerentity);
						projectile.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(), 0.0F, 0.5F * 3.0F, 1.0F);
						worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), ArachnidsSounds.GRENADELAUNCHER, SoundSource.PLAYERS, 0.25F, 1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
						removeOffHandItem(ArachnidsItems.MZ90, playerentity);
						playerentity.getCooldowns().addCooldown(this, 8);
						worldIn.addFreshEntity(projectile);
					} else if (playerentity.getOffhandItem().getItem() == ArachnidsItems.FLARE && EnchantmentHelper.getItemEnchantmentLevel(ArachnidsMod.FLAREATTACHMENT, stack) > 0) {
						projectile = createFlare(worldIn, stack, playerentity);
						projectile.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(), 0.0F, 0.5F * 3.0F, 1.0F);
						worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), ArachnidsSounds.FLAREGUN, SoundSource.PLAYERS, 0.25F, 1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
						removeOffHandItem(ArachnidsItems.FLARE, playerentity);
						playerentity.getCooldowns().addCooldown(this, 8);
						worldIn.addFreshEntity(projectile);
					} else {
						var result = BaseGunItem.hitscanTrace(playerentity, 64, 1.0F);
						var enchantlevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
						if (result != null) {
							if (result.getEntity()instanceof LivingEntity livingEntity)
								livingEntity.hurt(playerentity.damageSources().playerAttack(playerentity), (enchantlevel > 0 ? (ArachnidsMod.config.MAR1_bullet_damage + (enchantlevel * 1.5F + 0.5F)) : ArachnidsMod.config.MAR1_bullet_damage));
						} else {
							projectile = createBullet(worldIn, stack, playerentity, ArachnidsMod.config.MAR1_bullet_damage);
							projectile.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(), 0.0F, 20.0F, 1.0F);
							if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) > 0)
								projectile.setSecondsOnFire(100);
							worldIn.addFreshEntity(projectile);
						}
						worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), ArachnidsSounds.MAR1FIRE, SoundSource.PLAYERS, 0.25F, 1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
						playerentity.getCooldowns().addCooldown(this, 3);
						stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
						triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) worldIn), "shoot_controller", "firing");
					}
				}
				var isInsideWaterBlock = playerentity.level().isWaterAt(playerentity.blockPosition());
				spawnLightSource(entityLiving, isInsideWaterBlock);
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
		var enchantlevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
		super.appendHoverText(stack, world, tooltip, context);
		tooltip.add(Component.translatable("Damage: " + (enchantlevel > 0 ? (ArachnidsMod.config.MAR1_bullet_damage + (enchantlevel * 1.5F + 0.5F)) : ArachnidsMod.config.MAR1_bullet_damage)).withStyle(ChatFormatting.ITALIC));
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private MAR1Render renderer = null;

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				if (renderer == null)
					return new MAR1Render();
				return this.renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return this.renderProvider;
	}

}
