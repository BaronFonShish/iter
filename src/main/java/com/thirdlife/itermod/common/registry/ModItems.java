package com.thirdlife.itermod.common.registry;

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
    public static final RegistryObject<Item> RAW_NOSTELON = ITEMS.register("raw_nostelon", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NOSTELON_PIECE = ITEMS.register("nostelon_piece", () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}