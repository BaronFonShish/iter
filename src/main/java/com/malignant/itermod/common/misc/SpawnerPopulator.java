package com.malignant.itermod.common.misc;

import com.malignant.itermod.common.registry.ModProcessors;
import com.malignant.itermod.iterMod;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;
import java.util.List;

public class SpawnerPopulator extends StructureProcessor {

    public static final Codec<SpawnerPopulator> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.list(ResourceLocation.CODEC).fieldOf("possible_mobs").forGetter(p -> p.possibleMobs)
            ).apply(instance, SpawnerPopulator::new)
    );

    private final List<ResourceLocation> possibleMobs;

    public SpawnerPopulator(List<ResourceLocation> possibleMobs) {
        this.possibleMobs = possibleMobs;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader levelReader,
                                                        BlockPos seedPos,           // Position of the structure in the world
                                                        BlockPos templatePos,       // Position within the template
                                                        StructureTemplate.StructureBlockInfo rawBlockInfo,  // Original block from template
                                                        StructureTemplate.StructureBlockInfo processedBlockInfo,     // Current block in world (for reference)
                                                        StructurePlaceSettings placementSettings,
                                                        @Nullable StructureTemplate template   // The entire structure template (new parameter!)
    ) {

        if (processedBlockInfo.state().getBlock() == Blocks.SPAWNER) {
            BlockPos truepos = processedBlockInfo.pos();
            ResourceLocation mobId = getRandomMob(placementSettings.getRandom(truepos));

            CompoundTag spawnerNbt = new CompoundTag();

            CompoundTag spawnData = new CompoundTag();
            CompoundTag entityData = new CompoundTag();
            entityData.putString("id", mobId.toString());
            spawnData.put("entity", entityData);
            spawnerNbt.put("SpawnData", spawnData);

            ListTag spawnPotentials = new ListTag();
            CompoundTag potential = new CompoundTag();
            potential.put("data", spawnData.copy());
            potential.putInt("weight", 1);
            spawnPotentials.add(potential);
            spawnerNbt.put("SpawnPotentials", spawnPotentials);

            spawnerNbt.putShort("SpawnRange", (short) 4);
            spawnerNbt.putShort("MinSpawnDelay", (short) 200);
            spawnerNbt.putShort("MaxSpawnDelay", (short) 800);
            spawnerNbt.putShort("Delay", (short) 20);
            spawnerNbt.putInt("MaxNearbyEntities", (int) 4);
            spawnerNbt.putInt("RequiredPlayerRange", (int) 16);
            spawnerNbt.putInt("SpawnCount", (int) 4);

            return new StructureTemplate.StructureBlockInfo(
                    processedBlockInfo.pos(),
                    processedBlockInfo.state(),
                    spawnerNbt
            );
        }

        return processedBlockInfo;
    }

    private ResourceLocation getRandomMob(RandomSource random) {
        return possibleMobs.get(random.nextInt(possibleMobs.size()));
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModProcessors.SPAWNER_POPULATOR.get();
    }
}
