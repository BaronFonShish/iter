package com.malignant.itermod.common.registry;

import com.malignant.itermod.common.misc.AttachedBlockProcessor;
import com.malignant.itermod.common.misc.FlowerPotFiller;
import com.malignant.itermod.common.misc.SpawnerPopulator;
import com.malignant.itermod.iterMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModProcessors {
    public static final DeferredRegister<StructureProcessorType<?>> PROCESSORS =
            DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, iterMod.MOD_ID);

    public static final RegistryObject<StructureProcessorType<SpawnerPopulator>> SPAWNER_POPULATOR =
            PROCESSORS.register("spawner_populator", () -> () -> SpawnerPopulator.CODEC);

    public static final RegistryObject<StructureProcessorType<FlowerPotFiller>> FLOWER_POT_PROCESSOR =
            PROCESSORS.register("flower_pot_processor", () -> () -> FlowerPotFiller.CODEC);

    public static final RegistryObject<StructureProcessorType<AttachedBlockProcessor>> ATTACHED_BLOCK_PROCESSOR =
            PROCESSORS.register("attached_block_processor", () -> () -> AttachedBlockProcessor.CODEC);
}
