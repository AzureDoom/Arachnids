package mod.azure.arachnids.items.ammo;

import java.util.List;

import mod.azure.arachnids.entity.projectiles.BulletEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class BulletAmmo extends ArrowItem {

	public final float damage;

	public BulletAmmo(float damageIn) {
		super(new Item.Properties());
		this.damage = damageIn;
	}

	@Override
	public BulletEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		BulletEntity arrowentity = new BulletEntity(worldIn, shooter, damage);
		arrowentity.setBaseDamage(this.damage);
		return arrowentity;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
		tooltip.add(Component.translatable("arachnids.tooltip.762ammo").withStyle(ChatFormatting.ITALIC));
	}

}