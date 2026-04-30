package com.malignant.iter.common.block;

import com.malignant.iter.world.gui.GnawerGuiMenu;
import com.mojang.serialization.MapCodec;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GnawerBlock extends BaseEntityBlock {

    public GnawerBlock() {
        super(BlockBehaviour.Properties.of()
                .instrument(NoteBlockInstrument.BASEDRUM)
                .sound(SoundType.STONE)
                .strength(3f));
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 15;
    }

    public void appendHoverText(ItemStack itemstack, BlockGetter level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, (Item.TooltipContext) level, list, flag);
        list.add(Component.translatable("iterpg.desc.gnawer"));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockstate, Level world, BlockPos pos,
                                               Player entity, BlockHitResult hit) {
        if (!world.isClientSide && entity instanceof ServerPlayer player) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GnawerBlockEntity gnawerEntity) {
                player.openMenu(new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return Component.literal("Gnawer");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
                        buffer.writeBlockPos(pos);
                        return new GnawerGuiMenu(id, inventory, buffer);
                    }
                }, pos);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GnawerBlockEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GnawerBlockEntity gnawerEntity) {
                Containers.dropContents(world, pos, gnawerEntity);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
        BlockEntity tileentity = world.getBlockEntity(pos);
        if (tileentity instanceof GnawerBlockEntity gnawerEntity) {
            return AbstractContainerMenu.getRedstoneSignalFromContainer(gnawerEntity);
        }
        return 0;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                  BlockEntityType<T> type) {
        return null; // No ticker needed for Gnawer
    }
}