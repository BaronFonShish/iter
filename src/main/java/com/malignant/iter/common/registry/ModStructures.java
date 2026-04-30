package com.malignant.iter.common.registry;

import com.malignant.iter.IterMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModStructures {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_TYPE, IterMod.MOD_ID);

    public static final DeferredRegister<Structure> STRUCTURES =
            DeferredRegister.create(Registries.STRUCTURE, IterMod.MOD_ID);

    public static final Supplier<StructureType<JigsawStructure>> SIMPLE_MONSTER_ROOM =
            STRUCTURE_TYPES.register("simple_monster_room",
                    () -> () -> JigsawStructure.CODEC);

    public static final Supplier<StructureType<JigsawStructure>> GENERIC_DUNGEON =
            STRUCTURE_TYPES.register("generic_dungeon",
                    () -> () -> JigsawStructure.CODEC);

    public static final Supplier<StructureType<JigsawStructure>> SMALL_GENERIC_DUNGEON =
            STRUCTURE_TYPES.register("small_generic_dungeon",
                    () -> () -> JigsawStructure.CODEC);
}