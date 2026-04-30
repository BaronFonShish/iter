package com.malignant.iter.common.event;

import com.malignant.iter.common.item.magic.defaults.SpellBook;
import com.malignant.iter.common.item.magic.defaults.SpellItem;
import com.malignant.iter.common.variables.IterPlayerDataUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class SpellBookUtils {

    public static ItemStack getSpell(Player player) {
        ItemStack spellbookStack = findSpellbook(player);
        if (spellbookStack.isEmpty()) {
            return getSpellFromOffhand(player);
        }

        int selectedSlot = IterPlayerDataUtils.getSpellSlot(player);

        IItemHandler handler = spellbookStack.getCapability(Capabilities.ItemHandler.ITEM);
        if (handler != null) {
            if (selectedSlot >= 0 && selectedSlot < handler.getSlots()) {
                ItemStack spellStack = handler.getStackInSlot(selectedSlot);
                if (isSpellItem(spellStack)) {
                    return spellStack;
                }
            }
        }

        return getSpellFromOffhand(player);
    }

    public static boolean hasSpellbook(Player player) {
        return !findSpellbook(player).isEmpty();
    }

    public static ItemStack findSpellbook(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() instanceof SpellBook) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    private static ItemStack getSpellFromOffhand(Player player) {
        ItemStack offhandStack = player.getOffhandItem();
        if (isSpellItem(offhandStack)) {
            return offhandStack;
        }
        return ItemStack.EMPTY;
    }

    public static boolean isSpellItem(ItemStack stack) {
        return !stack.isEmpty() && (stack.getItem() instanceof SpellItem);
    }

    @Nullable
    public static ItemStack getSpellItemFromSlot(Player player, int slotOverride) {
        ItemStack spellbookStack = findSpellbook(player);
        if (spellbookStack.isEmpty()) {
            return getSpellFromOffhand(player);
        }

        IItemHandler handler = spellbookStack.getCapability(Capabilities.ItemHandler.ITEM);
        if (handler != null) {
            if (slotOverride >= 0 && slotOverride < handler.getSlots()) {
                ItemStack spellStack = handler.getStackInSlot(slotOverride);
                if (isSpellItem(spellStack)) {
                    return spellStack;
                }
            }
        }
        return getSpellFromOffhand(player);
    }
}