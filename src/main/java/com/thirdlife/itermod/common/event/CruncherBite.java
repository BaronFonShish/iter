package com.thirdlife.itermod.common.event;

import com.thirdlife.itermod.common.block.CruncherBlock;
import com.thirdlife.itermod.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.TierSortingRegistry;



public class CruncherBite {

    public static void tick(Level world, BlockPos pos, BlockState blockstate){

        BlockEntity cruncher = world.getBlockEntity(pos);
        assert cruncher != null;
        Direction facing = blockstate.getValue(CruncherBlock.FACING);
        BlockPos targetPos = pos.relative(facing);
        BlockState targetblock = world.getBlockState(targetPos);

        if (!world.hasNeighborSignal(pos)) {
            cruncher.getPersistentData().putInt("biteforce", 0);
            cruncher.getPersistentData().putInt("chewtimer", 0);
            world.sendBlockUpdated(pos, blockstate, blockstate, 3);
            if (blockstate.getValue(CruncherBlock.CHEWING) != 0) {
                world.setBlock(pos, blockstate.setValue(CruncherBlock.CHEWING, 0), 3);
            }
            return;
        }

            if (blockstate.getValue(CruncherBlock.CHEWING) != 1){
                world.setBlock(pos, blockstate.setValue(CruncherBlock.CHEWING, 1), 3);
            } else {
                world.setBlock(pos, blockstate.setValue(CruncherBlock.CHEWING, 2), 3);
                cruncher.getPersistentData().putInt("biteforce", (cruncher.getPersistentData().getInt("biteforce") + 1));
            }

        ///cruncher.getPersistentData().putInt("chewtimer", 0);
           /// world.sendBlockUpdated(pos, blockstate, blockstate, 3);

        if (targetblock.getBlock() instanceof AirBlock) {
            cruncher.getPersistentData().putInt("biteforce", 0);
            return;
        }

        double harvestlevel = new Object() {
            public int getHarvestLevel(BlockState targetblock) {
                return TierSortingRegistry.getSortedTiers().stream().filter(t -> t.getTag() != null && targetblock.is(t.getTag())).map(Tier::getLevel).findFirst().orElse(0);}}.getHarvestLevel(targetblock);

        if (harvestlevel > 3) {
            cruncher.getPersistentData().putInt("biteforce", 0);
            return;
        }

        float blockhardness = targetblock.getDestroySpeed(world, pos);

        if ((cruncher.getPersistentData().getInt("biteforce")) >= (((blockhardness + 0.5) * 4))){
            Block.dropResources(targetblock, world, BlockPos.containing(targetPos.getX()+0.5f, targetPos.getY()+0.25f, targetPos.getZ()+0.5f),null);
            world.destroyBlock(targetPos, false);
            cruncher.getPersistentData().putInt("biteforce", 0);
            world.sendBlockUpdated(pos, blockstate, blockstate, 3);
        }

        return;
    }
}