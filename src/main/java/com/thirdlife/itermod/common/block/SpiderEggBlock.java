package com.thirdlife.itermod.common.block;

import com.thirdlife.itermod.common.event.ExpDropEvent;
import com.thirdlife.itermod.common.event.SpiderEggHatchEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class SpiderEggBlock extends Block {
    public SpiderEggBlock() {
        super(Properties.of()
                .sound(SoundType.WOOL)
                .strength(0.15f, 1f)
                .requiresCorrectToolForDrops()
                .randomTicks());
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
        return true;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return box(2, 0, 2, 14, 15, 14);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        return belowState.isFaceSturdy(level, belowPos, Direction.UP);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos,
                                Block block, BlockPos fromPos, boolean isMoving) {
        if (!canSurvive(state, level, pos)) {
            level.destroyBlock(pos, true);
            SpiderEggHatchEvent.force(level, pos.getX(), pos.getY(), pos.getZ());
        }
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);

        List<Player> nearbyPlayers = level.getEntitiesOfClass(Player.class,
                new AABB(pos).inflate(16.0),
                player -> !player.isCreative() && !player.isSpectator());

        if (!nearbyPlayers.isEmpty() && random.nextDouble() < 0.75) { // 10% chance per random tick
            level.destroyBlock(pos, true);
            SpiderEggHatchEvent.force(level, pos.getX(), pos.getY(), pos.getZ());
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState blockstate, Level world, BlockPos pos, Player entity, boolean willHarvest, FluidState fluid) {
        boolean retval = super.onDestroyedByPlayer(blockstate, world, pos, entity, willHarvest, fluid);

        if (canHarvestBlock(blockstate, world, pos, entity)) {
            SpiderEggHatchEvent.check(world, pos.getX(), pos.getY(), pos.getZ(), entity);
            ExpDropEvent.blockBrokenRand(world, pos.getX(), pos.getY(), pos.getZ(), 0, 2, entity);
        }
        return retval;
    }

}