package mod.azure.arachnids.client;

import mod.azure.arachnids.ArachnidsMod;
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
import mod.azure.arachnids.client.render.mobs.projectiles.MZ90BlockItemRender;
import mod.azure.arachnids.client.render.mobs.projectiles.MZ90BlockRender;
import mod.azure.arachnids.client.render.mobs.projectiles.MZ90Render;
import mod.azure.arachnids.client.render.mobs.projectiles.TonBlockItemRender;
import mod.azure.arachnids.client.render.mobs.projectiles.TonBlockRender;
import mod.azure.arachnids.client.render.mobs.projectiles.TonEntityRender;
import mod.azure.arachnids.client.render.weapons.FlareRender;
import mod.azure.arachnids.client.render.weapons.MAR1Render;
import mod.azure.arachnids.client.render.weapons.MAR2Render;
import mod.azure.arachnids.entity.projectiles.FlareParticle;
import mod.azure.arachnids.network.EntityPacket;
import mod.azure.arachnids.network.EntityPacketOnClient;
import mod.azure.arachnids.util.ArachnidsItems;
import mod.azure.arachnids.util.ArachnidsMobs;
import mod.azure.arachnids.util.ProjectilesEntityRegister;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.InputUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

@SuppressWarnings("deprecation")
@Environment(EnvType.CLIENT)
public class ArachnidsClientInit implements ClientModInitializer {

  public static KeyBinding reload =
      new KeyBinding("key.arachnids.reload", InputUtil.Type.KEYSYM,
                     GLFW.GLFW_KEY_R, "category.arachnids.binds");

  public static KeyBinding scope =
      new KeyBinding("key.arachnids.scope", InputUtil.Type.KEYSYM,
                     GLFW.GLFW_KEY_LEFT_ALT, "category.arachnids.binds");

  @Override
  public void onInitializeClient() {
    KeyBindingHelper.registerKeyBinding(reload);
    KeyBindingHelper.registerKeyBinding(scope);
    GeoItemRenderer.registerItemRenderer(ArachnidsItems.MAR1, new MAR1Render());
    GeoItemRenderer.registerItemRenderer(ArachnidsItems.MAR2, new MAR2Render());
    GeoItemRenderer.registerItemRenderer(ArachnidsMod.MZ90BLOCK.asItem(),
                                         new MZ90BlockItemRender());
    GeoItemRenderer.registerItemRenderer(ArachnidsMod.TONBLOCK.asItem(),
                                         new TonBlockItemRender());
    EntityRendererRegistry.INSTANCE.register(ArachnidsMobs.MZ90,
                                             (ctx) -> new MZ90BlockRender(ctx));
    EntityRendererRegistry.INSTANCE.register(ArachnidsMobs.TON,
                                             (ctx) -> new TonBlockRender(ctx));
    EntityRendererRegistry.INSTANCE.register(ArachnidsMobs.ARKELLIANBUG,
                                             (ctx) -> new ArkellianRender(ctx));
    EntityRendererRegistry.INSTANCE.register(ArachnidsMobs.BRAINBUG,
                                             (ctx) -> new BrainRender(ctx));
    EntityRendererRegistry.INSTANCE.register(ArachnidsMobs.HOOPERBUG,
                                             (ctx) -> new HopperRender(ctx));
    EntityRendererRegistry.INSTANCE.register(ArachnidsMobs.PLASMABUG,
                                             (ctx) -> new PlasmaRender(ctx));
    EntityRendererRegistry.INSTANCE.register(ArachnidsMobs.SCORPIONBUG,
                                             (ctx) -> new ScorpionRender(ctx));
    EntityRendererRegistry.INSTANCE.register(ArachnidsMobs.TANKERBUG,
                                             (ctx) -> new TankerRender(ctx));
    EntityRendererRegistry.INSTANCE.register(ArachnidsMobs.WARRIORBUG,
                                             (ctx) -> new WarriorRender(ctx));
    EntityRendererRegistry.INSTANCE.register(ArachnidsMobs.WORKERBUG,
                                             (ctx) -> new WorkerRender(ctx));
    EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BULLETS,
                                             (ctx) -> new BulletRender(ctx));
    EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.MZ90,
                                             (ctx) -> new MZ90Render(ctx));
    EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.TON,
                                             (ctx) -> new TonEntityRender(ctx));
    EntityRendererRegistry.INSTANCE.register(
        ProjectilesEntityRegister.BUGPLASMA, (ctx) -> new BugPlasmaRender(ctx));
    requestParticleTexture(new Identifier("minecraft:particle/big_smoke_0"));
    EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.FLARE,
                                             (ctx) -> new FlareRender(ctx));
    ParticleFactoryRegistry.getInstance().register(
        ArachnidsParticles.FLARE, FlareParticle.RedSmokeFactory::new);
    ClientSidePacketRegistry.INSTANCE.register(EntityPacket.ID, (ctx, buf) -> {
      EntityPacketOnClient.onPacket(ctx, buf);
    });
    FabricModelPredicateProviderRegistry.register(
        ArachnidsItems.M55, new Identifier("broken"),
        (itemStack, clientWorld, livingEntity, seed) -> {
          return isUsable(itemStack) ? 0.0F : 1.0F;
        });
    FabricModelPredicateProviderRegistry.register(
        ArachnidsItems.MAR1, new Identifier("scoped"),
        (itemStack, clientWorld, livingEntity, seed) -> {
          if (livingEntity != null)
            return isScoped(itemStack) ? 1.0F : 0.0F;
          return 0.0F;
        });
    FabricModelPredicateProviderRegistry.register(
        ArachnidsItems.MAR2, new Identifier("scoped"),
        (itemStack, clientWorld, livingEntity, seed) -> {
          if (livingEntity != null)
            return isScoped(itemStack) ? 1.0F : 0.0F;
          return 0.0F;
        });
  }

  public static void requestParticleTexture(Identifier id) {
    ClientSpriteRegistryCallback
        .event(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE)
        .register(((texture, registry) -> registry.register(id)));
  }

  private static boolean isUsable(ItemStack stack) {
    return (stack.getDamage() < stack.getMaxDamage() - 1);
  }

  private static boolean isScoped(ItemStack stack) {
    return scope.isPressed() &&
        EnchantmentHelper.getLevel(ArachnidsMod.SNIPERATTACHMENT, stack) > 0;
  }
}