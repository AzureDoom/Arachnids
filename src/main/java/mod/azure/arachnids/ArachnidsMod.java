package mod.azure.arachnids;

//import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import mod.azure.arachnids.blocks.MZ90Block;
import mod.azure.arachnids.blocks.TONBlock;
import mod.azure.arachnids.blocks.TickingLightBlock;
import mod.azure.arachnids.blocks.TickingLightEntity;
import mod.azure.arachnids.config.ArachnidsConfig;
import mod.azure.arachnids.config.CustomMidnightConfig;
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
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.GeckoLib;

public class ArachnidsMod implements ModInitializer {

	public static ArachnidsItems ITEMS;
	public static ArachnidsSounds SOUNDS;
	public static final String MODID = "arachnids";
	public static ProjectilesEntityRegister PROJECTILES;
	public static final Block TONBLOCK = new TONBlock();
	public static final Block MZ90BLOCK = new MZ90Block();
	public static BlockEntityType<TickingLightEntity> TICKING_LIGHT_ENTITY;
	public static final TickingLightBlock TICKING_LIGHT_BLOCK = new TickingLightBlock();
	public static final Identifier RELOAD_BULLETS = new Identifier(MODID, "reload_bullets");
	public static final Identifier RELOAD_TON = new Identifier(MODID, "reload_ton");
	public static final Enchantment SNIPERATTACHMENT = new SnipingEnchantment(Rarity.RARE, EquipmentSlot.MAINHAND);
	public static final Enchantment GRENADEATTACHMENT = new GrenadeEnchantment(Rarity.RARE, EquipmentSlot.MAINHAND);
	public static final Enchantment FLAREATTACHMENT = new FlareEnchantment(Rarity.RARE, EquipmentSlot.MAINHAND);
	public static final ItemGroup ArachnidsItemGroup = FabricItemGroupBuilder.create(new Identifier(MODID, "itemgroup"))
			.icon(() -> new ItemStack(ArachnidsItems.MZ90)).build();

	@Override
	public void onInitialize() {
		CustomMidnightConfig.init(MODID, ArachnidsConfig.class);
		Registry.register(Registry.BLOCK, new Identifier(MODID, "mz90"), MZ90BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(MODID, "ton"), TONBLOCK);
		Registry.register(Registry.ENCHANTMENT, new Identifier(MODID, "grenadeattachment"), GRENADEATTACHMENT);
		Registry.register(Registry.ENCHANTMENT, new Identifier(MODID, "sniperattachment"), SNIPERATTACHMENT);
		Registry.register(Registry.ENCHANTMENT, new Identifier(MODID, "flareattachment"), FLAREATTACHMENT);
		ITEMS = new ArachnidsItems();
		SOUNDS = new ArachnidsSounds();
		PROJECTILES = new ProjectilesEntityRegister();
		Registry.register(Registry.BLOCK, new Identifier(MODID, "lightblock"), TICKING_LIGHT_BLOCK);
		TICKING_LIGHT_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MODID + ":lightblock",
				FabricBlockEntityTypeBuilder.create(TickingLightEntity::new, TICKING_LIGHT_BLOCK).build(null));
		GeckoLib.initialize();
		MobSpawn.addSpawnEntries();
		MobAttributes.init();
		ServerLifecycleEvents.SERVER_STARTED.register(minecraftServer -> ArachnidsVillagerTrades.addTrades());
		ServerPlayNetworking.registerGlobalReceiver(ArachnidsMod.RELOAD_BULLETS,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof BaseGunItem) {
						((BaseGunItem) player.getMainHandStack().getItem()).reloadBullets(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(ArachnidsMod.RELOAD_TON,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof M55Item) {
						((M55Item) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
	}
}
