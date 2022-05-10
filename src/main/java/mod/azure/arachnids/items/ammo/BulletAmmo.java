package mod.azure.arachnids.items.ammo;

import java.util.List;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.entity.projectiles.BulletEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class BulletAmmo extends ArrowItem {

	public final float damage;

	public BulletAmmo(float damageIn) {
		super(new Item.Settings().group(ArachnidsMod.ArachnidsItemGroup));
		this.damage = damageIn;
	}

	@Override
	public BulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		BulletEntity arrowentity = new BulletEntity(worldIn, shooter, damage);
		arrowentity.setDamage(this.damage);
		return arrowentity;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("arachnids.tooltip.762ammo").formatted(Formatting.ITALIC));
	}

}