package mod.azure.arachnids.armor;

import java.util.function.Supplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Lazy;

public enum MIArmorMateral implements ArmorMaterial {

  MIARMOR("miarmor", 37, new int[] {5, 2, 3, 1}, 30,
          SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 8.0F, 0.4F,
          () -> { return Ingredient.ofItems(Items.LEATHER); });

  private static final int[] BASE_DURABILITY = new int[] {13, 15, 16, 11};
  private final String name;
  private final int durabilityMultiplier;
  private final int[] protectionAmounts;
  private final int enchantability;
  private final SoundEvent equipSound;
  private final float toughness;
  private final float knockbackResistance;
  private final Lazy<Ingredient> repairIngredientSupplier;

  private MIArmorMateral(String name, int durabilityMultiplier,
                         int[] protectionAmounts, int enchantability,
                         SoundEvent equipSound, float toughness,
                         float knockbackResistance,
                         Supplier<Ingredient> supplier) {
    this.name = name;
    this.durabilityMultiplier = durabilityMultiplier;
    this.protectionAmounts = protectionAmounts;
    this.enchantability = enchantability;
    this.equipSound = equipSound;
    this.toughness = toughness;
    this.knockbackResistance = knockbackResistance;
    this.repairIngredientSupplier = new Lazy<Ingredient>(supplier);
  }

  public int getDurability(EquipmentSlot slot) {
    return BASE_DURABILITY[slot.getEntitySlotId()] * this.durabilityMultiplier;
  }

  public int getProtectionAmount(EquipmentSlot slot) {
    return this.protectionAmounts[slot.getEntitySlotId()];
  }

  public int getEnchantability() { return this.enchantability; }

  public SoundEvent getEquipSound() { return this.equipSound; }

  public Ingredient getRepairIngredient() {
    return (Ingredient)this.repairIngredientSupplier.get();
  }

  @Environment(EnvType.CLIENT)
  public String getName() {
    return this.name;
  }

  public float getToughness() { return this.toughness; }

  public float getKnockbackResistance() { return this.knockbackResistance; }
}