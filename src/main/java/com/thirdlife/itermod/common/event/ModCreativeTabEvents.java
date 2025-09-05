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
                // Вкладка "Ингредиенты"
                new TabInsertion(CreativeModeTabs.INGREDIENTS, new ItemStack(Items.AMETHYST_SHARD), new ItemStack(ModItems.NOSTELON.get())),
                new TabInsertion(CreativeModeTabs.INGREDIENTS, new ItemStack(Items.IRON_INGOT), new ItemStack(ModItems.NOSTELON_PIECE.get())),

                // Вкладка "Строительные блоки"
                new TabInsertion(CreativeModeTabs.BUILDING_BLOCKS, new ItemStack(Items.STONE), new ItemStack(ModItems.NOSTELON.get())),
                new TabInsertion(CreativeModeTabs.BUILDING_BLOCKS, new ItemStack(Items.OAK_PLANKS), new ItemStack(ModItems.NOSTELON_PIECE.get())),

                // Вкладка "Боевая экипировка"
            new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(Items.FIREWORK_ROCKET), new ItemStack(ModItems.NOSTELON.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(Items.DIAMOND_SWORD), new ItemStack(ModItems.NOSTELON_PIECE.get()))
        );

        for (TabInsertion ins : insertions) {
            if (event.getTabKey() == ins.tab()) {
                // Вставляем после указанного предмета
                event.getEntries().putAfter(
                        ins.reference(),
                        ins.insert(),
                        CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
                );
            }
        }
    }
}
