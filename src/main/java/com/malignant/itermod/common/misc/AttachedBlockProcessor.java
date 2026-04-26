package com.malignant.itermod.common.misc;

import com.malignant.itermod.common.registry.ModProcessors;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class AttachedBlockProcessor extends StructureProcessor {

    public static final Codec<AttachedBlockProcessor> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("chance").forGetter(p -> p.chance),
                    BuiltInRegistries.BLOCK.byNameCodec().listOf().fieldOf("possible_blocks").forGetter(p -> p.possibleBlocks),
                    Codec.STRING.fieldOf("attachment_type").forGetter(p -> p.attachmentType),
                    Codec.STRING.optionalFieldOf("rotation_type", "none").forGetter(p -> p.rotationType),
                    Codec.STRING.optionalFieldOf("set_rotation").forGetter(p -> p.setRotation)
            ).apply(instance, AttachedBlockProcessor::new)
    );

    private final float chance;
    private final List<Block> possibleBlocks;
    private final String attachmentType;
    private final String rotationType;
    private final Optional<String> setRotation;

    public AttachedBlockProcessor(float chance, List<Block> possibleBlocks, String attachmentType,
                                  String rotationType, Optional<String> setRotation) {
        this.chance = chance;
        this.possibleBlocks = possibleBlocks;
        this.attachmentType = attachmentType;
        this.rotationType = rotationType;
        this.setRotation = setRotation;
    }

    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader levelReader,
                                                        BlockPos seedPos,           // Position of the structure in the world
                                                        BlockPos templatePos,       // Position within the template
                                                        StructureTemplate.StructureBlockInfo rawBlockInfo,  // Original block from template
                                                        StructureTemplate.StructureBlockInfo processedBlockInfo,     // Current block in world (for reference)
                                                        StructurePlaceSettings placementSettings,
                                                        @Nullable StructureTemplate template   // The entire structure template (new parameter!)
    ) {
        BlockPos truepos = processedBlockInfo.pos();
        RandomSource random = placementSettings.getRandom(truepos);

        if (!processedBlockInfo.state().isAir()) {
            return processedBlockInfo;
        }

        if (random.nextFloat() >= chance) {
            return processedBlockInfo;
        }

        if (!canAttach(levelReader, truepos)) {
            return processedBlockInfo;
        }

        Block selectedBlock = possibleBlocks.get(random.nextInt(possibleBlocks.size()));
        BlockState newState = selectedBlock.defaultBlockState();

        newState = applyRotation(newState, random);

        return new StructureTemplate.StructureBlockInfo(
                processedBlockInfo.pos(),
                newState,
                processedBlockInfo.nbt()
        );
    }

    private boolean canAttach(LevelReader level, BlockPos pos) {
        switch (attachmentType.toLowerCase()) {
            case "floor":
                BlockPos belowPos = pos.offset(0, -1, 0);
                return level.getBlockState(belowPos).isSolid();

            case "ceiling":
                BlockPos abovePos = pos.offset(0, 1, 0);
                return level.getBlockState(abovePos).isSolid();

            case "wall":
                BlockPos northPos = pos.offset(0, 0, -1);
                BlockPos southPos = pos.offset(0, 0, 1);
                BlockPos eastPos = pos.offset(1, 0, 0);
                BlockPos westPos = pos.offset(-1, 0, 0);

                return level.getBlockState(northPos).isSolid() ||
                        level.getBlockState(southPos).isSolid() ||
                        level.getBlockState(eastPos).isSolid() ||
                        level.getBlockState(westPos).isSolid();

            default:
                return false;
        }
    }

    private BlockState applyRotation(BlockState state, RandomSource random) {
        String rotType = rotationType.toLowerCase();

        if ("set".equals(rotType) && setRotation.isPresent()) {
            Direction direction = getDirectionFromString(setRotation.get());
            if (direction != null) {
                return applyDirectionToState(state, direction);
            }
            return state;
        }

        switch (rotType) {
            case "none":
                return state;

            case "random_4":
                Direction[] horizontalDirs = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
                Direction randomHorizontal = horizontalDirs[random.nextInt(horizontalDirs.length)];
                return applyDirectionToState(state, randomHorizontal);

            case "random_6":
                Direction[] allDirs = Direction.values();
                Direction randomDirection = allDirs[random.nextInt(allDirs.length)];
                return applyDirectionToState(state, randomDirection);

            default:
                return state;
        }
    }

    private Direction getDirectionFromString(String directionName) {
        switch (directionName.toLowerCase()) {
            case "north": return Direction.NORTH;
            case "south": return Direction.SOUTH;
            case "east": return Direction.EAST;
            case "west": return Direction.WEST;
            case "up": return Direction.UP;
            case "down": return Direction.DOWN;
            default: return null;
        }
    }

    private BlockState applyDirectionToState(BlockState state, Direction direction) {
        if (state.hasProperty(BlockStateProperties.FACING)) {
            return state.setValue(BlockStateProperties.FACING, direction);
        }

        if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING) && direction.getAxis().isHorizontal()) {
            return state.setValue(BlockStateProperties.HORIZONTAL_FACING, direction);
        }

        if (state.hasProperty(BlockStateProperties.FACING_HOPPER) && direction != Direction.UP) {
            return state.setValue(BlockStateProperties.FACING_HOPPER, direction);
        }

        return state;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModProcessors.ATTACHED_BLOCK_PROCESSOR.get();
    }
}
