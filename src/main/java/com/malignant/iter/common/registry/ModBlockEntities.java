package com.malignant.iter.common.registry;

import com.malignant.iter.common.block.CruncherBlockEntity;
import com.malignant.iter.common.block.GnawerBlockEntity;
import com.malignant.iter.common.block.VoidMawBlockEntity;
import com.malignant.iter.IterMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;


public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, IterMod.MOD_ID);


    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> CRUNCHER = register("cruncher", ModBlocks.CRUNCHER, CruncherBlockEntity::new);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> GNAWER = register("gnawer", ModBlocks.GNAWER, GnawerBlockEntity::new);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> VOID_MAW = register("void_maw", ModBlocks.VOID_MAW, VoidMawBlockEntity::new);

    private static DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> register(String registryname, DeferredHolder<Block, Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
        return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
    }
}
