package mod.azure.arachnids.client;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import mod.azure.arachnids.ArachnidsMod;
import mod.azure.arachnids.client.particles.FlareParticle;
import mod.azure.arachnids.client.render.mobs.ArkellianRender;
import mod.azure.arachnids.client.render.mobs.BrainRender;
import mod.azure.arachnids.client.render.mobs.HopperRender;
import mod.azure.arachnids.client.render.mobs.PlasmaRender;
import mod.azure.arachnids.client.render.mobs.ScorpionRender;
import mod.azure.arachnids.client.render.mobs.TankerRender;
import mod.azure.arachnids.client.render.mobs.WarriorRender;
import mod.azure.arachnids.client.render.mobs.WorkerRender;
import mod.azure.arachnids.client.render.mobs.projectiles.BugPlasmaRender;
import mod.azure.arachnids.client.render.mobs.projectiles.BulletRender;
import mod.azure.arachnids.client.render.mobs.projectiles.FlameFiringRender;
import mod.azure.arachnids.client.render.mobs.projectiles.MZ90BlockRender;
import mod.azure.arachnids.client.render.mobs.projectiles.MZ90Render;
import mod.azure.arachnids.client.render.mobs.projectiles.TonBlockRender;
import mod.azure.arachnids.client.render.mobs.projectiles.TonEntityRender;
import mod.azure.arachnids.client.render.weapons.FlareRender;
import mod.azure.arachnids.util.ArachnidsItems;
import mod.azure.arachnids.util.ArachnidsMobs;
import mod.azure.arachnids.util.ProjectilesEntityRegister;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

@Environment(EnvType.CLIENT)
public class ArachnidsClientInit implements ClientModInitializer {

	public static KeyMapping reload = new KeyMapping("key.arachnids.reload", InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_R, "category.arachnids.binds");

	public static KeyMapping scope = new KeyMapping("key.arachnids.scope", InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_LEFT_ALT, "category.arachnids.binds");

	public static final ResourceLocation PacketID = new ResourceLocation(ArachnidsMod.MODID, "spawn_packet");

	@Override
	public void onInitializeClient() {
		KeyBindingHelper.registerKeyBinding(reload);
		KeyBindingHelper.registerKeyBinding(scope);
		EntityRendererRegistry.register(ArachnidsMobs.MZ90, (ctx) -> new MZ90BlockRender(ctx));
		EntityRendererRegistry.register(ArachnidsMobs.TON, (ctx) -> new TonBlockRender(ctx));
		EntityRendererRegistry.register(ArachnidsMobs.ARKELLIANBUG, (ctx) -> new ArkellianRender(ctx));
		EntityRendererRegistry.register(ArachnidsMobs.BRAINBUG, (ctx) -> new BrainRender(ctx));
		EntityRendererRegistry.register(ArachnidsMobs.HOOPERBUG, (ctx) -> new HopperRender(ctx));
		EntityRendererRegistry.register(ArachnidsMobs.PLASMABUG, (ctx) -> new PlasmaRender(ctx));
		EntityRendererRegistry.register(ArachnidsMobs.SCORPIONBUG, (ctx) -> new ScorpionRender(ctx));
		EntityRendererRegistry.register(ArachnidsMobs.TANKERBUG, (ctx) -> new TankerRender(ctx));
		EntityRendererRegistry.register(ArachnidsMobs.WARRIORBUG, (ctx) -> new WarriorRender(ctx));
		EntityRendererRegistry.register(ArachnidsMobs.WORKERBUG, (ctx) -> new WorkerRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.BULLETS, (ctx) -> new BulletRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.MZ90, (ctx) -> new MZ90Render(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.TON, (ctx) -> new TonEntityRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.BUGPLASMA, (ctx) -> new BugPlasmaRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.FLARE, (ctx) -> new FlareRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.FIRING, (ctx) -> new FlameFiringRender(ctx));
		ParticleFactoryRegistry.getInstance().register(ArachnidsParticles.FLARE, FlareParticle.RedSmokeFactory::new);
		ItemProperties.register(ArachnidsItems.M55, new ResourceLocation("broken"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return isUsable(itemStack) ? 0.0F : 1.0F;
				});
		ItemProperties.register(ArachnidsItems.MAR1, new ResourceLocation("scoped"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					if (livingEntity != null)
						return isScoped(itemStack) ? 1.0F : 0.0F;
					return 0.0F;
				});
		ItemProperties.register(ArachnidsItems.MAR2, new ResourceLocation("scoped"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					if (livingEntity != null)
						return isScoped(itemStack) ? 1.0F : 0.0F;
					return 0.0F;
				});
	}

	private static boolean isUsable(ItemStack stack) {
		return (stack.getDamageValue() < stack.getMaxDamage() - 1);
	}

	private static boolean isScoped(ItemStack stack) {
		return scope.isDown() && EnchantmentHelper.getItemEnchantmentLevel(ArachnidsMod.SNIPERATTACHMENT, stack) > 0;
	}

}