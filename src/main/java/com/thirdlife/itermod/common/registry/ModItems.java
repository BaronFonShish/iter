package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.common.item.TankardItem;
import com.thirdlife.itermod.iterMod;
import net.minecraft.world.item.Item;
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


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}