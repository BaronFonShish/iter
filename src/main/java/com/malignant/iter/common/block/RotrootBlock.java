package com.malignant.iter.common.block;

import com.malignant.iter.common.registry.ModItems;
import com.malignant.iter.common.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RotrootBlock extends CropBlock {

    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(7.0D, 0.0D, 7.0D, 9.0D, 2.0D, 9.0D),  // Age 0
            Block.box(6.5D, 0.0D, 6.5D, 9.5D, 4.0D, 9.5D),  // Age 1
            Block.box(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D), // Age 2
            Block.box(5.0D, 0.0D, 5.0D, 11.0D, 8.0D, 11.0D)  // Age 3 (mature)
    };

    public RotrootBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_AGE[this.getAge(pState)];
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.ROTROOT_SEEDS.get();
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return;

        int i = this.getAge(pState);
        if (i < this.getMaxAge()) {
            float f = getGrowthSpeed(pState, pLevel, pPos);
            if (pRandom.nextInt((int) (25.0F / f) + 1) == 0) {
                pLevel.setBlock(pPos, this.getStateForAge(i + 1), 2);
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        this.growCrops(pLevel, pPos, pState);
    }

    public void growCrops(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.random.nextInt(5) >= 3) {
            int i = this.getAge(pState) + 1;
            if (i > this.getMaxAge()) {
                i = this.getMaxAge();
            }
            pLevel.setBlock(pPos, this.getStateForAge(i), 2);
        }
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos belowPos = pPos.below();
        BlockState belowState = pLevel.getBlockState(belowPos);
        return this.mayPlaceOn(belowState, pLevel, belowPos);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(ModTags.Blocks.ROTROOT_GROWABLE) || pState.is(Blocks.FARMLAND);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
}