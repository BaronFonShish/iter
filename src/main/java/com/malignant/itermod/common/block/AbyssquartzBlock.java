package com.malignant.itermod.common.block;

import com.malignant.itermod.common.IterModConfig;
import com.malignant.itermod.common.event.AbyssquartzGrow;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;

public class AbyssquartzBlock extends Block {
    public AbyssquartzBlock() {
        super(Properties.of().instrument(NoteBlockInstrument.CHIME)
                .sound(SoundType.GLASS)
                .strength(6f, 8f)
                .requiresCorrectToolForDrops()
                .randomTicks());
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
        return retval;
    }

    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (IterModConfig.COMMON.abyssquartzGrowth.get()) AbyssquartzGrow.execute(world, pos);
    }
}