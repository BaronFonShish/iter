package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.block.DeepslateNostelonOreBlock;
import com.thirdlife.itermod.block.NostelonOreBlock;
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

    public static final RegistryObject<Block> NOSTELON_ORE = registerBlock("nostelon_ore",
            () -> new NostelonOreBlock());
    public static final RegistryObject<Block> DEEPSLATE_NOSTELON_ORE = registerBlock("deepslate_nostelon_ore",
            () -> new DeepslateNostelonOreBlock());

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
