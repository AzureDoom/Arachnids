package mod.azure.arachnids.mixin;

import com.mojang.authlib.GameProfile;
import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.client.ArachnidsClientInit;
import mod.azure.arachnids.util.ArachnidsItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {

  public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw,
                                         GameProfile profile,
                                         PlayerPublicKey playerPublicKey) {
    super(world, pos, yaw, profile, playerPublicKey);
  }

  @Inject(at = @At("HEAD"), method = "getFovMultiplier", cancellable = true)
  private void render(CallbackInfoReturnable<Float> ci) {
    ItemStack itemStack = this.getMainHandStack();
    if (MinecraftClient.getInstance()
            .options.getPerspective()
            .isFirstPerson()) {
      if (itemStack.isOf(ArachnidsItems.MAR1) ||
          itemStack.isOf(ArachnidsItems.MAR2) &&
              EnchantmentHelper.getLevel(ArachnidsMod.SNIPERATTACHMENT,
                                         itemStack) > 0) {
        ci.setReturnValue(ArachnidsClientInit.scope.isPressed() ? 0.1F : 1.0F);
      }
    }
  }
}
