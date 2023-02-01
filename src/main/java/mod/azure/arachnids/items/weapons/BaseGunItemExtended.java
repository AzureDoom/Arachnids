package mod.azure.arachnids.items.weapons;

import io.netty.buffer.Unpooled;
import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.client.ArachnidsClientInit;
import mod.azure.arachnids.config.ArachnidsConfig;
import mod.azure.arachnids.entity.projectiles.BulletEntity;
import mod.azure.arachnids.entity.projectiles.FlareEntity;
import mod.azure.arachnids.entity.projectiles.MZ90Entity;
import mod.azure.arachnids.util.ArachnidsItems;
import mod.azure.arachnids.util.ArachnidsSounds;
import mod.azure.azurelib.items.BaseGunItem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public abstract class BaseGunItemExtended extends BaseGunItem {

	public BaseGunItemExtended(Properties properties) {
		super(properties);
	}

	public MZ90Entity createMZ90(Level worldIn, ItemStack stack, LivingEntity shooter) {
		MZ90Entity arrowentity = new MZ90Entity(worldIn, shooter, false);
		return arrowentity;
	}

	public FlareEntity createFlare(Level worldIn, ItemStack stack, LivingEntity shooter) {
		FlareEntity arrowentity = new FlareEntity(worldIn, stack, shooter, true);
		return arrowentity;
	}

	public BulletEntity createBullet(Level worldIn, ItemStack stack, LivingEntity shooter, float bulletDamage) {
		float j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
		BulletEntity arrowentity = new BulletEntity(worldIn, shooter,
				j > 0 ? (bulletDamage + (j * 1.5F + 0.5F)) : bulletDamage);
		return arrowentity;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (world.isClientSide()) {
			if (((Player) entity).getMainHandItem().getItem() instanceof BaseGunItemExtended) {
				if (ArachnidsClientInit.reload.isDown() && selected) {
					FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
					passedData.writeBoolean(true);
					ClientPlayNetworking.send(ArachnidsMod.RELOAD_BULLETS, passedData);
				}
			}
		}
	}

	public void reloadBullets(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof BaseGunItemExtended) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0
					&& user.getInventory().countItem(ArachnidsItems.BULLETS) > 0) {
				removeAmmo(ArachnidsItems.BULLETS, user);
				user.getItemInHand(hand).hurtAndBreak(-ArachnidsConfig.MAR1_mag_size, user,
						s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
				user.getCommandSenderWorld().playSound((Player) null, user.getX(), user.getY(), user.getZ(),
						ArachnidsSounds.CLIPRELOAD, SoundSource.PLAYERS, 1.00F, 1.0F);
			}
		}
	}

}