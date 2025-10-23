package com.thirdlife.itermod.common.event;

import com.thirdlife.itermod.common.item.magic.defaults.SpellBook;
import com.thirdlife.itermod.common.variables.MageUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class SpellBookUtils {

    public static ItemStack getSpell(Player player) {
        ItemStack spellbookStack = findSpellbookInInventory(player);

        if (spellbookStack.isEmpty()) {
            return getSpellFromOffhand(player);
        }

        int selectedSlot = MageUtils.getSpellSlot(player);

        LazyOptional<IItemHandler> capability = spellbookStack.getCapability(ForgeCapabilities.ITEM_HANDLER);
        if (capability.isPresent()) {
            IItemHandler handler = capability.orElseThrow(() -> new IllegalStateException("Spellbook capability missing"));

            if (selectedSlot >= 0 && selectedSlot < handler.getSlots()) {
                ItemStack spellStack = handler.getStackInSlot(selectedSlot);

                if (isSpellItem(spellStack)) {
                    return spellStack;
                }
            }
        }

        return getSpellFromOffhand(player);
    }

    /**
     * Finds the first spellbook in the player's inventory
     * @param player The player to search
     * @return The spellbook ItemStack, or ItemStack.EMPTY if not found
     */
    private static ItemStack findSpellbookInInventory(Player player) {
        // Check main inventory (including hotbar)
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

    private static boolean isSpellItem(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        return stack.getTags().anyMatch(tag ->
                tag.location().equals(new ResourceLocation("iter", "spell_item"))
        );
    }

    @Nullable
    public static ItemStack getSpellItemFromSlot(Player player, int slotOverride) {
        ItemStack spellbookStack = findSpellbookInInventory(player);

        if (spellbookStack.isEmpty()) {
            return getSpellFromOffhand(player);
        }

        LazyOptional<IItemHandler> capability = spellbookStack.getCapability(ForgeCapabilities.ITEM_HANDLER);
        if (capability.isPresent()) {
            IItemHandler handler = capability.orElseThrow(() -> new IllegalStateException("Spellbook capability missing"));

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
