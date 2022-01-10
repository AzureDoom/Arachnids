package mod.azure.arachnids.items.weapons;

import java.util.List;

import io.netty.buffer.Unpooled;
import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.client.ArachnidsClientInit;
import mod.azure.arachnids.config.ArachnidsConfig.Weapons;
import mod.azure.arachnids.entity.projectiles.TacticalOxygenNukeEntity;
import mod.azure.arachnids.util.ArachnidsItems;
import mod.azure.arachnids.util.ArachnidsSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class M55Item extends Item {

	public static Weapons config = ArachnidsMod.config.weapons;

	public M55Item() {
		super(new Item.Settings().group(ArachnidsMod.ArachnidsItemGroup).maxCount(1).maxDamage(2));
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int remainingUseTicks) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			if (stack.getDamage() < (stack.getMaxDamage() - 1)) {
				playerentity.getItemCooldownManager().set(this, 15);
				if (!worldIn.isClient) {
					TacticalOxygenNukeEntity abstractarrowentity = createNuke(worldIn, stack, playerentity);
					abstractarrowentity.setVelocity(playerentity, playerentity.getPitch(), playerentity.getYaw(),
							0.0F, 0.75F * 3.0F, 1.0F);
					abstractarrowentity.refreshPositionAndAngles(entityLiving.getX(), entityLiving.getBodyY(0.95),
							entityLiving.getZ(), 0, 0);
					stack.damage(1, entityLiving, p -> p.sendToolBreakStatus(entityLiving.getActiveHand()));
					worldIn.spawnEntity(abstractarrowentity);
					worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(),
							playerentity.getZ(), ArachnidsSounds.M55FIRE, SoundCategory.PLAYERS, 1.0F,
							1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
				}
			}
		}
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof M55Item) {
			while (user.getStackInHand(hand).getDamage() != 0 && user.getInventory().count(ArachnidsItems.TON) > 0) {
				removeAmmo(ArachnidsItems.TON, user);
				user.getStackInHand(hand).damage(-2, user, s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setBobbingAnimationTime(3);
				user.getEntityWorld().playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(),
						ArachnidsSounds.M55RELOAD, SoundCategory.PLAYERS, 0.5F, 1.0F);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (world.isClient) {
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof M55Item
					&& ArachnidsClientInit.reload.isPressed() && selected) {
				PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
				passedData.writeBoolean(true);
				ClientPlayNetworking.send(ArachnidsMod.RELOAD_TON, passedData);
			}
		}
	}

	public TacticalOxygenNukeEntity createNuke(World worldIn, ItemStack stack, LivingEntity shooter) {
		TacticalOxygenNukeEntity arrowentity = new TacticalOxygenNukeEntity(worldIn, shooter, false);
		return arrowentity;
	}

	public void removeAmmo(Item ammo, PlayerEntity playerEntity) {
		if (!playerEntity.isCreative()) {
			for (ItemStack item : playerEntity.getInventory().offHand) {
				if (item.getItem() == ammo) {
					item.decrement(1);
					break;
				}
				for (ItemStack item1 : playerEntity.getInventory().main) {
					if (item1.getItem() == ammo) {
						item1.decrement(1);
						break;
					}
				}
			}
		}
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		user.setCurrentHand(hand);
		return TypedActionResult.consume(itemStack);
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return false;
	}

	@Override
	public int getEnchantability() {
		return 0;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 72000;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText(
				"Ammo: " + (stack.getMaxDamage() - stack.getDamage() - 1) + " / " + (stack.getMaxDamage() - 1))
						.formatted(Formatting.ITALIC));
	}

}
