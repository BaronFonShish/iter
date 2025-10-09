package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.common.item.DaggerItem;
import com.thirdlife.itermod.common.item.RotrootItem;
import com.thirdlife.itermod.common.item.TankardItem;
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
    public static final RegistryObject<Item> ROTROOT = ITEMS.register("rotroot",() -> new RotrootItem(new Item.Properties()));

    public static final RegistryObject<Item> WOODEN_DAGGER = ITEMS.register("wooden_dagger",
            () -> new DaggerItem(Tiers.WOOD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> STIONE_DAGGER = ITEMS.register("stone_dagger",
            () -> new DaggerItem(Tiers.STONE, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> IRON_DAGGER = ITEMS.register("iron_dagger",
            () -> new DaggerItem(Tiers.IRON, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GOLD_DAGGER = ITEMS.register("gold_dagger",
            () -> new DaggerItem(Tiers.GOLD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DIAMOND_DAGGER = ITEMS.register("diamond_dagger",
            () -> new DaggerItem(Tiers.DIAMOND, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> NETHERITE_DAGGER = ITEMS.register("netherite_dagger",
            () -> new DaggerItem(Tiers.NETHERITE, new Item.Properties().stacksTo(1)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}