package com.malignant.iter.common.registry;

import com.malignant.iter.IterMod;
import com.malignant.iter.common.misc.FlowerPotFiller;
import com.malignant.iter.common.misc.SpawnerPopulator;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModProcessors {
    public static final DeferredRegister<StructureProcessorType<?>> PROCESSORS =
            DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, IterMod.MOD_ID);

    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<SpawnerPopulator>> SPAWNER_POPULATOR =
            PROCESSORS.register("spawner_populator", () -> () -> SpawnerPopulator.CODEC);

    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<FlowerPotFiller>> FLOWER_POT_PROCESSOR =
            PROCESSORS.register("flower_pot_processor", () -> () -> FlowerPotFiller.CODEC);
}