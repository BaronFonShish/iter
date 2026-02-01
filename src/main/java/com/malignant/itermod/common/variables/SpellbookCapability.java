package com.malignant.itermod.common.variables;

import com.malignant.itermod.common.registry.ModItems;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SpellbookCapability implements ICapabilitySerializable<CompoundTag> {
    private final LazyOptional<ItemStackHandler> inventory = LazyOptional.of(this::createItemHandler);

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        return capability == ForgeCapabilities.ITEM_HANDLER ? this.inventory.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return getItemHandler().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getItemHandler().deserializeNBT(nbt);
    }

    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(57) {
            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() != ModItems.SPELL_BOOK.get();
            }
        };
    }

    private ItemStackHandler getItemHandler() {
        return inventory.orElseThrow(RuntimeException::new);
    }
}