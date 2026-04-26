package com.malignant.itermod.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModStructures {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_TYPE, "iter");

    public static final RegistryObject<StructureType<JigsawStructure>> SIMPLE_MONSTER_ROOM =
            STRUCTURE_TYPES.register("simple_monster_room",
                    () -> () -> JigsawStructure.CODEC);
    public static final RegistryObject<StructureType<JigsawStructure>> GENERIC_DUNGEON =
            STRUCTURE_TYPES.register("generic_dungeon",
                    () -> () -> JigsawStructure.CODEC);
    public static final RegistryObject<StructureType<JigsawStructure>> SMALL_GENERIC_DUNGEON =
            STRUCTURE_TYPES.register("small_generic_dungeon",
                    () -> () -> JigsawStructure.CODEC);
}
