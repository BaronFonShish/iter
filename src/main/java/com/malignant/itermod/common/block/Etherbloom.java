package com.malignant.itermod.common.block;

import com.malignant.itermod.common.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class Etherbloom extends FlowerBlock {
    public Etherbloom() {
        super(() -> MobEffects.GLOWING, 100, BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_MAGENTA)
                .sound(SoundType.GRASS)
                .instabreak()
                .lightLevel(s -> 3)
                .noCollission()
                .offsetType(BlockBehaviour.OffsetType.NONE)
                .pushReaction(PushReaction.DESTROY));
    }


    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos belowPos = pPos.below();
        BlockState belowState = pLevel.getBlockState(belowPos);
        return this.mayPlaceOn(belowState, pLevel, belowPos);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return (pState.is(ModTags.Blocks.ETHERBLOOM_SOIL) || pState.is(BlockTags.DIRT));
    }
}
