package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.common.block.*;
import com.thirdlife.itermod.iterMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, iterMod.MOD_ID);

    public static final RegistryObject<Block> NOSTELON_ORE = registerBlock("nostelon_ore", NostelonOreBlock::new);
    public static final RegistryObject<Block> DEEPSLATE_NOSTELON_ORE = registerBlock("deepslate_nostelon_ore", DeepslateNostelonOreBlock::new);
    public static final RegistryObject<Block> ABYSSQUARTZ_BLOCK = registerBlock("abyssquartz_block", AbyssquartzBlock::new);
    public static final RegistryObject<Block> ABYSSQUARTZ_CRYSTAL = registerBlock("abyssquartz_crystal", AbyssquartzCrystal::new);
    public static final RegistryObject<Block> SANGUARNET_ORE = registerBlock("sanguarnet_ore", SanguarnetOreBlock::new);
    public static final RegistryObject<Block> COMPONENT_DEPOSIT = registerBlock("component_deposit", ComponentDepositBlock::new);
    public static final RegistryObject<Block> DEEPSLATE_SANGUARNET_ORE = registerBlock("deepslate_sanguarnet_ore", DeepslateSanguarnetOreBlock::new);
    public static final RegistryObject<Block> SPIDER_EGG = registerBlock("spider_egg", SpiderEggBlock::new);
    public static final RegistryObject<Block> ROTROOT = registerBlockItemless("rotroot",
            () -> new RotrootBlock(BlockBehaviour.Properties.copy(Blocks.BEETROOTS).sound(SoundType.CROP).noOcclusion().noCollission()));
    public static final RegistryObject<Block> ETHERBLOOM = registerBlock("etherbloom", Etherbloom::new);
    public static final RegistryObject<Block> ETHERBLOOM_PLANT = registerBlockItemless("etherbloom_plant",
            () -> new EtherbloomPlant(BlockBehaviour.Properties.copy(Blocks.POPPY).sound(SoundType.GRASS).noOcclusion().offsetType(BlockBehaviour.OffsetType.NONE).noCollission()));

    public static final RegistryObject<Block> ANCIENT_VASE = registerBlock("ancient_vase", AncientVaseBlock::new);
    public static final RegistryObject<Block> ANCIENT_BIG_VASE = registerBlock("ancient_big_vase", AncientBigVaseBlock::new);
    public static final RegistryObject<Block> ANCIENT_SMALL_VASE = registerBlock("ancient_small_vase", AncientSmallVaseBlock::new);

    public static final RegistryObject<Block> CRUNCHER = registerBlock("cruncher", CruncherBlock::new);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> registerBlockItemless(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        return toReturn;
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
