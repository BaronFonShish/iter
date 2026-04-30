package com.malignant.iter.common.variables;

import com.malignant.iter.common.registry.ModItems;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpellbookCapability implements INBTSerializable<CompoundTag> {

    private final ItemStackHandler inventory;

    public SpellbookCapability() {
        this.inventory = createItemHandler();
    }
    @Nullable
    public IItemHandler getCapability(@Nullable Direction side) {
        return inventory;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider lookupProvider) {
        return inventory.serializeNBT(lookupProvider);
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
        inventory.deserializeNBT(lookupProvider, nbt);
    }

    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(57) {
            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.getItem() != ModItems.SPELL_BOOK.get();
            }
        };
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }
}