package com.malignant.iter.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class ComponentDepositBlock extends DropExperienceBlock {
    public ComponentDepositBlock() {
        super(UniformInt.of(2, 4), Properties.of().instrument(NoteBlockInstrument.BELL).sound(SoundType.METAL).strength(7.5f, 8f).requiresCorrectToolForDrops());
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 15;
    }

//    @Override
//    public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
//        if (player.getInventory().getSelected().getItem() instanceof PickaxeItem tieredItem)
//            return tieredItem.getTier().getLevel() >= 3;
//        return false;
//    }
}