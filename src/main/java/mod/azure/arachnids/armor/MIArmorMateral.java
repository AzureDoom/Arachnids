package mod.azure.arachnids.armor;

import java.util.function.Supplier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public enum MIArmorMateral implements ArmorMaterial {

	MIARMOR("miarmor", 37, new int[] { 5, 2, 3, 1 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC, 8.0F, 0.4F, () -> {
		return Ingredient.of(Items.LEATHER);
	});

	private static final int[] BASE_DURABILITY = new int[] { 13, 15, 16, 11 };
	private final String name;
	private final int durabilityMultiplier;
	private final int[] protectionAmounts;
	private final int enchantability;
	private final SoundEvent equipSound;
	private final float toughness;
	private final float knockbackResistance;
	private final LazyLoadedValue<Ingredient> repairIngredient;

	private MIArmorMateral(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> supplier) {
		this.name = name;
		this.durabilityMultiplier = durabilityMultiplier;
		this.protectionAmounts = protectionAmounts;
		this.enchantability = enchantability;
		this.equipSound = equipSound;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairIngredient = new LazyLoadedValue<Ingredient>(supplier);
	}

	@Override
	public int getDurabilityForType(Type slot) {
		return BASE_DURABILITY[slot.getSlot().getIndex()] * this.durabilityMultiplier;
	}

	@Override
	public int getDefenseForType(Type slot) {
		return this.protectionAmounts[slot.getSlot().getIndex()];
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantability;
	}

	@Override
	public SoundEvent getEquipSound() {
		return this.equipSound;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return (Ingredient) this.repairIngredient.get();
	}

	@Override
	@Environment(EnvType.CLIENT)
	public String getName() {
		return this.name;
	}

	@Override
	public float getToughness() {
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}
}