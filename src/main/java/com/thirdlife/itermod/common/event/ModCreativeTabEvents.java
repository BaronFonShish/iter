package com.thirdlife.itermod.common.event;

import com.thirdlife.itermod.common.registry.ModBlocks;
import com.thirdlife.itermod.common.registry.ModItems;
import com.thirdlife.itermod.iterMod;
import net.minecraft.client.renderer.entity.GuardianRenderer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
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
                new TabInsertion(CreativeModeTabs.INGREDIENTS, new ItemStack(ModItems.NOSTELON.get()), new ItemStack(ModItems.ROUGH_NOSTELON.get())),
                new TabInsertion(CreativeModeTabs.INGREDIENTS, new ItemStack(ModItems.ROUGH_NOSTELON.get()), new ItemStack(ModItems.NOSTELON_NUGGET.get())),

                new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, new ItemStack(Blocks.DEEPSLATE_LAPIS_ORE), new ItemStack(ModBlocks.NOSTELON_ORE.get())),
                new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, new ItemStack(ModBlocks.NOSTELON_ORE.get()), new ItemStack(ModBlocks.DEEPSLATE_NOSTELON_ORE.get())),
                new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, new ItemStack(Blocks.AMETHYST_CLUSTER), new ItemStack(ModBlocks.ABYSSQUARTZ_BLOCK.get())),
                new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, new ItemStack(ModBlocks.ABYSSQUARTZ_BLOCK.get()), new ItemStack(ModBlocks.ABYSSQUARTZ_CRYSTAL.get())),
                new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, new ItemStack(ModBlocks.ABYSSQUARTZ_CRYSTAL.get()), new ItemStack(ModBlocks.SPIDER_EGG.get())),
                new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, new ItemStack(Items.BEETROOT_SEEDS), new ItemStack(ModItems.ROTROOT_SEEDS.get())),
                new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, new ItemStack(Items.SPORE_BLOSSOM), new ItemStack(ModBlocks.ETHERBLOOM.get())),
                new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, new ItemStack(Items.SWEET_BERRIES), new ItemStack(ModItems.ETHERBLOOM_SEEDS.get())),

                new TabInsertion(CreativeModeTabs.FOOD_AND_DRINKS, new ItemStack(Items.BEETROOT), new ItemStack(ModItems.ROTROOT.get())),

                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(Items.NETHERITE_SWORD), new ItemStack(ModItems.WOODEN_DAGGER.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.WOODEN_DAGGER.get()), new ItemStack(ModItems.STONE_DAGGER.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.STONE_DAGGER.get()), new ItemStack(ModItems.IRON_DAGGER.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.IRON_DAGGER.get()), new ItemStack(ModItems.GOLDEN_DAGGER.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.GOLDEN_DAGGER.get()), new ItemStack(ModItems.DIAMOND_DAGGER.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.DIAMOND_DAGGER.get()), new ItemStack(ModItems.NETHERITE_DAGGER.get())),

                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(Items.NETHERITE_AXE), new ItemStack(ModItems.WOODEN_FLAIL.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.WOODEN_FLAIL.get()), new ItemStack(ModItems.STONE_FLAIL.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.STONE_FLAIL.get()), new ItemStack(ModItems.IRON_FLAIL.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.IRON_FLAIL.get()), new ItemStack(ModItems.GOLDEN_FLAIL.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.GOLDEN_FLAIL.get()), new ItemStack(ModItems.DIAMOND_FLAIL.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.DIAMOND_FLAIL.get()), new ItemStack(ModItems.NETHERITE_FLAIL.get())),

                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.NETHERITE_FLAIL.get()), new ItemStack(ModItems.WOODEN_SPEAR.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.WOODEN_SPEAR.get()), new ItemStack(ModItems.STONE_SPEAR.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.STONE_SPEAR.get()), new ItemStack(ModItems.IRON_SPEAR.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.IRON_SPEAR.get()), new ItemStack(ModItems.GOLDEN_SPEAR.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.GOLDEN_SPEAR.get()), new ItemStack(ModItems.DIAMOND_SPEAR.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.DIAMOND_SPEAR.get()), new ItemStack(ModItems.NETHERITE_SPEAR.get())),

                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.NETHERITE_SPEAR.get()), new ItemStack(ModItems.WOODEN_SCYTHE.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.WOODEN_SCYTHE.get()), new ItemStack(ModItems.STONE_SCYTHE.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.STONE_SCYTHE.get()), new ItemStack(ModItems.IRON_SCYTHE.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.IRON_SCYTHE.get()), new ItemStack(ModItems.GOLDEN_SCYTHE.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.GOLDEN_SCYTHE.get()), new ItemStack(ModItems.DIAMOND_SCYTHE.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.DIAMOND_SCYTHE.get()), new ItemStack(ModItems.NETHERITE_SCYTHE.get()))



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
