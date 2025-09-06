package com.thirdlife.itermod.common.event;

import com.thirdlife.itermod.common.registry.ModItems;
import com.thirdlife.itermod.iterMod;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = iterMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeTabEvents {

    private static record TabInsertion(
            ResourceKey<CreativeModeTab> tab,
            ItemStack reference,
            ItemStack insert
    ) {}

    @SubscribeEvent
    public static void onCreativeTab(BuildCreativeModeTabContentsEvent event) {
        List<TabInsertion> insertions = List.of(

                new TabInsertion(CreativeModeTabs.INGREDIENTS, new ItemStack(Items.AMETHYST_SHARD), new ItemStack(ModItems.NOSTELON.get())),
                new TabInsertion(CreativeModeTabs.INGREDIENTS, new ItemStack(ModItems.NOSTELON.get()), new ItemStack(ModItems.NOSTELON_NUGGET.get())),
                new TabInsertion(CreativeModeTabs.INGREDIENTS, new ItemStack(Items.RAW_GOLD), new ItemStack(ModItems.ROUGH_NOSTELON.get()))

        );

        for (TabInsertion ins : insertions) {
            if (event.getTabKey() == ins.tab()) {
                event.getEntries().putAfter(
                        ins.reference(),
                        ins.insert(),
                        CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
                );
            }
        }
    }
}
