package com.thirdlife.itermod.common.event;

import com.thirdlife.itermod.common.registry.ModBlocks;
import com.thirdlife.itermod.common.registry.ModItems;
import com.thirdlife.itermod.iterMod;
import net.minecraft.client.renderer.entity.GuardianRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

@Mod.EventBusSubscriber(modid = iterMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeTabEvents {

    private static record TabInsertion(
            ResourceKey<CreativeModeTab> tab,
            ItemStack reference,
            ItemStack insert
    ) {
    }

    @SubscribeEvent
    public static void onCreativeTab(BuildCreativeModeTabContentsEvent event) {
        List<TabInsertion> insertions = List.of(
                new TabInsertion(CreativeModeTabs.INGREDIENTS, new ItemStack(Items.AMETHYST_SHARD), new ItemStack(ModItems.NOSTELON.get())),
                new TabInsertion(CreativeModeTabs.INGREDIENTS, new ItemStack(ModItems.NOSTELON.get()), new ItemStack(ModItems.ROUGH_NOSTELON.get())),
                new TabInsertion(CreativeModeTabs.INGREDIENTS, new ItemStack(ModItems.ROUGH_NOSTELON.get()), new ItemStack(ModItems.NOSTELON_NUGGET.get())),
                new TabInsertion(CreativeModeTabs.INGREDIENTS, new ItemStack(ModItems.NOSTELON_NUGGET.get()), new ItemStack(ModItems.ABYSSQUARTZ_SHARD.get())),
                new TabInsertion(CreativeModeTabs.INGREDIENTS, new ItemStack(Items.DISC_FRAGMENT_5), new ItemStack(ModItems.SPAWNER_FRAGMENT.get())),
                new TabInsertion(CreativeModeTabs.INGREDIENTS, new ItemStack(Items.BOWL), new ItemStack(ModItems.POTSHERD.get())),
                new TabInsertion(CreativeModeTabs.INGREDIENTS, new ItemStack(Items.STRING), new ItemStack(ModItems.SPIDER_SILK.get())),
                new TabInsertion(CreativeModeTabs.INGREDIENTS, new ItemStack(ModItems.ABYSSQUARTZ_SHARD.get()), new ItemStack(ModItems.ETHERDUST.get())),
                new TabInsertion(CreativeModeTabs.INGREDIENTS, new ItemStack(ModItems.ETHERDUST.get()), new ItemStack(ModItems.GIST.get())),
                new TabInsertion(CreativeModeTabs.INGREDIENTS, new ItemStack(ModItems.GIST.get()), new ItemStack(ModItems.INK_BOTTLE.get())),
                new TabInsertion(CreativeModeTabs.INGREDIENTS, new ItemStack(Items.FLINT), new ItemStack(ModItems.GOBSTEEL_SCRAP.get())),
                new TabInsertion(CreativeModeTabs.INGREDIENTS, new ItemStack(ModItems.GOBSTEEL_SCRAP.get()), new ItemStack(ModItems.DWARVEN_COMPONENTS.get())),
                new TabInsertion(CreativeModeTabs.INGREDIENTS, new ItemStack(Items.LEATHER), new ItemStack(ModItems.ABSTRUSE_CLOTH.get())),

                new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, new ItemStack(Blocks.DEEPSLATE_LAPIS_ORE), new ItemStack(ModBlocks.NOSTELON_ORE.get())),
                new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, new ItemStack(ModBlocks.NOSTELON_ORE.get()), new ItemStack(ModBlocks.DEEPSLATE_NOSTELON_ORE.get())),
                new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, new ItemStack(ModBlocks.DEEPSLATE_NOSTELON_ORE.get()), new ItemStack(ModBlocks.SANGUARNET_ORE.get())),
                new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, new ItemStack(ModBlocks.SANGUARNET_ORE.get()), new ItemStack(ModBlocks.DEEPSLATE_SANGUARNET_ORE.get())),
                new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, new ItemStack(Blocks.DEEPSLATE_DIAMOND_ORE), new ItemStack(ModBlocks.COMPONENT_DEPOSIT.get())),
                new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, new ItemStack(Blocks.AMETHYST_CLUSTER), new ItemStack(ModBlocks.ABYSSQUARTZ_BLOCK.get())),
                new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, new ItemStack(ModBlocks.ABYSSQUARTZ_BLOCK.get()), new ItemStack(ModBlocks.ABYSSQUARTZ_CRYSTAL.get())),
                new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, new ItemStack(ModBlocks.ABYSSQUARTZ_CRYSTAL.get()), new ItemStack(ModBlocks.SPIDER_EGG.get())),
                new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, new ItemStack(Items.BEETROOT_SEEDS), new ItemStack(ModItems.ROTROOT_SEEDS.get())),
                new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, new ItemStack(Items.SPORE_BLOSSOM), new ItemStack(ModBlocks.ETHERBLOOM.get())),
                new TabInsertion(CreativeModeTabs.NATURAL_BLOCKS, new ItemStack(Items.SWEET_BERRIES), new ItemStack(ModItems.ETHERBLOOM_SEEDS.get())),

                new TabInsertion(CreativeModeTabs.FUNCTIONAL_BLOCKS, new ItemStack(Blocks.SUSPICIOUS_GRAVEL), new ItemStack(ModBlocks.ANCIENT_SMALL_VASE.get())),
                new TabInsertion(CreativeModeTabs.FUNCTIONAL_BLOCKS, new ItemStack(ModBlocks.ANCIENT_SMALL_VASE.get()), new ItemStack(ModBlocks.ANCIENT_VASE.get())),
                new TabInsertion(CreativeModeTabs.FUNCTIONAL_BLOCKS, new ItemStack(ModBlocks.ANCIENT_VASE.get()), new ItemStack(ModBlocks.ANCIENT_BIG_VASE.get())),

                new TabInsertion(CreativeModeTabs.FOOD_AND_DRINKS, new ItemStack(Items.BEETROOT), new ItemStack(ModItems.ROTROOT.get())),

                new TabInsertion(CreativeModeTabs.REDSTONE_BLOCKS, new ItemStack(Blocks.REDSTONE_ORE), new ItemStack(ModBlocks.CRUNCHER.get())),

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
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.DIAMOND_SCYTHE.get()), new ItemStack(ModItems.NETHERITE_SCYTHE.get())),

                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(Items.BOW), new ItemStack(ModItems.RECURVE_BOW.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.RECURVE_BOW.get()), new ItemStack(ModItems.STINGER.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(Items.NETHERITE_SWORD), new ItemStack(ModItems.GOBSTEEL_SWORD.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(Items.NETHERITE_AXE), new ItemStack(ModItems.GOBSTEEL_AXE.get())),

                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(Items.TURTLE_HELMET), new ItemStack(ModItems.APPRENTICE_HOOD.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.APPRENTICE_HOOD.get()), new ItemStack(ModItems.APPRENTICE_ROBES.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.APPRENTICE_ROBES.get()), new ItemStack(ModItems.APPRENTICE_PANTS.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.APPRENTICE_PANTS.get()), new ItemStack(ModItems.APPRENTICE_BOOTS.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.APPRENTICE_BOOTS.get()), new ItemStack(ModItems.AZURE_HOOD.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.AZURE_HOOD.get()), new ItemStack(ModItems.AZURE_MANTLE.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.AZURE_MANTLE.get()), new ItemStack(ModItems.AZURE_PANTS.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.AZURE_PANTS.get()), new ItemStack(ModItems.AZURE_BOOTS.get())),

                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.AZURE_BOOTS.get()), new ItemStack(ModItems.IRON_RING.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.IRON_RING.get()), new ItemStack(ModItems.GOLDEN_RING.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.GOLDEN_RING.get()), new ItemStack(ModItems.NETHERITE_RING.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.NETHERITE_RING.get()), new ItemStack(ModItems.IRON_RING_DIAMOND.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.IRON_RING_DIAMOND.get()), new ItemStack(ModItems.GOLDEN_RING_DIAMOND.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.GOLDEN_RING_DIAMOND.get()), new ItemStack(ModItems.NETHERITE_RING_DIAMOND.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.NETHERITE_RING_DIAMOND.get()), new ItemStack(ModItems.IRON_RING_EMERALD.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.IRON_RING_EMERALD.get()), new ItemStack(ModItems.GOLDEN_RING_EMERALD.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.GOLDEN_RING_EMERALD.get()), new ItemStack(ModItems.NETHERITE_RING_EMERALD.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.NETHERITE_RING_EMERALD.get()), new ItemStack(ModItems.IRON_RING_NOSTELON.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.IRON_RING_NOSTELON.get()), new ItemStack(ModItems.GOLDEN_RING_NOSTELON.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.GOLDEN_RING_NOSTELON.get()), new ItemStack(ModItems.NETHERITE_RING_NOSTELON.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.NETHERITE_RING_NOSTELON.get()), new ItemStack(ModItems.IRON_RING_ABYSSQUARTZ.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.IRON_RING_ABYSSQUARTZ.get()), new ItemStack(ModItems.GOLDEN_RING_ABYSSQUARTZ.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.GOLDEN_RING_ABYSSQUARTZ.get()), new ItemStack(ModItems.NETHERITE_RING_ABYSSQUARTZ.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.NETHERITE_RING_ABYSSQUARTZ.get()), new ItemStack(ModItems.IRON_RING_AMETHYST.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.IRON_RING_AMETHYST.get()), new ItemStack(ModItems.GOLDEN_RING_AMETHYST.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.GOLDEN_RING_AMETHYST.get()), new ItemStack(ModItems.NETHERITE_RING_AMETHYST.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.NETHERITE_RING_AMETHYST.get()), new ItemStack(ModItems.IRON_RING_SANGUARNET.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.IRON_RING_SANGUARNET.get()), new ItemStack(ModItems.GOLDEN_RING_SANGUARNET.get())),
                new TabInsertion(CreativeModeTabs.COMBAT, new ItemStack(ModItems.GOLDEN_RING_SANGUARNET.get()), new ItemStack(ModItems.NETHERITE_RING_SANGUARNET.get())),

                new TabInsertion(CreativeModeTabs.TOOLS_AND_UTILITIES, new ItemStack(Items.NETHERITE_HOE), new ItemStack(ModItems.GOBSTEEL_SHOVEL.get())),
                new TabInsertion(CreativeModeTabs.TOOLS_AND_UTILITIES, new ItemStack(ModItems.GOBSTEEL_SHOVEL.get()), new ItemStack(ModItems.GOBSTEEL_PICKAXE.get())),
                new TabInsertion(CreativeModeTabs.TOOLS_AND_UTILITIES, new ItemStack(ModItems.GOBSTEEL_PICKAXE.get()), new ItemStack(ModItems.GOBSTEEL_AXE.get())),
                new TabInsertion(CreativeModeTabs.TOOLS_AND_UTILITIES, new ItemStack(ModItems.GOBSTEEL_AXE.get()), new ItemStack(ModItems.GOBSTEEL_HOE.get())),

                new TabInsertion(CreativeModeTabs.SPAWN_EGGS, new ItemStack(Items.ZOMBIFIED_PIGLIN_SPAWN_EGG), new ItemStack(ModItems.GIANT_SPIDER_SPAWN_EGG.get())),
                new TabInsertion(CreativeModeTabs.SPAWN_EGGS, new ItemStack(ModItems.GIANT_SPIDER_SPAWN_EGG.get()), new ItemStack(ModItems.SPIDERLING_SPAWN_EGG.get())),
                new TabInsertion(CreativeModeTabs.SPAWN_EGGS, new ItemStack(ModItems.SPIDERLING_SPAWN_EGG.get()), new ItemStack(ModItems.GOBLIN_SPAWN_EGG.get())),
                new TabInsertion(CreativeModeTabs.SPAWN_EGGS, new ItemStack(ModItems.GOBLIN_SPAWN_EGG.get()), new ItemStack(ModItems.GOBLIN_WARRIOR_SPAWN_EGG.get()))
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