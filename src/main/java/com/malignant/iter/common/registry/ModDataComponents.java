package com.malignant.iter.common.registry;

import com.malignant.iter.IterMod;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, IterMod.MOD_ID);

    public static final Supplier<DataComponentType<String>> FLAYING_UUID =
            DATA_COMPONENTS.registerComponentType("flaying_uuid",
                    builder -> builder.persistent(Codec.STRING));

    public static final Supplier<DataComponentType<Integer>> FLAYING_STACK =
            DATA_COMPONENTS.registerComponentType("flaying_stack",
                    builder -> builder.persistent(Codec.INT));

    public static final Supplier<DataComponentType<Integer>> SPELL_QUALITY =
            DATA_COMPONENTS.registerComponentType("spell_quality",
                    builder -> builder.persistent(Codec.INT));
}
