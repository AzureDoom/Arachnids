package mod.azure.arachnids.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.authlib.GameProfile;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.client.ArachnidsClientInit;
import mod.azure.arachnids.util.ArachnidsItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerEntityMixin extends Player {

	public AbstractClientPlayerEntityMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile) {
		super(level, blockPos, f, gameProfile);
	}

	@Inject(at = @At("HEAD"), method = "getFieldOfViewModifier", cancellable = true)
	private void render(CallbackInfoReturnable<Float> ci) {
		ItemStack itemStack = this.getMainHandItem();
		if (Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
			if (itemStack.is(ArachnidsItems.MAR1) || itemStack.is(ArachnidsItems.MAR2)
					&& EnchantmentHelper.getItemEnchantmentLevel(ArachnidsMod.SNIPERATTACHMENT, itemStack) > 0) {
				ci.setReturnValue(ArachnidsClientInit.scope.isDown() ? 0.1F : 1.0F);
			}
		}
	}
}
