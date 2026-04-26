package com.malignant.itermod.common.misc;

import com.malignant.itermod.common.registry.ModProcessors;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;
import java.util.List;

public class FlowerPotFiller extends StructureProcessor {

    public static final Codec<FlowerPotFiller> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("chance").forGetter(p -> p.chance),
                    BuiltInRegistries.BLOCK.byNameCodec().listOf().fieldOf("potted_plants").forGetter(p -> p.potted_plants)
            ).apply(instance, FlowerPotFiller::new)
    );

    private final float chance;
    private final List<Block> potted_plants;

    public FlowerPotFiller(float chance, List<Block> potted_plants) {
        this.chance = chance;
        this.potted_plants = potted_plants;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader levelReader,
                                                        BlockPos seedPos,
                                                        BlockPos templatePos,
                                                        StructureTemplate.StructureBlockInfo rawBlockInfo,
                                                        StructureTemplate.StructureBlockInfo processedBlockInfo,
                                                        StructurePlaceSettings placementSettings,
                                                        @Nullable StructureTemplate template
    ) {

        if (processedBlockInfo.state().getBlock() != Blocks.FLOWER_POT) {
            return processedBlockInfo;
        }

        BlockPos truepos = processedBlockInfo.pos();
        RandomSource random = RandomSource.create();
        random.setSeed(truepos.asLong());

        if (processedBlockInfo.state().getBlock() == Blocks.FLOWER_POT && random.nextFloat() < chance) {

            Block plantBlock = potted_plants.get(random.nextInt(potted_plants.size()));

            return new StructureTemplate.StructureBlockInfo(
                    processedBlockInfo.pos(),
                    plantBlock.defaultBlockState(),
                    processedBlockInfo.nbt()
            );
        }
        return processedBlockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModProcessors.FLOWER_POT_PROCESSOR.get();
    }
}
