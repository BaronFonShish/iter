package com.malignant.itermod.common.block;

import com.malignant.itermod.common.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithFullMetadata();
    }
}
