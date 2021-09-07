package mod.azure.arachnids.items.weapons;

import java.util.List;

import io.netty.buffer.Unpooled;
import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.client.ArachnidsClientInit;
import mod.azure.arachnids.entity.projectiles.BulletEntity;
import mod.azure.arachnids.util.ArachnidsItems;
import mod.azure.arachnids.util.ArachnidsSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class MAR2Item extends BaseGunItem {

	public MAR2Item() {
		super(new Item.Settings().group(ArachnidsMod.ArachnidsItemGroup).maxCount(1)
				.maxDamage((config.MAR2_max_ammo + 1)));
	}

	@Override
	public void usageTick(World worldIn, LivingEntity entityLiving, ItemStack stack, int count) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			if (stack.getDamage() < (stack.getMaxDamage() - 1)
					&& !playerentity.getItemCooldownManager().isCoolingDown(this)) {
				if (!worldIn.isClient) {
					PersistentProjectileEntity abstractarrowentity;
					if (playerentity.getOffHandStack().getItem() == ArachnidsItems.MZ90) {
						abstractarrowentity = createMZ90(worldIn, stack, playerentity);
						abstractarrowentity.setProperties(playerentity, playerentity.getPitch(), playerentity.getYaw(),
								0.0F, 0.5F * 3.0F, 1.0F);
						worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(),
								playerentity.getZ(), ArachnidsSounds.GRENADELAUNCHER, SoundCategory.PLAYERS, 0.25F,
								1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
						removeOffHandItem(ArachnidsItems.MZ90, playerentity);
						playerentity.getItemCooldownManager().set(this, 8);
					} else if (playerentity.getOffHandStack().getItem() == ArachnidsItems.FLARE
							&& EnchantmentHelper.getLevel(ArachnidsMod.FLAREATTACHMENT, stack) > 0) {
						abstractarrowentity = createFlare(worldIn, stack, playerentity);
						abstractarrowentity.setProperties(playerentity, playerentity.getPitch(), playerentity.getYaw(),
								0.0F, 0.5F * 3.0F, 1.0F);
						worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(),
								playerentity.getZ(), ArachnidsSounds.FLAREGUN, SoundCategory.PLAYERS, 0.25F,
								1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
						removeOffHandItem(ArachnidsItems.FLARE, playerentity);
						playerentity.getItemCooldownManager().set(this, 8);
					} else {
						abstractarrowentity = createBullet(worldIn, stack, playerentity);
						abstractarrowentity.setProperties(playerentity, playerentity.getPitch(), playerentity.getYaw(),
								0.0F, 1.0F * 3.0F, 1.0F);
						if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
							abstractarrowentity.setOnFireFor(100);
						}
						worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(),
								playerentity.getZ(), ArachnidsSounds.MAR2FIRE, SoundCategory.PLAYERS, 0.25F,
								1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
						stack.damage(1, entityLiving, p -> p.sendToolBreakStatus(entityLiving.getActiveHand()));
						playerentity.getItemCooldownManager().set(this, 3);
					}
					worldIn.spawnEntity(abstractarrowentity);
					final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerWorld) worldIn);
					GeckoLibNetwork.syncAnimation(playerentity, this, id, ANIM_OPEN);
					for (PlayerEntity otherPlayer : PlayerLookup.tracking(playerentity)) {
						GeckoLibNetwork.syncAnimation(otherPlayer, this, id, ANIM_OPEN);
					}
				}
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (world.isClient) {
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof MAR2Item) {
				if (ArachnidsClientInit.reload.isPressed() && selected) {
					PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
					passedData.writeBoolean(true);
					ClientPlayNetworking.send(ArachnidsMod.MAR2, passedData);
				}
			}
		}
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof MAR2Item) {
			while (user.getStackInHand(hand).getDamage() != 0
					&& user.getInventory().count(ArachnidsItems.BULLETS) > 0) {
				removeAmmo(ArachnidsItems.BULLETS, user);
				user.getStackInHand(hand).damage(-config.MAR2_mag_size, user, s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setCooldown(3);
				user.getEntityWorld().playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(),
						ArachnidsSounds.CLIPRELOAD, SoundCategory.PLAYERS, 1.00F, 1.0F);
			}
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		float j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
		super.appendTooltip(stack, world, tooltip, context);
		tooltip.add(new TranslatableText(
				"Damage: " + (j > 0 ? (config.MAR2_bullet_damage + (j * 1.5F + 0.5F)) : config.MAR2_bullet_damage))
						.formatted(Formatting.ITALIC));
	}

	public BulletEntity createBullet(World worldIn, ItemStack stack, LivingEntity shooter) {
		float j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
		BulletEntity arrowentity = new BulletEntity(worldIn, shooter,
				j > 0 ? (config.MAR2_bullet_damage + (j * 1.5F + 0.5F)) : config.MAR2_bullet_damage);
		return arrowentity;
	}

}
