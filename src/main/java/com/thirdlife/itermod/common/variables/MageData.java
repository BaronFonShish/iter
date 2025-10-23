package com.thirdlife.itermod.common.variables;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class MageData {
    private float etherBurnout = 0.0f;
    private int selectedSpellSlot = 1;
    private ItemStack selectedSpellBook = ItemStack.EMPTY;

    public float getEtherBurnout() { return etherBurnout; }
    public void setEtherBurnout(float burnout) { this.etherBurnout = Math.max(0, burnout); }
    public void addEtherBurnout(float amount){this.etherBurnout = Math.max(0, this.etherBurnout + amount);}
    public void subtractEtherBurnout(float amount){this.etherBurnout = Math.min(this.etherBurnout, this.etherBurnout - amount);}


    public int getSelectedSpellSlot() { return selectedSpellSlot; }
    public void setSelectedSpellSlot(int slot) { this.selectedSpellSlot = slot; }

    public ItemStack getSelectedSpellBook() { return selectedSpellBook; }
    public void setSelectedSpellBook(ItemStack book) { this.selectedSpellBook = book; }


    public void copyFrom(MageData source) {
        this.etherBurnout = source.etherBurnout;
        this.selectedSpellSlot = source.selectedSpellSlot;
        this.selectedSpellBook = source.selectedSpellBook.copy();
    }

    public void saveNBT(CompoundTag nbt) {
        nbt.putFloat("EtherBurnout", etherBurnout);
        nbt.putInt("SelectedSpellSlot", selectedSpellSlot);

        if (!selectedSpellBook.isEmpty()) {
            CompoundTag bookTag = new CompoundTag();
            selectedSpellBook.save(bookTag);
            nbt.put("SelectedSpellBook", bookTag);
        }
    }

    public void loadNBT(CompoundTag nbt) {
        etherBurnout = nbt.getFloat("EtherBurnout");
        selectedSpellSlot = nbt.getInt("SelectedSpellSlot");

        if (nbt.contains("SelectedSpellBook")) {
            selectedSpellBook = ItemStack.of(nbt.getCompound("SelectedSpellBook"));
        } else {
            selectedSpellBook = ItemStack.EMPTY;
        }
    }
}