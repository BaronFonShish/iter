package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.common.item.*;
import com.thirdlife.itermod.common.item.armor.ApprenticeArmor;
import com.thirdlife.itermod.common.item.armor.AzureArmor;
import com.thirdlife.itermod.common.item.curio.*;
import com.thirdlife.itermod.common.item.magic.foci.*;
import com.thirdlife.itermod.common.item.magic.defaults.SpellBook;
import com.thirdlife.itermod.common.item.magic.spells.*;
import com.thirdlife.itermod.common.misc.GobsteelTier;
import com.thirdlife.itermod.iterMod;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, iterMod.MOD_ID);

    public static final RegistryObject<Item> NOSTELON = ITEMS.register("nostelon", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ROUGH_NOSTELON = ITEMS.register("rough_nostelon", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NOSTELON_NUGGET = ITEMS.register("nostelon_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOBSTEEL_SCRAP = ITEMS.register("gobsteel_scrap", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPIDER_SILK = ITEMS.register("spider_silk", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BOWSTRING = ITEMS.register("bowstring", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DWARVEN_COMPONENTS = ITEMS.register("dwarven_components", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ABYSSQUARTZ_SHARD = ITEMS.register("abyssquartz_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MAGMANUM = ITEMS.register("magmanum", () -> new MagmanumItem(new Item.Properties()));
    public static final RegistryObject<Item> ETHERDUST = ITEMS.register("etherdust", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GIST = ITEMS.register("gist", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> INK_BOTTLE = ITEMS.register("ink_bottle", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> POTSHERD = ITEMS.register("potsherd", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_COIN = ITEMS.register("gold_coin", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> COPPER_COIN = ITEMS.register("copper_coin", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_COIN = ITEMS.register("diamond_coin", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> TANKARD = ITEMS.register("tankard", () -> new TankardItem(new Item.Properties()));
    public static final RegistryObject<Item> SPAWNER_FRAGMENT = ITEMS.register("spawner_fragment", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ROTROOT_SEEDS = ITEMS.register("rotroot_seeds", () -> new ItemNameBlockItem(ModBlocks.ROTROOT.get(), new Item.Properties()));
    public static final RegistryObject<Item> ETHERBLOOM_SEEDS = ITEMS.register("etherbloom_seeds", () -> new ItemNameBlockItem(ModBlocks.ETHERBLOOM_PLANT.get(), new Item.Properties()));
    public static final RegistryObject<Item> ROTROOT = ITEMS.register("rotroot",() -> new RotrootItem(new Item.Properties()));
    public static final RegistryObject<Item> ABSTRUSE_CLOTH = ITEMS.register("abstruse_cloth", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SANGUARNET = ITEMS.register("sanguarnet",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RECURVE_BOW = ITEMS.register("recurve_bow", RecurveBowItem::new);
    public static final RegistryObject<Item> STINGER = ITEMS.register("stinger", StingerItem::new);
    public static final RegistryObject<Item> MAGMANUM_SWORD = ITEMS.register("magmanum_sword", MagmanumSword::new);
    public static final RegistryObject<Item> MAGMANUM_PICKAXE = ITEMS.register("magmanum_pickaxe", MagmanumPickaxe::new);
    public static final RegistryObject<Item> HELLBLAZE_ARROW = ITEMS.register("hellblaze_arrow", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SPIDERLING_SPAWN_EGG =
            ITEMS.register("spiderling_spawn_egg", () ->new ForgeSpawnEggItem
                    (ModEntities.SPIDERLING, -2372425, -13029294, new Item.Properties()));
    public static final RegistryObject<Item> GIANT_SPIDER_SPAWN_EGG =
            ITEMS.register("giant_spider_spawn_egg", () ->new ForgeSpawnEggItem
                    (ModEntities.GIANT_SPIDER, 0x526133, 0x4B3A43, new Item.Properties()));
    public static final RegistryObject<Item> GOBLIN_SPAWN_EGG =
            ITEMS.register("goblin_spawn_egg", () ->new ForgeSpawnEggItem
                    (ModEntities.GOBLIN, 0x526133, 0x634F52, new Item.Properties()));
    public static final RegistryObject<Item> GOBLIN_WARRIOR_SPAWN_EGG =
            ITEMS.register("goblin_warrior_spawn_egg", () ->new ForgeSpawnEggItem
                    (ModEntities.GOBLIN_WARRIOR, 0x526133, 0x4B3A43, new Item.Properties()));


    public static final RegistryObject<Item> WOODEN_DAGGER = ITEMS.register("wooden_dagger",
            () -> new DaggerItem(Tiers.WOOD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> STONE_DAGGER = ITEMS.register("stone_dagger",
            () -> new DaggerItem(Tiers.STONE, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> IRON_DAGGER = ITEMS.register("iron_dagger",
            () -> new DaggerItem(Tiers.IRON, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GOLDEN_DAGGER = ITEMS.register("golden_dagger",
            () -> new DaggerItem(Tiers.GOLD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DIAMOND_DAGGER = ITEMS.register("diamond_dagger",
            () -> new DaggerItem(Tiers.DIAMOND, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> NETHERITE_DAGGER = ITEMS.register("netherite_dagger",
            () -> new DaggerItem(Tiers.NETHERITE, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> WOODEN_FLAIL = ITEMS.register("wooden_flail",
            () -> new FlailItem(Tiers.WOOD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> STONE_FLAIL = ITEMS.register("stone_flail",
            () -> new FlailItem(Tiers.STONE, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> IRON_FLAIL = ITEMS.register("iron_flail",
            () -> new FlailItem(Tiers.IRON, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GOLDEN_FLAIL = ITEMS.register("golden_flail",
            () -> new FlailItem(Tiers.GOLD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DIAMOND_FLAIL = ITEMS.register("diamond_flail",
            () -> new FlailItem(Tiers.DIAMOND, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> NETHERITE_FLAIL = ITEMS.register("netherite_flail",
            () -> new FlailItem(Tiers.NETHERITE, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> WOODEN_SCYTHE = ITEMS.register("wooden_scythe",
            () -> new ScytheItem(Tiers.WOOD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> STONE_SCYTHE = ITEMS.register("stone_scythe",
            () -> new ScytheItem(Tiers.STONE, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> IRON_SCYTHE = ITEMS.register("iron_scythe",
            () -> new ScytheItem(Tiers.IRON, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GOLDEN_SCYTHE = ITEMS.register("golden_scythe",
            () -> new ScytheItem(Tiers.GOLD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DIAMOND_SCYTHE = ITEMS.register("diamond_scythe",
            () -> new ScytheItem(Tiers.DIAMOND, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> NETHERITE_SCYTHE = ITEMS.register("netherite_scythe",
            () -> new ScytheItem(Tiers.NETHERITE, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> WOODEN_SPEAR = ITEMS.register("wooden_spear",
            () -> new SpearItem(Tiers.WOOD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> STONE_SPEAR = ITEMS.register("stone_spear",
            () -> new SpearItem(Tiers.STONE, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> IRON_SPEAR = ITEMS.register("iron_spear",
            () -> new SpearItem(Tiers.IRON, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GOLDEN_SPEAR = ITEMS.register("golden_spear",
            () -> new SpearItem(Tiers.GOLD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DIAMOND_SPEAR = ITEMS.register("diamond_spear",
            () -> new SpearItem(Tiers.DIAMOND, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> NETHERITE_SPEAR = ITEMS.register("netherite_spear",
            () -> new SpearItem(Tiers.NETHERITE, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> GOBSTEEL_SWORD = ITEMS.register("gobsteel_sword",
            () -> new SwordItem(GobsteelTier.INSTANCE, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> GOBSTEEL_PICKAXE = ITEMS.register("gobsteel_pickaxe",
            () -> new PickaxeItem(GobsteelTier.INSTANCE, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> GOBSTEEL_AXE = ITEMS.register("gobsteel_axe",
            () -> new AxeItem(GobsteelTier.INSTANCE, 6.0F, -3.2F, new Item.Properties()));
    public static final RegistryObject<Item> GOBSTEEL_SHOVEL = ITEMS.register("gobsteel_shovel",
            () -> new ShovelItem(GobsteelTier.INSTANCE, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> GOBSTEEL_HOE = ITEMS.register("gobsteel_hoe",
            () -> new HoeItem(GobsteelTier.INSTANCE, -2, -1.0F, new Item.Properties()));

    public static final RegistryObject<Item> IRON_RING = ITEMS.register("iron_ring", IronRing::new);
    public static final RegistryObject<Item> GOLDEN_RING = ITEMS.register("golden_ring", GoldenRing::new);
    public static final RegistryObject<Item> NETHERITE_RING = ITEMS.register("netherite_ring", NetheriteRing::new);
    public static final RegistryObject<Item> IRON_RING_DIAMOND = ITEMS.register("iron_ring_diamond", IronRingDiamond::new);
    public static final RegistryObject<Item> GOLDEN_RING_DIAMOND = ITEMS.register("golden_ring_diamond", GoldenRingDiamond::new);
    public static final RegistryObject<Item> NETHERITE_RING_DIAMOND = ITEMS.register("netherite_ring_diamond", NetheriteRingDiamond::new);
    public static final RegistryObject<Item> IRON_RING_NOSTELON = ITEMS.register("iron_ring_nostelon", IronRingNostelon::new);
    public static final RegistryObject<Item> GOLDEN_RING_NOSTELON = ITEMS.register("golden_ring_nostelon", GoldenRingNostelon::new);
    public static final RegistryObject<Item> NETHERITE_RING_NOSTELON = ITEMS.register("netherite_ring_nostelon", NetheriteRingNostelon::new);
    public static final RegistryObject<Item> IRON_RING_ABYSSQUARTZ = ITEMS.register("iron_ring_abyssquartz", IronRingAbyssquartz::new);
    public static final RegistryObject<Item> GOLDEN_RING_ABYSSQUARTZ = ITEMS.register("golden_ring_abyssquartz", GoldenRingAbyssquartz::new);
    public static final RegistryObject<Item> NETHERITE_RING_ABYSSQUARTZ = ITEMS.register("netherite_ring_abyssquartz", NetheriteRingAbyssquartz::new);
    public static final RegistryObject<Item> IRON_RING_EMERALD = ITEMS.register("iron_ring_emerald", IronRingEmerald::new);
    public static final RegistryObject<Item> GOLDEN_RING_EMERALD = ITEMS.register("golden_ring_emerald", GoldenRingEmerald::new);
    public static final RegistryObject<Item> NETHERITE_RING_EMERALD = ITEMS.register("netherite_ring_emerald", NetheriteRingEmerald::new);
    public static final RegistryObject<Item> IRON_RING_AMETHYST = ITEMS.register("iron_ring_amethyst", IronRingAmethyst::new);
    public static final RegistryObject<Item> GOLDEN_RING_AMETHYST = ITEMS.register("golden_ring_amethyst", GoldenRingAmethyst::new);
    public static final RegistryObject<Item> NETHERITE_RING_AMETHYST = ITEMS.register("netherite_ring_amethyst", NetheriteRingAmethyst::new);
    public static final RegistryObject<Item> IRON_RING_SANGUARNET = ITEMS.register("iron_ring_sanguarnet", IronRingSanguarnet::new);
    public static final RegistryObject<Item> GOLDEN_RING_SANGUARNET = ITEMS.register("golden_ring_sanguarnet", GoldenRingSanguarnet::new);
    public static final RegistryObject<Item> NETHERITE_RING_SANGUARNET = ITEMS.register("netherite_ring_sanguarnet", NetheriteRingSanguarnet::new);
    public static final RegistryObject<Item> IRON_RING_MAGMANUM = ITEMS.register("iron_ring_magmanum", IronRingMagmanum::new);
    public static final RegistryObject<Item> GOLDEN_RING_MAGMANUM = ITEMS.register("golden_ring_magmanum", GoldenRingMagmanum::new);
    public static final RegistryObject<Item> NETHERITE_RING_MAGMANUM = ITEMS.register("netherite_ring_magmanum", NetheriteRingMagmanum::new);

    public static final RegistryObject<Item> APPRENTICE_HOOD = ITEMS.register("apprentice_hood", ApprenticeArmor.Helmet::new);
    public static final RegistryObject<Item> APPRENTICE_ROBES = ITEMS.register("apprentice_robes", ApprenticeArmor.Chestplate::new);
    public static final RegistryObject<Item> APPRENTICE_PANTS = ITEMS.register("apprentice_pants", ApprenticeArmor.Leggings::new);
    public static final RegistryObject<Item> APPRENTICE_BOOTS = ITEMS.register("apprentice_boots", ApprenticeArmor.Boots::new);
    public static final RegistryObject<Item> AZURE_HOOD = ITEMS.register("azure_hood", AzureArmor.Helmet::new);
    public static final RegistryObject<Item> AZURE_MANTLE = ITEMS.register("azure_mantle", AzureArmor.Chestplate::new);
    public static final RegistryObject<Item> AZURE_PANTS = ITEMS.register("azure_pants", AzureArmor.Leggings::new);
    public static final RegistryObject<Item> AZURE_BOOTS = ITEMS.register("azure_boots", AzureArmor.Boots::new);

    public static final RegistryObject<Item> SPELL_BOOK = ITEMS.register("spell_book", SpellBook::new);

    public static final RegistryObject<Item> BONE_STAFF = ITEMS.register("bone_staff", BoneStaff::new);
    public static final RegistryObject<Item> AMETHYST_WAND = ITEMS.register("amethyst_wand", AmethystWand::new);
    public static final RegistryObject<Item> ANCIENT_STAFF = ITEMS.register("ancient_staff", AncientStaff::new);
    public static final RegistryObject<Item> NOSTELON_STAFF = ITEMS.register("nostelon_staff", NostelonStaff::new);
    public static final RegistryObject<Item> DIAMOND_STAFF = ITEMS.register("diamond_staff", DiamondStaff::new);
    public static final RegistryObject<Item> MAGMANUM_STAFF = ITEMS.register("magmanum_staff", MagmanumStaff::new);


    public static final RegistryObject<Item> SPELL_ETHERBOLT = ITEMS.register("spell_etherbolt", SpellEtherbolt::new);
    public static final RegistryObject<Item> SPELL_LESSER_HEAL = ITEMS.register("spell_lesser_heal", SpellLesserHeal::new);
    public static final RegistryObject<Item> SPELL_MEND = ITEMS.register("spell_mend", SpellMend::new);
    public static final RegistryObject<Item> SPELL_RECALL = ITEMS.register("spell_recall", SpellRecall::new);
    public static final RegistryObject<Item> SPELL_LEAP = ITEMS.register("spell_leap", SpellLeap::new);

    public static final RegistryObject<Item> SPELL_IGNITE = ITEMS.register("spell_ignite", SpellIgnite::new);
    public static final RegistryObject<Item> SPELL_FROST_SPIKE = ITEMS.register("spell_frost_spike", SpellFrostSpike::new);
    public static final RegistryObject<Item> SPELL_SPLASHES = ITEMS.register("spell_splashes", SpellSplashes::new);
    public static final RegistryObject<Item> SPELL_BLAZE_BARRAGE = ITEMS.register("spell_blaze_barrage", SpellBlazeBarrage::new);
    public static final RegistryObject<Item> SPELL_LEGACY_FIREBALL = ITEMS.register("spell_legacy_fireball", SpellLegacyFireball::new);

    public static final RegistryObject<Item> SPELL_BEAM_TEST = ITEMS.register("spell_beam_test", SpellBeamTest::new);

    public static final RegistryObject<Item> SPELL_WITCH_LASH = ITEMS.register("spell_witch_lash", SpellWitchLash::new);


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}