package com.thirdlife.itermod.common.variables;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class IterPlayerData {
    private float etherBurnout = 0.0f;
    private float spellLuck = 0.0f;
    private int selectedSpellSlot = 1;
    private ItemStack selectedSpellBook = ItemStack.EMPTY;
    private boolean spellweaverSwitch = false;

    public float getEtherBurnout() { return etherBurnout; }
    public void setEtherBurnout(float burnout) { this.etherBurnout = Math.max(0, burnout); }
    public float getSpellLuck() { return spellLuck; }
    public void resetSpellLuck() { this.spellLuck = 0; }
    public void addEtherBurnout(float amount){this.etherBurnout = Math.max(0, this.etherBurnout + amount);}
    public void subtractEtherBurnout(float amount){this.etherBurnout = Math.min(this.etherBurnout, this.etherBurnout - amount);}
    public boolean getSpellweaverSwitch() { return spellweaverSwitch; }
    public void setSpellweaverSwitch(boolean state) { this.spellweaverSwitch = state; }



    public int getSelectedSpellSlot() { return selectedSpellSlot; }
    public void setSelectedSpellSlot(int slot) { this.selectedSpellSlot = slot; }

    public ItemStack getSelectedSpellBook() { return selectedSpellBook; }
    public void setSelectedSpellBook(ItemStack book) { this.selectedSpellBook = book; }


    public void copyFrom(IterPlayerData source) {
        this.etherBurnout = source.etherBurnout;
        this.spellLuck = source.spellLuck;
        this.selectedSpellSlot = source.selectedSpellSlot;
        this.selectedSpellBook = source.selectedSpellBook.copy();
        this.spellweaverSwitch = source.spellweaverSwitch;
    }

    public void saveNBT(CompoundTag nbt) {
        nbt.putFloat("EtherBurnout", etherBurnout);
        nbt.putFloat("SpellLuck", spellLuck);
        nbt.putInt("SelectedSpellSlot", selectedSpellSlot);
        nbt.putBoolean("SpellweaverSwitch", spellweaverSwitch);

        if (!selectedSpellBook.isEmpty()) {
            CompoundTag bookTag = new CompoundTag();
            selectedSpellBook.save(bookTag);
            nbt.put("SelectedSpellBook", bookTag);
        }
    }

    public void loadNBT(CompoundTag nbt) {
        etherBurnout = nbt.getFloat("EtherBurnout");
        spellLuck = nbt.getFloat("SpellLuck");
        selectedSpellSlot = nbt.getInt("SelectedSpellSlot");
        spellweaverSwitch = nbt.getBoolean("SpellweaverSwitch");

        if (nbt.contains("SelectedSpellBook")) {
            selectedSpellBook = ItemStack.of(nbt.getCompound("SelectedSpellBook"));
        } else {
            selectedSpellBook = ItemStack.EMPTY;
        }
    }
}