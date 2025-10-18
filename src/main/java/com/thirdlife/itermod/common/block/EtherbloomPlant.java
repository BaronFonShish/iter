package com.thirdlife.itermod.common.block;

import com.thirdlife.itermod.common.registry.ModBlocks;
import com.thirdlife.itermod.common.registry.ModItems;
import com.thirdlife.itermod.common.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import static org.apache.commons.lang3.RandomUtils.nextInt;

public class EtherbloomPlant extends CropBlock {

    public static final int MAX_AGE = 5;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;

    public EtherbloomPlant(Properties pProperties) {
        super(pProperties);
    }

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(7.0D, 0.0D, 7.0D, 9.0D, 2.0D, 9.0D),
            Block.box(7.0D, 0.0D, 7.0D, 9.0D, 2.0D, 9.0D),
            Block.box(6.5D, 0.0D, 6.5D, 9.5D, 4.0D, 9.5D),
            Block.box(6.5D, 0.0D, 6.5D, 9.5D, 4.0D, 9.5D),
            Block.box(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D),
            Block.box(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D)};

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_AGE[this.getAge(pState)];
    }


    @Override
    protected ItemLike getBaseSeedId(){
        return ModItems.ETHERBLOOM_SEEDS.get();
    }


    @Override
    public IntegerProperty getAgeProperty(){
        return AGE;
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return;
        {
            int i = this.getAge(pState);
            if (i == getMaxAge()){
                pLevel.setBlock(pPos, ModBlocks.ETHERBLOOM.get().defaultBlockState(), 3);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
            } else {
            if (i < this.getMaxAge()) {
                float f = getGrowthSpeed(this, pLevel, pPos);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int)(25.0F / f) + 1) == 0)) {
                    pLevel.setBlock(pPos, this.getStateForAge(i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
                }
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return true;
    }

    @Override
    public void growCrops(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.random.nextInt(5) >= 3) {
            int i = this.getAge(pState) + 1;
            if (i > this.getMaxAge()) {
                pLevel.setBlock(pPos, ModBlocks.ETHERBLOOM.get().defaultBlockState(), 16);

            } else {
                pLevel.setBlock(pPos, this.getStateForAge(i), 2);
            }
        }
    }

    @Override
    public int getMaxAge(){
        return MAX_AGE;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos belowPos = pPos.below();
        BlockState belowState = pLevel.getBlockState(belowPos);
        return this.mayPlaceOn(belowState, pLevel, belowPos);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(ModTags.Blocks.ETHERBLOOM_SOIL);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
}
