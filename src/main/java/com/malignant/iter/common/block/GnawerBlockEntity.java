package com.malignant.iter.common.block;

import com.malignant.iter.common.registry.ModBlockEntities;
import com.malignant.iter.world.gui.GnawerGuiMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class GnawerBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {

    private NonNullList<ItemStack> stacks = NonNullList.withSize(3, ItemStack.EMPTY);

    private final IItemHandler itemHandler = new ItemStackHandler() {
        @Override
        public int getSlots() {
            return GnawerBlockEntity.this.getContainerSize();
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return GnawerBlockEntity.this.getItem(slot);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (!GnawerBlockEntity.this.canPlaceItem(slot, stack)) {
                return stack;
            }

            ItemStack current = getStackInSlot(slot);
            int space = getSlotLimit(slot) - current.getCount();

            if (space <= 0 || !current.isEmpty() && !ItemStack.isSameItemSameComponents(current, stack)) {
                return stack;
            }

            int amount = Math.min(space, stack.getCount());
            if (!simulate) {
                if (current.isEmpty()) {
                    GnawerBlockEntity.this.setItem(slot, stack.copyWithCount(amount));
                } else {
                    current.grow(amount);
                }
                GnawerBlockEntity.this.setChanged();
            }

            if (amount == stack.getCount()) {
                return ItemStack.EMPTY;
            } else {
                return stack.copyWithCount(stack.getCount() - amount);
            }
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (!GnawerBlockEntity.this.canTakeItemThroughFace(slot, getStackInSlot(slot), null)) {
                return ItemStack.EMPTY;
            }

            ItemStack current = getStackInSlot(slot);
            if (current.isEmpty() || amount <= 0) {
                return ItemStack.EMPTY;
            }

            int extractAmount = Math.min(amount, current.getCount());
            if (!simulate) {
                ItemStack result = current.copyWithCount(extractAmount);
                current.shrink(extractAmount);
                if (current.isEmpty()) {
                    GnawerBlockEntity.this.setItem(slot, ItemStack.EMPTY);
                }
                GnawerBlockEntity.this.setChanged();
                return result;
            } else {
                return current.copyWithCount(extractAmount);
            }
        }

        @Override
        public int getSlotLimit(int slot) {
            return GnawerBlockEntity.this.getMaxStackSize();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return GnawerBlockEntity.this.canPlaceItem(slot, stack);
        }
    };

    public GnawerBlockEntity(BlockPos position, BlockState state) {
        super(ModBlockEntities.GNAWER.get(), position, state);
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        if (!this.tryLoadLootTable(compound)) {
            this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        }
        ContainerHelper.loadAllItems(compound, this.stacks, registries);
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.stacks, registries);
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    @Override
    public int getContainerSize() {
        return stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.stacks) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Component getDefaultName() {
        return Component.literal("gnawer");
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new GnawerGuiMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(this.worldPosition));
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Gnawer");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> stacks) {
        this.stacks = stacks;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return index != 2;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return IntStream.range(0, this.getContainerSize()).toArray();
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return index != 0 && index != 1;
    }

    @Nullable
    public IItemHandler getItemHandler(Direction facing) {
        return itemHandler;
    }
}