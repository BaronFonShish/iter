package com.thirdlife.itermod.common.event;

import com.thirdlife.itermod.common.registry.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class ModCreativeTabEvents {
    private static record ItemInsertion(ItemStack after, ItemStack insert) {}

    @SubscribeEvent
    public static void onCreativeTab(BuildCreativeModeTabContentsEvent event) {

        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {

            // ItemStack для своих предметов
            ItemStack nostelon = new ItemStack(ModItems.NOSTELON.get());

            // список вставок (вставляем предмет после указанного)
            List<ItemInsertion> insertions = new ArrayList<>();
            insertions.add(new ItemInsertion(new ItemStack(Items.AMETHYST_SHARD), nostelon));

            // применение вставок
            for (ItemInsertion insertion : insertions) {
                event.getEntries().putAfter(
                        insertion.after(),
                        insertion.insert(),
                        CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
                );
            }
        }

    }
}
