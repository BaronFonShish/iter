package com.malignant.iter.common.block;

import com.malignant.iter.common.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CruncherBlockEntity extends BlockEntity {
    public CruncherBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CRUNCHER.get(), pPos, pBlockState);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
