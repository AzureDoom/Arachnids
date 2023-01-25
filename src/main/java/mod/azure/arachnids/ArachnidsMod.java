package mod.azure.arachnids;

import eu.midnightdust.lib.config.MidnightConfig;
import mod.azure.arachnids.blocks.MZ90Block;
import mod.azure.arachnids.blocks.TONBlock;
import mod.azure.arachnids.blocks.TickingLightBlock;
import mod.azure.arachnids.blocks.TickingLightEntity;
import mod.azure.arachnids.config.ArachnidsConfig;
import mod.azure.arachnids.enchantment.FlareEnchantment;
import mod.azure.arachnids.enchantment.GrenadeEnchantment;
import mod.azure.arachnids.enchantment.SnipingEnchantment;
import mod.azure.arachnids.items.weapons.BaseGunItem;
import mod.azure.arachnids.items.weapons.M55Item;
import mod.azure.arachnids.util.ArachnidsItems;
import mod.azure.arachnids.util.ArachnidsSounds;
import mod.azure.arachnids.util.ArachnidsVillagerTrades;
import mod.azure.arachnids.util.MobAttributes;
import mod.azure.arachnids.util.MobSpawn;
import mod.azure.arachnids.util.ProjectilesEntityRegister;
import mod.azure.azurelib.AzureLib;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ArachnidsMod implements ModInitializer {

	public static ArachnidsItems ITEMS;
	public static ArachnidsSounds SOUNDS;
	public static final String MODID = "arachnids";
	public static ProjectilesEntityRegister PROJECTILES;
	public static final Block TONBLOCK = new TONBlock();
	public static final Block MZ90BLOCK = new MZ90Block();
	public static BlockEntityType<TickingLightEntity> TICKING_LIGHT_ENTITY;
	public static final TickingLightBlock TICKING_LIGHT_BLOCK = new TickingLightBlock();
	public static final ResourceLocation RELOAD_BULLETS = new ResourceLocation(MODID, "reload_bullets");
	public static final ResourceLocation RELOAD_TON = new ResourceLocation(MODID, "reload_ton");
	public static final Enchantment SNIPERATTACHMENT = new SnipingEnchantment(Rarity.RARE, EquipmentSlot.MAINHAND);
	public static final Enchantment GRENADEATTACHMENT = new GrenadeEnchantment(Rarity.RARE, EquipmentSlot.MAINHAND);
	public static final Enchantment FLAREATTACHMENT = new FlareEnchantment(Rarity.RARE, EquipmentSlot.MAINHAND);
	public static final CreativeModeTab GENERAL = FabricItemGroup
			.builder(new ResourceLocation(ArachnidsMod.MODID, "itemgroup"))
			.icon(() -> new ItemStack(ArachnidsItems.MZ90))
			.displayItems((enabledFeatures, entries, operatorEnabled) -> {
				entries.accept(ArachnidsItems.MAR1);
				entries.accept(ArachnidsItems.MAR2);
				entries.accept(ArachnidsItems.M55);
				entries.accept(ArachnidsItems.BULLETS);
				entries.accept(ArachnidsItems.TON);
				entries.accept(ArachnidsItems.MZ90);
				entries.accept(ArachnidsItems.FLARE);
				entries.accept(ArachnidsItems.MI_HELMET);
				entries.accept(ArachnidsItems.MI_CHESTPLATE);
				entries.accept(ArachnidsItems.MI_LEGGINGS);
				entries.accept(ArachnidsItems.MI_BOOTS);
				entries.accept(ArachnidsItems.ARKELLIANBUG_SPAWN_EGG);
				entries.accept(ArachnidsItems.WORKERBUG_SPAWN_EGG);
				entries.accept(ArachnidsItems.WARRIORBUG_SPAWN_EGG);
				entries.accept(ArachnidsItems.HOOPERBUG_SPAWN_EGG);
				entries.accept(ArachnidsItems.PLAMSABUG_SPAWN_EGG);
				entries.accept(ArachnidsItems.SCORPIONBUG_SPAWN_EGG);
				entries.accept(ArachnidsItems.TANKERBUG_SPAWN_EGG);
				entries.accept(ArachnidsItems.BRAINBUG_SPAWN_EGG);
			}).build();

	@Override
	public void onInitialize() {
		MidnightConfig.init(MODID, ArachnidsConfig.class);
		Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(MODID, "mz90"), MZ90BLOCK);
		Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(MODID, "ton"), TONBLOCK);
		Registry.register(BuiltInRegistries.ENCHANTMENT, new ResourceLocation(MODID, "grenadeattachment"),
				GRENADEATTACHMENT);
		Registry.register(BuiltInRegistries.ENCHANTMENT, new ResourceLocation(MODID, "sniperattachment"),
				SNIPERATTACHMENT);
		Registry.register(BuiltInRegistries.ENCHANTMENT, new ResourceLocation(MODID, "flareattachment"),
				FLAREATTACHMENT);
		ITEMS = new ArachnidsItems();
		SOUNDS = new ArachnidsSounds();
		PROJECTILES = new ProjectilesEntityRegister();
		Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(MODID, "lightblock"), TICKING_LIGHT_BLOCK);
		TICKING_LIGHT_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, MODID + ":lightblock",
				FabricBlockEntityTypeBuilder.create(TickingLightEntity::new, TICKING_LIGHT_BLOCK).build(null));
		AzureLib.initialize();
		MobSpawn.addSpawnEntries();
		MobAttributes.init();
		ServerLifecycleEvents.SERVER_STARTED.register(minecraftServer -> ArachnidsVillagerTrades.addTrades());
		ServerPlayNetworking.registerGlobalReceiver(ArachnidsMod.RELOAD_BULLETS,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof BaseGunItem) {
						((BaseGunItem) player.getMainHandItem().getItem()).reloadBullets(player,
								InteractionHand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(ArachnidsMod.RELOAD_TON,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof M55Item) {
						((M55Item) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
					}
					;
				});
	}
}
