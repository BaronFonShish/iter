package com.malignant.iter.common.registry;

import com.malignant.iter.IterMod;
import com.malignant.iter.common.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;


import java.util.function.Supplier;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Registries.BLOCK, IterMod.MOD_ID);

    public static final DeferredHolder<Block, Block> NOSTELON_ORE = registerBlock("nostelon_ore", NostelonOreBlock::new);
    public static final DeferredHolder<Block, Block> DEEPSLATE_NOSTELON_ORE = registerBlock("deepslate_nostelon_ore", DeepslateNostelonOreBlock::new);
    public static final DeferredHolder<Block, Block> ABYSSQUARTZ_BLOCK = registerBlock("abyssquartz_block", AbyssquartzBlock::new);
    public static final DeferredHolder<Block, Block> ABYSSQUARTZ_CRYSTAL = registerBlock("abyssquartz_crystal", AbyssquartzCrystal::new);
    public static final DeferredHolder<Block, Block> SANGUARNET_ORE = registerBlock("sanguarnet_ore", SanguarnetOreBlock::new);
    public static final DeferredHolder<Block, Block> COMPONENT_DEPOSIT = registerBlock("component_deposit", ComponentDepositBlock::new);
    public static final DeferredHolder<Block, Block> DEEPSLATE_SANGUARNET_ORE = registerBlock("deepslate_sanguarnet_ore", DeepslateSanguarnetOreBlock::new);
    public static final DeferredHolder<Block, Block> MAGMANUM_ORE = registerBlock("magmanum_ore", MagmanumOreBlock::new);
    public static final DeferredHolder<Block, Block> SPIDER_EGG = registerBlock("spider_egg", SpiderEggBlock::new);
    public static final DeferredHolder<Block, Block> ROTROOT = registerBlockItemless("rotroot",
            () -> new RotrootBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS).sound(SoundType.CROP).noOcclusion().noCollission()));
    public static final DeferredHolder<Block, Block> ETHERBLOOM = registerBlock("etherbloom", Etherbloom::new);
    public static final DeferredHolder<Block, Block> ETHERBLOOM_PLANT = registerBlockItemless("etherbloom_plant",
            () -> new EtherbloomPlant(BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).sound(SoundType.GRASS).noOcclusion().offsetType(BlockBehaviour.OffsetType.NONE).noCollission()));

    public static final DeferredHolder<Block, Block> ANCIENT_VASE = registerBlock("ancient_vase", AncientVaseBlock::new);
    public static final DeferredHolder<Block, Block> ANCIENT_BIG_VASE = registerBlock("ancient_big_vase", AncientBigVaseBlock::new);
    public static final DeferredHolder<Block, Block> ANCIENT_SMALL_VASE = registerBlock("ancient_small_vase", AncientSmallVaseBlock::new);

    public static final DeferredHolder<Block, Block> CRUNCHER = registerBlock("cruncher", CruncherBlock::new);
    public static final DeferredHolder<Block, Block> GNAWER = registerBlock("gnawer", GnawerBlock::new);
    public static final DeferredHolder<Block, Block> VOID_MAW = registerBlock("void_maw", VoidMawBlock::new);
    public static final DeferredHolder<Block, Block> SPELLWEAVER_TABLE = registerBlock("spellweaver_table", SpellweaverTableBlock::new);

    private static <T extends Block> DeferredHolder<Block, T> registerBlock(String name, Supplier<T> block) {
        DeferredHolder<Block, T> registeredBlock = BLOCKS.register(name, block);
        registerBlockItem(name, registeredBlock);
        return registeredBlock;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredHolder<Block, T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.value(), new Item.Properties()));
    }

    private static <T extends Block> DeferredHolder<Block, T> registerBlockItemless(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
