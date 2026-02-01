package com.malignant.itermod.common.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BlockPlacementChecks {

    public static boolean isFaceSolid(Level level, BlockPos pos, Direction direction) {
        BlockState state = level.getBlockState(pos);

        if (state.isFaceSturdy(level, pos, direction)) {
            return true;
        }

        return checkSpecialCases(level, pos, state, direction);
    }
    private static boolean checkSpecialCases(Level level, BlockPos pos, BlockState state, Direction direction) {
        return state.isSolidRender(level, pos) &&
                state.getBlock().getCollisionShape(state, level, pos, null).isEmpty();
    }
}
