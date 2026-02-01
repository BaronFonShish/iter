package com.malignant.itermod.common.event;

import com.malignant.itermod.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class AbyssquartzGrow {

    public static void execute(Level world, BlockPos pos) {

        if ((world.getRandom().nextInt(20) != 0) | !(isTouchingBedrock(world, pos))){
            return;
        }

        PlaceCrystal(world, pos);
    }

    private static boolean isTouchingBedrock(Level level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockPos adjacentPos = pos.relative(direction);
            if (level.getBlockState(adjacentPos).getBlock() == Blocks.BEDROCK) {
                return true;
            }
        }
        return false;
    }

    private static void PlaceCrystal(Level level, BlockPos pos) {
        List<Direction> directions = new ArrayList<>(Arrays.asList(Direction.values()));
        Collections.shuffle(directions);

        for (Direction direction : directions) {
            BlockPos targetPos = pos.relative(direction);
            if (level.isEmptyBlock(targetPos) && isValidCrystalPosition(level, targetPos, direction)) {

                BlockState crystalState = ModBlocks.ABYSSQUARTZ_CRYSTAL.get().defaultBlockState();
                if (crystalState.hasProperty(BlockStateProperties.FACING)) {
                    crystalState = crystalState.setValue(BlockStateProperties.FACING, direction);
                }

                level.setBlock(targetPos, crystalState, 3);
                return;
            }
        }
    }

    private static boolean isValidCrystalPosition(Level level, BlockPos pos, Direction growthDirection) {
        BlockPos supportPos = pos.relative(growthDirection.getOpposite());
        return level.getBlockState(supportPos).isSolid();
    }
}