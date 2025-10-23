package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.common.item.*;
import com.thirdlife.itermod.common.item.magic.BoneStaff;
import com.thirdlife.itermod.common.item.magic.SpellBook;
import com.thirdlife.itermod.iterMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.ItemLike;
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
    public static final RegistryObject<Item> SPIDER_SILK = ITEMS.register("spider_silk", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ABYSSQUARTZ_SHARD = ITEMS.register("abyssquartz_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ETHERDUST = ITEMS.register("etherdust", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> POTSHERD = ITEMS.register("potsherd", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_COIN = ITEMS.register("gold_coin", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COPPER_COIN = ITEMS.register("copper_coin", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_COIN = ITEMS.register("diamond_coin", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TANKARD = ITEMS.register("tankard", () -> new TankardItem(new Item.Properties()));
    public static final RegistryObject<Item> SPAWNER_FRAGMENT = ITEMS.register("spawner_fragment", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ROTROOT_SEEDS = ITEMS.register("rotroot_seeds", () -> new ItemNameBlockItem(ModBlocks.ROTROOT.get(), new Item.Properties()));
    public static final RegistryObject<Item> ETHERBLOOM_SEEDS = ITEMS.register("etherbloom_seeds", () -> new ItemNameBlockItem(ModBlocks.ETHERBLOOM_PLANT.get(), new Item.Properties()));
    public static final RegistryObject<Item> ROTROOT = ITEMS.register("rotroot",() -> new RotrootItem(new Item.Properties()));
    public static final RegistryObject<Item> SPELL_BOOK = ITEMS.register("spell_book", SpellBook::new);

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

    public static final RegistryObject<Item> BONE_STAFF = ITEMS.register("bone_staff",() -> new BoneStaff());


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}