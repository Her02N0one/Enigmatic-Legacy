package com.integral.enigmaticlegacy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.integral.enigmaticlegacy.api.items.IAdvancedPotionItem.PotionType;
import com.integral.enigmaticlegacy.api.materials.EnigmaticArmorMaterials;
import com.integral.enigmaticlegacy.blocks.BlockBigLamp;
import com.integral.enigmaticlegacy.blocks.BlockMassiveLamp;
import com.integral.enigmaticlegacy.brewing.SpecialBrewingRecipe;
import com.integral.enigmaticlegacy.brewing.ValidationBrewingRecipe;
import com.integral.enigmaticlegacy.config.ConfigHandler;
import com.integral.enigmaticlegacy.crafting.EnigmaticRecipeSerializers;
import com.integral.enigmaticlegacy.entities.EnigmaticPotionEntity;
import com.integral.enigmaticlegacy.entities.PermanentItemEntity;
import com.integral.enigmaticlegacy.entities.UltimateWitherSkullEntity;
import com.integral.enigmaticlegacy.handlers.EnigmaticEventHandler;
import com.integral.enigmaticlegacy.handlers.EnigmaticKeybindHandler;
import com.integral.enigmaticlegacy.handlers.EnigmaticUpdateHandler;
import com.integral.enigmaticlegacy.handlers.OneSpecialHandler;
import com.integral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.integral.enigmaticlegacy.helpers.ObfuscatedFields;
import com.integral.enigmaticlegacy.helpers.PotionHelper;
import com.integral.enigmaticlegacy.items.AngelBlessing;
import com.integral.enigmaticlegacy.items.AstralBreaker;
import com.integral.enigmaticlegacy.items.AstralDust;
import com.integral.enigmaticlegacy.items.EnchantmentTransposer;
import com.integral.enigmaticlegacy.items.EnderRing;
import com.integral.enigmaticlegacy.items.EnderRod;
import com.integral.enigmaticlegacy.items.EnigmaticAmulet;
import com.integral.enigmaticlegacy.items.EnigmaticItem;
import com.integral.enigmaticlegacy.items.EscapeScroll;
import com.integral.enigmaticlegacy.items.EtheriumArmor;
import com.integral.enigmaticlegacy.items.EtheriumAxe;
import com.integral.enigmaticlegacy.items.EtheriumIngot;
import com.integral.enigmaticlegacy.items.EtheriumOre;
import com.integral.enigmaticlegacy.items.EtheriumPickaxe;
import com.integral.enigmaticlegacy.items.EtheriumScythe;
import com.integral.enigmaticlegacy.items.EtheriumShovel;
import com.integral.enigmaticlegacy.items.EtheriumSword;
import com.integral.enigmaticlegacy.items.ExtradimensionalEye;
import com.integral.enigmaticlegacy.items.EyeOfNebula;
import com.integral.enigmaticlegacy.items.FabulousScroll;
import com.integral.enigmaticlegacy.items.ForbiddenAxe;
import com.integral.enigmaticlegacy.items.GemOfBinding;
import com.integral.enigmaticlegacy.items.GolemHeart;
import com.integral.enigmaticlegacy.items.HastePotion;
import com.integral.enigmaticlegacy.items.HeavenScroll;
import com.integral.enigmaticlegacy.items.IronRing;
import com.integral.enigmaticlegacy.items.LootGenerator;
import com.integral.enigmaticlegacy.items.LoreFragment;
import com.integral.enigmaticlegacy.items.LoreInscriber;
import com.integral.enigmaticlegacy.items.MagmaHeart;
import com.integral.enigmaticlegacy.items.MagnetRing;
import com.integral.enigmaticlegacy.items.Megasponge;
import com.integral.enigmaticlegacy.items.MendingMixture;
import com.integral.enigmaticlegacy.items.MiningCharm;
import com.integral.enigmaticlegacy.items.MonsterCharm;
import com.integral.enigmaticlegacy.items.OblivionStone;
import com.integral.enigmaticlegacy.items.OceanStone;
import com.integral.enigmaticlegacy.items.RecallPotion;
import com.integral.enigmaticlegacy.items.RelicOfTesting;
import com.integral.enigmaticlegacy.items.SuperMagnetRing;
import com.integral.enigmaticlegacy.items.ThiccScroll;
import com.integral.enigmaticlegacy.items.UltimatePotionBase;
import com.integral.enigmaticlegacy.items.UltimatePotionLingering;
import com.integral.enigmaticlegacy.items.UltimatePotionSplash;
import com.integral.enigmaticlegacy.items.UnholyGrail;
import com.integral.enigmaticlegacy.items.VoidPearl;
import com.integral.enigmaticlegacy.items.WormholePotion;
import com.integral.enigmaticlegacy.items.XPScroll;
import com.integral.enigmaticlegacy.items.generic.GenericBlockItem;
import com.integral.enigmaticlegacy.objects.AdvancedPotion;
import com.integral.enigmaticlegacy.packets.clients.PacketFlameParticles;
import com.integral.enigmaticlegacy.packets.clients.PacketHandleItemPickup;
import com.integral.enigmaticlegacy.packets.clients.PacketPlayerMotion;
import com.integral.enigmaticlegacy.packets.clients.PacketPlayerRotations;
import com.integral.enigmaticlegacy.packets.clients.PacketPlayerSetlook;
import com.integral.enigmaticlegacy.packets.clients.PacketPortalParticles;
import com.integral.enigmaticlegacy.packets.clients.PacketRecallParticles;
import com.integral.enigmaticlegacy.packets.clients.PacketSlotUnlocked;
import com.integral.enigmaticlegacy.packets.clients.PacketUpdateNotification;
import com.integral.enigmaticlegacy.packets.clients.PacketWitherParticles;
import com.integral.enigmaticlegacy.packets.server.PacketAnvilField;
import com.integral.enigmaticlegacy.packets.server.PacketConfirmTeleportation;
import com.integral.enigmaticlegacy.packets.server.PacketEnderRingKey;
import com.integral.enigmaticlegacy.packets.server.PacketSpellstoneKey;
import com.integral.enigmaticlegacy.packets.server.PacketXPScrollKey;
import com.integral.enigmaticlegacy.proxy.ClientProxy;
import com.integral.enigmaticlegacy.proxy.CommonProxy;
import com.integral.enigmaticlegacy.triggers.BeheadingTrigger;
import com.integral.enigmaticlegacy.triggers.UseUnholyGrailTrigger;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import vazkii.patchouli.api.PatchouliAPI;

@Mod("enigmaticlegacy")
public class EnigmaticLegacy {

	public static EnigmaticLegacy enigmaticLegacy;
	public static final Logger enigmaticLogger = LogManager.getLogger("Enigmatic Legacy");
	public static SimpleChannel packetInstance;

	public static final String MODID = "enigmaticlegacy";
	public static final String VERSION = "1.8.1";
	public static final String RELEASE_TYPE = "Release";
	public static final String NAME = "Enigmatic Legacy";

	public static final int howCoolAmI = Integer.MAX_VALUE;

	public static EnigmaticEventHandler enigmaticHandler;
	public static EnigmaticKeybindHandler keybindHandler;
	public static final OneSpecialHandler butImAsGuiltyAsThe = new OneSpecialHandler();
	public static List<String> damageTypesFire = new ArrayList<String>();
	public static List<AdvancedPotion> ultimatePotionTypes = new ArrayList<AdvancedPotion>();
	public static List<AdvancedPotion> commonPotionTypes = new ArrayList<AdvancedPotion>();
	public static SoundEvent HHON;
	public static SoundEvent HHOFF;
	public static SoundEvent SHIELD_TRIGGER;
	
	public static BlockMassiveLamp massiveLamp;
	public static BlockBigLamp bigLamp;

	public static EnigmaticItem enigmaticItem;
	public static XPScroll xpScroll;
	public static EnigmaticAmulet enigmaticAmulet;
	public static MagnetRing magnetRing;
	public static ExtradimensionalEye extradimensionalEye;
	public static RelicOfTesting relicOfTesting;
	public static RecallPotion recallPotion;
	public static ForbiddenAxe forbiddenAxe;
	public static EscapeScroll escapeScroll;
	public static HeavenScroll heavenScroll;
	public static SuperMagnetRing superMagnetRing;
	public static GolemHeart golemHeart;
	public static Megasponge megaSponge;
	public static UnholyGrail unholyGrail;
	public static EyeOfNebula eyeOfNebula;
	public static MagmaHeart magmaHeart;
	public static VoidPearl voidPearl;
	public static OceanStone oceanStone;
	public static AngelBlessing angelBlessing;
	public static MonsterCharm monsterCharm;
	public static MiningCharm miningCharm;
	public static EnderRing enderRing;
	public static MendingMixture mendingMixture;
	public static LootGenerator lootGenerator;
	public static ThiccScroll thiccScroll;
	public static IronRing ironRing;
	public static HastePotion hastePotionDefault;
	public static HastePotion hastePotionExtended;
	public static HastePotion hastePotionEmpowered;
	public static HastePotion hastePotionExtendedEmpowered;
	public static EtheriumOre etheriumOre;
	public static EtheriumIngot etheriumIngot;
	public static UltimatePotionBase ultimatePotionBase;
	public static UltimatePotionSplash ultimatePotionSplash;
	public static UltimatePotionLingering ultimatePotionLingering;
	public static UltimatePotionBase commonPotionBase;
	public static UltimatePotionSplash commonPotionSplash;
	public static UltimatePotionLingering commonPotionLingering;

	public static EtheriumArmor etheriumHelmet;
	public static EtheriumArmor etheriumChestplate;
	public static EtheriumArmor etheriumLeggings;
	public static EtheriumArmor etheriumBoots;

	public static EtheriumPickaxe etheriumPickaxe;
	public static EtheriumAxe etheriumAxe;
	public static EtheriumShovel etheriumShovel;
	public static EtheriumSword etheriumSword;
	public static EtheriumScythe etheriumScythe;

	public static AstralDust astralDust;
	public static LoreInscriber loreInscriber;
	public static LoreFragment loreFragment;
	public static EnderRod enderRod;

	public static AstralBreaker astralBreaker;
	public static OblivionStone oblivionStone;
	public static EnchantmentTransposer enchantmentTransposer;

	public static GemOfBinding gemOfBinding;
	public static WormholePotion wormholePotion;
	public static FabulousScroll fabulousScroll;

	public static AdvancedPotion ULTIMATE_NIGHT_VISION;
	public static AdvancedPotion ULTIMATE_INVISIBILITY;
	public static AdvancedPotion ULTIMATE_LEAPING;
	public static AdvancedPotion ULTIMATE_FIRE_RESISTANCE;
	public static AdvancedPotion ULTIMATE_SWIFTNESS;
	public static AdvancedPotion ULTIMATE_SLOWNESS;
	public static AdvancedPotion ULTIMATE_TURTLE_MASTER;
	public static AdvancedPotion ULTIMATE_WATER_BREATHING;
	public static AdvancedPotion ULTIMATE_HEALING;
	public static AdvancedPotion ULTIMATE_HARMING;
	public static AdvancedPotion ULTIMATE_POISON;
	public static AdvancedPotion ULTIMATE_REGENERATION;
	public static AdvancedPotion ULTIMATE_STRENGTH;
	public static AdvancedPotion ULTIMATE_WEAKNESS;
	public static AdvancedPotion ULTIMATE_SLOW_FALLING;

	public static AdvancedPotion HASTE;
	public static AdvancedPotion LONG_HASTE;
	public static AdvancedPotion STRONG_HASTE;
	public static AdvancedPotion ULTIMATE_HASTE;

	public static AdvancedPotion EMPTY;

	public static AdvancedPotion testingPotion;

	public static ItemStack universalClock;
	public static UUID soulOfTheArchitect;

	private static final String PTC_VERSION = "1";

	public static final CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

	@SuppressWarnings("deprecation")
	public EnigmaticLegacy() {
		EnigmaticLegacy.enigmaticLogger.info("Constructing mod instance...");

		EnigmaticLegacy.enigmaticLegacy = this;

		EnigmaticLegacy.enigmaticHandler = new EnigmaticEventHandler();
		EnigmaticLegacy.keybindHandler = new EnigmaticKeybindHandler();

		massiveLamp = new BlockMassiveLamp(Block.Properties.from(Blocks.BEACON));
		bigLamp = new BlockBigLamp(Block.Properties.from(Blocks.BEACON));
		
		EnigmaticLegacy.enigmaticItem = new EnigmaticItem();
		EnigmaticLegacy.xpScroll = new XPScroll();
		EnigmaticLegacy.enigmaticAmulet = new EnigmaticAmulet();
		EnigmaticLegacy.magnetRing = new MagnetRing();
		EnigmaticLegacy.extradimensionalEye = new ExtradimensionalEye();
		EnigmaticLegacy.relicOfTesting = new RelicOfTesting();
		EnigmaticLegacy.recallPotion = new RecallPotion();
		EnigmaticLegacy.forbiddenAxe = new ForbiddenAxe();
		EnigmaticLegacy.escapeScroll = new EscapeScroll();
		EnigmaticLegacy.heavenScroll = new HeavenScroll();
		EnigmaticLegacy.superMagnetRing = new SuperMagnetRing();
		EnigmaticLegacy.golemHeart = new GolemHeart();
		EnigmaticLegacy.megaSponge = new Megasponge();
		EnigmaticLegacy.unholyGrail = new UnholyGrail();
		EnigmaticLegacy.eyeOfNebula = new EyeOfNebula();
		EnigmaticLegacy.magmaHeart = new MagmaHeart();
		EnigmaticLegacy.voidPearl = new VoidPearl();
		EnigmaticLegacy.oceanStone = new OceanStone();
		EnigmaticLegacy.angelBlessing = new AngelBlessing();
		EnigmaticLegacy.monsterCharm = new MonsterCharm();
		EnigmaticLegacy.miningCharm = new MiningCharm();
		EnigmaticLegacy.enderRing = new EnderRing();
		EnigmaticLegacy.mendingMixture = new MendingMixture();
		EnigmaticLegacy.lootGenerator = new LootGenerator();
		EnigmaticLegacy.thiccScroll = new ThiccScroll();
		EnigmaticLegacy.ironRing = new IronRing();
		EnigmaticLegacy.etheriumOre = new EtheriumOre();
		EnigmaticLegacy.etheriumIngot = new EtheriumIngot();

		EnigmaticLegacy.hastePotionDefault = (HastePotion) new HastePotion(Rarity.COMMON, 3600, 0).setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "haste_potion_default"));
		EnigmaticLegacy.hastePotionExtended = (HastePotion) new HastePotion(Rarity.COMMON, 9600, 0).setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "haste_potion_extended"));
		EnigmaticLegacy.hastePotionEmpowered = (HastePotion) new HastePotion(Rarity.COMMON, 1800, 1).setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "haste_potion_empowered"));
		EnigmaticLegacy.hastePotionExtendedEmpowered = (HastePotion) new HastePotion(Rarity.RARE, 4800, 1).setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "haste_potion_extended_empowered"));

		EnigmaticLegacy.commonPotionBase = (UltimatePotionBase) new UltimatePotionBase(Rarity.COMMON, PotionType.COMMON).setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "common_potion"));
		EnigmaticLegacy.commonPotionSplash = (UltimatePotionSplash) new UltimatePotionSplash(Rarity.COMMON, PotionType.COMMON).setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "common_potion_splash"));
		EnigmaticLegacy.commonPotionLingering = (UltimatePotionLingering) new UltimatePotionLingering(Rarity.COMMON, PotionType.COMMON).setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "common_potion_lingering"));

		EnigmaticLegacy.ultimatePotionBase = (UltimatePotionBase) new UltimatePotionBase(Rarity.RARE, PotionType.ULTIMATE).setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "ultimate_potion"));
		EnigmaticLegacy.ultimatePotionSplash = (UltimatePotionSplash) new UltimatePotionSplash(Rarity.RARE, PotionType.ULTIMATE).setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "ultimate_potion_splash"));
		EnigmaticLegacy.ultimatePotionLingering = (UltimatePotionLingering) new UltimatePotionLingering(Rarity.RARE, PotionType.ULTIMATE).setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "ultimate_potion_lingering"));

		EnigmaticLegacy.etheriumHelmet = (EtheriumArmor) new EtheriumArmor(EnigmaticArmorMaterials.ETHERIUM, EquipmentSlotType.HEAD).setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "etherium_helmet"));
		EnigmaticLegacy.etheriumChestplate = (EtheriumArmor) new EtheriumArmor(EnigmaticArmorMaterials.ETHERIUM, EquipmentSlotType.CHEST).setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "etherium_chestplate"));
		EnigmaticLegacy.etheriumLeggings = (EtheriumArmor) new EtheriumArmor(EnigmaticArmorMaterials.ETHERIUM, EquipmentSlotType.LEGS).setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "etherium_leggings"));
		EnigmaticLegacy.etheriumBoots = (EtheriumArmor) new EtheriumArmor(EnigmaticArmorMaterials.ETHERIUM, EquipmentSlotType.FEET).setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "etherium_boots"));

		EnigmaticLegacy.etheriumPickaxe = new EtheriumPickaxe();
		EnigmaticLegacy.etheriumAxe = new EtheriumAxe();
		EnigmaticLegacy.etheriumShovel = new EtheriumShovel();
		EnigmaticLegacy.etheriumSword = new EtheriumSword();
		EnigmaticLegacy.etheriumScythe = new EtheriumScythe();

		EnigmaticLegacy.astralDust = new AstralDust();
		EnigmaticLegacy.loreInscriber = new LoreInscriber();
		EnigmaticLegacy.loreFragment = new LoreFragment();
		EnigmaticLegacy.enderRod = new EnderRod();

		EnigmaticLegacy.astralBreaker = new AstralBreaker();
		EnigmaticLegacy.oblivionStone = new OblivionStone();
		EnigmaticLegacy.enchantmentTransposer = new EnchantmentTransposer();

		EnigmaticLegacy.gemOfBinding = new GemOfBinding();
		EnigmaticLegacy.wormholePotion = new WormholePotion();
		EnigmaticLegacy.fabulousScroll = new FabulousScroll();

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::intermodStuff);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onLoadComplete);
		FMLJavaModLoadingContext.get().getModEventBus().register(new EnigmaticRecipeSerializers());

		FMLJavaModLoadingContext.get().getModEventBus().register(this);

		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(EnigmaticLegacy.enigmaticHandler);
		MinecraftForge.EVENT_BUS.register(EnigmaticLegacy.keybindHandler);
		MinecraftForge.EVENT_BUS.register(new EnigmaticUpdateHandler());

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON, "enigmatic-legacy-common.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHandler.CLIENT, "enigmatic-legacy-client.toml");

		EnigmaticLegacy.enigmaticLogger.info("Mod instance constructed successfully.");
	}
	
	private void onLoadComplete(final FMLLoadCompleteEvent event) {

		EnigmaticLegacy.enigmaticLogger.info("Initializing load completion phase...");

		EnigmaticLegacy.enigmaticLogger.info("Initializing config values...");

		if (!ConfigHandler.CONFIG_VERSION.get().equals(ConfigHandler.CURRENT_VERSION)) {
			ConfigHandler.resetConfig();

			ConfigHandler.CONFIG_VERSION.set(ConfigHandler.CURRENT_VERSION);
			ConfigHandler.CONFIG_VERSION.save();
		}

		EnigmaticLegacy.golemHeart.initAttributes();

		EnigmaticLegacy.enigmaticLogger.info("Registering brewing recipes...");

		if (ConfigHandler.RECALL_POTION_ENABLED.getValue())
			BrewingRecipeRegistry.addRecipe(new SpecialBrewingRecipe(Ingredient.fromStacks(PotionHelper.createVanillaPotion(Items.POTION, Potions.AWKWARD)), Ingredient.fromItems(Items.ENDER_EYE), new ItemStack(EnigmaticLegacy.recallPotion)));

		if (ConfigHandler.COMMON_POTIONS_ENABLED.getValue())
			PotionHelper.registerCommonPotions();

		if (ConfigHandler.ULTIMATE_POTIONS_ENABLED.getValue()) {

			PotionHelper.registerBasicUltimatePotions();
			PotionHelper.registerSplashUltimatePotions();
			PotionHelper.registerLingeringUltimatePotions();

		}

		BrewingRecipeRegistry.addRecipe(new ValidationBrewingRecipe(Ingredient.fromItems(EnigmaticLegacy.hastePotionExtendedEmpowered, EnigmaticLegacy.recallPotion, EnigmaticLegacy.ultimatePotionLingering, EnigmaticLegacy.commonPotionLingering), null));

		EnigmaticUpdateHandler.init();

		EnigmaticLegacy.proxy.loadComplete(event);

		EnigmaticLegacy.universalClock = new ItemStack(Items.CLOCK);
		EnigmaticLegacy.soulOfTheArchitect = UUID.fromString("3efc546d-30bb-4c29-bb61-b3081a118408");

		EnigmaticLegacy.enigmaticLogger.info("Load completion phase finished successfully");
	}

	private void setup(final FMLCommonSetupEvent event) {

		EnigmaticLegacy.enigmaticLogger.info("Initializing common setup phase...");

		EnigmaticLegacy.damageTypesFire.add(DamageSource.LAVA.damageType);
		EnigmaticLegacy.damageTypesFire.add(DamageSource.IN_FIRE.damageType);
		EnigmaticLegacy.damageTypesFire.add(DamageSource.ON_FIRE.damageType);

		EnigmaticLegacy.enigmaticLogger.info("Registering packets...");
		EnigmaticLegacy.packetInstance = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(EnigmaticLegacy.MODID, "main")).networkProtocolVersion(() -> EnigmaticLegacy.PTC_VERSION).clientAcceptedVersions(EnigmaticLegacy.PTC_VERSION::equals).serverAcceptedVersions(EnigmaticLegacy.PTC_VERSION::equals).simpleChannel();

		EnigmaticLegacy.packetInstance.registerMessage(0, PacketRecallParticles.class, PacketRecallParticles::encode, PacketRecallParticles::decode, PacketRecallParticles::handle);
		EnigmaticLegacy.packetInstance.registerMessage(1, PacketEnderRingKey.class, PacketEnderRingKey::encode, PacketEnderRingKey::decode, PacketEnderRingKey::handle);
		EnigmaticLegacy.packetInstance.registerMessage(2, PacketSpellstoneKey.class, PacketSpellstoneKey::encode, PacketSpellstoneKey::decode, PacketSpellstoneKey::handle);
		EnigmaticLegacy.packetInstance.registerMessage(3, PacketPlayerMotion.class, PacketPlayerMotion::encode, PacketPlayerMotion::decode, PacketPlayerMotion::handle);
		EnigmaticLegacy.packetInstance.registerMessage(4, PacketPlayerRotations.class, PacketPlayerRotations::encode, PacketPlayerRotations::decode, PacketPlayerRotations::handle);
		EnigmaticLegacy.packetInstance.registerMessage(5, PacketPlayerSetlook.class, PacketPlayerSetlook::encode, PacketPlayerSetlook::decode, PacketPlayerSetlook::handle);

		EnigmaticLegacy.packetInstance.registerMessage(7, PacketConfirmTeleportation.class, PacketConfirmTeleportation::encode, PacketConfirmTeleportation::decode, PacketConfirmTeleportation::handle);
		EnigmaticLegacy.packetInstance.registerMessage(8, PacketPortalParticles.class, PacketPortalParticles::encode, PacketPortalParticles::decode, PacketPortalParticles::handle);
		EnigmaticLegacy.packetInstance.registerMessage(9, PacketXPScrollKey.class, PacketXPScrollKey::encode, PacketXPScrollKey::decode, PacketXPScrollKey::handle);
		EnigmaticLegacy.packetInstance.registerMessage(10, PacketSlotUnlocked.class, PacketSlotUnlocked::encode, PacketSlotUnlocked::decode, PacketSlotUnlocked::handle);
		EnigmaticLegacy.packetInstance.registerMessage(11, PacketHandleItemPickup.class, PacketHandleItemPickup::encode, PacketHandleItemPickup::decode, PacketHandleItemPickup::handle);
		EnigmaticLegacy.packetInstance.registerMessage(12, PacketUpdateNotification.class, PacketUpdateNotification::encode, PacketUpdateNotification::decode, PacketUpdateNotification::handle);
		EnigmaticLegacy.packetInstance.registerMessage(13, PacketAnvilField.class, PacketAnvilField::encode, PacketAnvilField::decode, PacketAnvilField::handle);
		EnigmaticLegacy.packetInstance.registerMessage(14, PacketWitherParticles.class, PacketWitherParticles::encode, PacketWitherParticles::decode, PacketWitherParticles::handle);
		EnigmaticLegacy.packetInstance.registerMessage(15, PacketFlameParticles.class, PacketFlameParticles::encode, PacketFlameParticles::decode, PacketFlameParticles::handle);

		EnigmaticLegacy.enigmaticLogger.info("Registering triggers...");
		CriteriaTriggers.register(UseUnholyGrailTrigger.INSTANCE);
		CriteriaTriggers.register(BeheadingTrigger.INSTANCE);

		ObfuscatedFields.extractCommonFields();

		EnigmaticLegacy.enigmaticLogger.info("Common setup phase finished successfully.");
	}

	private void clientRegistries(final FMLClientSetupEvent event) {
		EnigmaticLegacy.enigmaticLogger.info("Initializing client setup phase...");
		EnigmaticLegacy.keybindHandler.registerKeybinds();

		ObfuscatedFields.extractClientFields();
		RenderTypeLookup.setRenderLayer(EnigmaticLegacy.massiveLamp, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnigmaticLegacy.bigLamp, RenderType.getCutout());

		EnigmaticLegacy.enigmaticLogger.info("Client setup phase finished successfully.");
	}

	private void intermodStuff(final InterModEnqueueEvent event) {
		EnigmaticLegacy.enigmaticLogger.info("Sending messages to Curios API...");
		SuperpositionHandler.registerCurioType("charm", 1, true, false, null);
		SuperpositionHandler.registerCurioType("ring", 2, false, false, null);
		SuperpositionHandler.registerCurioType("spellstone", 1, false, false, new ResourceLocation(EnigmaticLegacy.MODID, "slots/empty_spellstone_slot"));
		SuperpositionHandler.registerCurioType("scroll", 1, false, false, new ResourceLocation(EnigmaticLegacy.MODID, "slots/empty_scroll_slot"));

	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = EnigmaticLegacy.MODID)
	public static class RegistryEvents {

		@OnlyIn(Dist.CLIENT)
		@SubscribeEvent
		public static void stitchTextures(TextureStitchEvent.Pre evt) {

			if (evt.getMap().getTextureLocation() == PlayerContainer.LOCATION_BLOCKS_TEXTURE) {

				evt.addSprite(new ResourceLocation(EnigmaticLegacy.MODID, "slots/empty_spellstone_slot"));
				evt.addSprite(new ResourceLocation(EnigmaticLegacy.MODID, "slots/empty_scroll_slot"));

			}
		}
		
		@SubscribeEvent
		public static void registerBlocks(final RegistryEvent.Register<Block> event) {
			EnigmaticLegacy.enigmaticLogger.info("Initializing blocks registration...");
			
			event.getRegistry().registerAll(massiveLamp, bigLamp);
			
			EnigmaticLegacy.enigmaticLogger.info("Blocks registered successfully.");
		}

		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event) {

			EnigmaticLegacy.enigmaticLogger.info("Initializing items registration...");

			event.getRegistry().registerAll(
					EnigmaticLegacy.enigmaticItem,
					EnigmaticLegacy.golemHeart,
					EnigmaticLegacy.angelBlessing,
					EnigmaticLegacy.oceanStone,
					EnigmaticLegacy.magmaHeart,
					EnigmaticLegacy.eyeOfNebula,
					EnigmaticLegacy.voidPearl,
					EnigmaticLegacy.ironRing,
					EnigmaticLegacy.enigmaticAmulet,
					EnigmaticLegacy.thiccScroll,
					EnigmaticLegacy.xpScroll,
					EnigmaticLegacy.escapeScroll,
					EnigmaticLegacy.heavenScroll,
					EnigmaticLegacy.magnetRing,
					EnigmaticLegacy.superMagnetRing,
					EnigmaticLegacy.enderRing,
					EnigmaticLegacy.monsterCharm,
					EnigmaticLegacy.miningCharm,
					EnigmaticLegacy.megaSponge,
					EnigmaticLegacy.extradimensionalEye,
					EnigmaticLegacy.forbiddenAxe,
					EnigmaticLegacy.unholyGrail,
					EnigmaticLegacy.recallPotion,
					EnigmaticLegacy.mendingMixture,
					EnigmaticLegacy.lootGenerator,
					EnigmaticLegacy.hastePotionDefault,
					EnigmaticLegacy.hastePotionExtended,
					EnigmaticLegacy.hastePotionEmpowered,
					EnigmaticLegacy.hastePotionExtendedEmpowered,
					EnigmaticLegacy.relicOfTesting,
					EnigmaticLegacy.etheriumOre,
					EnigmaticLegacy.etheriumIngot,
					EnigmaticLegacy.commonPotionBase,
					EnigmaticLegacy.commonPotionSplash,
					EnigmaticLegacy.commonPotionLingering,
					EnigmaticLegacy.ultimatePotionBase,
					EnigmaticLegacy.ultimatePotionSplash,
					EnigmaticLegacy.ultimatePotionLingering,
					EnigmaticLegacy.etheriumHelmet,
					EnigmaticLegacy.etheriumChestplate,
					EnigmaticLegacy.etheriumLeggings,
					EnigmaticLegacy.etheriumBoots,
					EnigmaticLegacy.etheriumPickaxe,
					EnigmaticLegacy.etheriumAxe,
					EnigmaticLegacy.etheriumShovel,
					EnigmaticLegacy.etheriumSword,
					EnigmaticLegacy.etheriumScythe,
					EnigmaticLegacy.astralBreaker,
					EnigmaticLegacy.astralDust,
					EnigmaticLegacy.enderRod,
					EnigmaticLegacy.loreInscriber,
					EnigmaticLegacy.loreFragment,
					EnigmaticLegacy.oblivionStone,
					EnigmaticLegacy.enchantmentTransposer,
					EnigmaticLegacy.fabulousScroll,
					new GenericBlockItem(massiveLamp),
					new GenericBlockItem(bigLamp)
					/*,gemOfBinding,wormholePotion*/
			);

			EnigmaticLegacy.enigmaticLogger.info("Items registered successfully.");
		}

		@SubscribeEvent
		public static void registerSounds(final RegistryEvent.Register<SoundEvent> event) {
			EnigmaticLegacy.enigmaticLogger.info("Initializing sounds registration...");

			EnigmaticLegacy.HHON = SuperpositionHandler.registerSound("misc.hhon");
			EnigmaticLegacy.HHOFF = SuperpositionHandler.registerSound("misc.hhoff");
			EnigmaticLegacy.SHIELD_TRIGGER = SuperpositionHandler.registerSound("misc.shield_trigger");

			EnigmaticLegacy.enigmaticLogger.info("Sounds registered successfully.");
		}

		@SubscribeEvent
		public static void onRecipeRegister(final RegistryEvent.Register<IRecipeSerializer<?>> e) {

		}

		@SubscribeEvent
		public static void registerBrewing(RegistryEvent.Register<Potion> event) {

			EnigmaticLegacy.enigmaticLogger.info("Initializing advanced potion system...");

			EnigmaticLegacy.ULTIMATE_NIGHT_VISION = new AdvancedPotion("ultimate_night_vision", new EffectInstance(Effects.NIGHT_VISION, 19200));
			EnigmaticLegacy.ULTIMATE_INVISIBILITY = new AdvancedPotion("ultimate_invisibility", new EffectInstance(Effects.INVISIBILITY, 19200));
			EnigmaticLegacy.ULTIMATE_LEAPING = new AdvancedPotion("ultimate_leaping", new EffectInstance(Effects.JUMP_BOOST, 9600, 1));
			EnigmaticLegacy.ULTIMATE_FIRE_RESISTANCE = new AdvancedPotion("ultimate_fire_resistance", new EffectInstance(Effects.FIRE_RESISTANCE, 19200));
			EnigmaticLegacy.ULTIMATE_SWIFTNESS = new AdvancedPotion("ultimate_swiftness", new EffectInstance(Effects.SPEED, 9600, 1));
			EnigmaticLegacy.ULTIMATE_SLOWNESS = new AdvancedPotion("ultimate_slowness", new EffectInstance(Effects.SLOWNESS, 1200, 3));
			EnigmaticLegacy.ULTIMATE_TURTLE_MASTER = new AdvancedPotion("ultimate_turtle_master", new EffectInstance(Effects.SLOWNESS, 800, 5), new EffectInstance(Effects.RESISTANCE, 800, 3));
			EnigmaticLegacy.ULTIMATE_WATER_BREATHING = new AdvancedPotion("ultimate_water_breathing", new EffectInstance(Effects.WATER_BREATHING, 19200));
			EnigmaticLegacy.ULTIMATE_HEALING = new AdvancedPotion("ultimate_healing", new EffectInstance(Effects.INSTANT_HEALTH, 1, 2));
			EnigmaticLegacy.ULTIMATE_HARMING = new AdvancedPotion("ultimate_harming", new EffectInstance(Effects.INSTANT_DAMAGE, 1, 2));
			EnigmaticLegacy.ULTIMATE_POISON = new AdvancedPotion("ultimate_poison", new EffectInstance(Effects.POISON, 1800, 1));
			EnigmaticLegacy.ULTIMATE_REGENERATION = new AdvancedPotion("ultimate_regeneration", new EffectInstance(Effects.REGENERATION, 1800, 1));
			EnigmaticLegacy.ULTIMATE_STRENGTH = new AdvancedPotion("ultimate_strength", new EffectInstance(Effects.STRENGTH, 9600, 1));
			EnigmaticLegacy.ULTIMATE_WEAKNESS = new AdvancedPotion("ultimate_weakness", new EffectInstance(Effects.WEAKNESS, 9600));
			EnigmaticLegacy.ULTIMATE_SLOW_FALLING = new AdvancedPotion("ultimate_slow_falling", new EffectInstance(Effects.SLOW_FALLING, 9600));

			EnigmaticLegacy.HASTE = new AdvancedPotion("haste", new EffectInstance(Effects.HASTE, 3600));
			EnigmaticLegacy.LONG_HASTE = new AdvancedPotion("long_haste", new EffectInstance(Effects.HASTE, 9600));
			EnigmaticLegacy.STRONG_HASTE = new AdvancedPotion("strong_haste", new EffectInstance(Effects.HASTE, 1800, 1));
			EnigmaticLegacy.ULTIMATE_HASTE = new AdvancedPotion("ultimate_haste", new EffectInstance(Effects.HASTE, 9600, 1));

			EnigmaticLegacy.EMPTY = new AdvancedPotion("empty");

			EnigmaticLegacy.ultimatePotionTypes.add(EnigmaticLegacy.ULTIMATE_NIGHT_VISION);
			EnigmaticLegacy.ultimatePotionTypes.add(EnigmaticLegacy.ULTIMATE_INVISIBILITY);
			EnigmaticLegacy.ultimatePotionTypes.add(EnigmaticLegacy.ULTIMATE_LEAPING);
			EnigmaticLegacy.ultimatePotionTypes.add(EnigmaticLegacy.ULTIMATE_FIRE_RESISTANCE);
			EnigmaticLegacy.ultimatePotionTypes.add(EnigmaticLegacy.ULTIMATE_SWIFTNESS);
			EnigmaticLegacy.ultimatePotionTypes.add(EnigmaticLegacy.ULTIMATE_SLOWNESS);
			EnigmaticLegacy.ultimatePotionTypes.add(EnigmaticLegacy.ULTIMATE_TURTLE_MASTER);
			EnigmaticLegacy.ultimatePotionTypes.add(EnigmaticLegacy.ULTIMATE_WATER_BREATHING);
			EnigmaticLegacy.ultimatePotionTypes.add(EnigmaticLegacy.ULTIMATE_HEALING);
			EnigmaticLegacy.ultimatePotionTypes.add(EnigmaticLegacy.ULTIMATE_HARMING);
			EnigmaticLegacy.ultimatePotionTypes.add(EnigmaticLegacy.ULTIMATE_POISON);
			EnigmaticLegacy.ultimatePotionTypes.add(EnigmaticLegacy.ULTIMATE_REGENERATION);
			EnigmaticLegacy.ultimatePotionTypes.add(EnigmaticLegacy.ULTIMATE_STRENGTH);
			EnigmaticLegacy.ultimatePotionTypes.add(EnigmaticLegacy.ULTIMATE_WEAKNESS);
			EnigmaticLegacy.ultimatePotionTypes.add(EnigmaticLegacy.ULTIMATE_SLOW_FALLING);

			EnigmaticLegacy.commonPotionTypes.add(EnigmaticLegacy.HASTE);
			EnigmaticLegacy.commonPotionTypes.add(EnigmaticLegacy.LONG_HASTE);
			EnigmaticLegacy.commonPotionTypes.add(EnigmaticLegacy.STRONG_HASTE);
			EnigmaticLegacy.ultimatePotionTypes.add(EnigmaticLegacy.ULTIMATE_HASTE);

			EnigmaticLegacy.enigmaticLogger.info("Advanced potion system initialized successfully.");
		}

		@SubscribeEvent
		public static void onEntitiesRegistry(final RegistryEvent.Register<EntityType<?>> event) {
			EnigmaticLegacy.enigmaticLogger.info("Initializing entities registration...");

			event.getRegistry().register(EntityType.Builder.<PermanentItemEntity>create(PermanentItemEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).setTrackingRange(64).setCustomClientFactory((spawnEntity, world) -> new PermanentItemEntity(PermanentItemEntity.TYPE, world)).setUpdateInterval(2).setShouldReceiveVelocityUpdates(true).build("").setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "permanent_item_entity")));

			event.getRegistry().register(EntityType.Builder.<EnigmaticPotionEntity>create(EnigmaticPotionEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).setTrackingRange(64).setCustomClientFactory((spawnEntity, world) -> new EnigmaticPotionEntity(EnigmaticPotionEntity.TYPE, world)).setUpdateInterval(10).setShouldReceiveVelocityUpdates(true).build("").setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "enigmatic_potion_entity")));

			event.getRegistry().register(EntityType.Builder.<UltimateWitherSkullEntity>create(UltimateWitherSkullEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).setTrackingRange(64).setCustomClientFactory((spawnEntity, world) -> new UltimateWitherSkullEntity(UltimateWitherSkullEntity.TYPE, world))
					//.setUpdateInterval(1)
					//.setShouldReceiveVelocityUpdates(true)
					.build("").setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "ultimate_wither_skull_entity")));

			EnigmaticLegacy.enigmaticLogger.info("Entities registered successfully.");
		}
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onColorInit(net.minecraftforge.client.event.ColorHandlerEvent.Item event) {
		EnigmaticLegacy.enigmaticLogger.info("Initializing colors registration...");

		event.getItemColors().register((stack, color) -> {
			if (PotionHelper.isAdvancedPotion(stack))
				return color > 0 ? -1 : PotionHelper.getColor(stack);

			return color > 0 ? -1 : PotionUtils.getColor(stack);
		}, EnigmaticLegacy.ultimatePotionBase, EnigmaticLegacy.ultimatePotionSplash, EnigmaticLegacy.ultimatePotionLingering, EnigmaticLegacy.commonPotionBase, EnigmaticLegacy.commonPotionSplash, EnigmaticLegacy.commonPotionLingering);

		EnigmaticLegacy.enigmaticLogger.info("Colors registered successfully.");
	}

	public static final ItemGroup enigmaticTab = new ItemGroup("enigmaticCreativeTab") {
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(EnigmaticLegacy.enigmaticItem);
		}
	};

	public static final ItemGroup enigmaticPotionTab = new ItemGroup("enigmaticPotionCreativeTab") {
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(EnigmaticLegacy.recallPotion);
		}
	};

}
