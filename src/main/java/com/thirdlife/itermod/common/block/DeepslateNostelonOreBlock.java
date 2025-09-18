package com.thirdlife.itermod.common.block;

import com.thirdlife.itermod.common.event.ExpDropEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;

public class DeepslateNostelonOreBlock extends Block {
    public DeepslateNostelonOreBlock() {
        super(Properties.of().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE).strength(4f, 6f).requiresCorrectToolForDrops());
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 15;
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
        if (player.getInventory().getSelected().getItem() instanceof PickaxeItem tieredItem)
            return tieredItem.getTier().getLevel() >= 2;
        return false;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState blockstate, Level world, BlockPos pos, Player entity, boolean willHarvest, FluidState fluid) {
        boolean retval = super.onDestroyedByPlayer(blockstate, world, pos, entity, willHarvest, fluid);
        if (canHarvestBlock(blockstate, world, pos, entity)) {
            ExpDropEvent.blockBrokenRand(world, pos.getX(), pos.getY(), pos.getZ(), 0, 4, entity);
        }
        return retval;
    }
}