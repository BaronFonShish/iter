package com.malignant.itermod.common.registry;

import com.malignant.itermod.common.block.CruncherBlockEntity;
import com.malignant.itermod.common.block.GnawerBlockEntity;
import com.malignant.itermod.common.block.VoidMawBlockEntity;
import com.malignant.itermod.common.block.*;
import com.malignant.itermod.iterMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, iterMod.MOD_ID);


    public static final RegistryObject<BlockEntityType<?>> CRUNCHER = register("cruncher", ModBlocks.CRUNCHER, CruncherBlockEntity::new);
    public static final RegistryObject<BlockEntityType<?>> GNAWER = register("gnawer", ModBlocks.GNAWER, GnawerBlockEntity::new);
    public static final RegistryObject<BlockEntityType<?>> VOID_MAW = register("void_maw", ModBlocks.VOID_MAW, VoidMawBlockEntity::new);

    private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
        return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
    }
}
