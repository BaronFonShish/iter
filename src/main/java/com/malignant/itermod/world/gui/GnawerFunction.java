package com.malignant.itermod.world.gui;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.Map;
import java.util.function.Supplier;

public class GnawerFunction {


    public static ItemStack getItemFromSlot(Player player, int slot){
        if (player.containerMenu == null) return ItemStack.EMPTY;

        if (slot >= 0 && slot < player.containerMenu.slots.size()) {
            Slot menuSlot = player.containerMenu.getSlot(slot);
            if (menuSlot != null) {
                return menuSlot.getItem();
            }
        }
        return ItemStack.EMPTY;
    }

    public static void RemoveItemFromSlot(Player player, int slot, int amount){
        if (player.containerMenu == null) return;

        if (slot >= 0 && slot < player.containerMenu.slots.size()) {
            Slot menuSlot = player.containerMenu.getSlot(slot);
            if (menuSlot != null && menuSlot.hasItem()) {
                menuSlot.remove(amount);
                player.containerMenu.broadcastChanges();
            }
        }
    }

    public static void SetItemToSlot(Player player, ItemStack item, int slot, int amount){
        if (player.containerMenu instanceof Supplier _current && _current.get() instanceof Map slots) {
            item.setCount(amount);
            ((Slot) slots.get(slot)).set(item);
            player.containerMenu.broadcastChanges();
        }
    }

    public static void function(Player player){
        if (player.level().isClientSide) return;

        ItemStack book = getItemFromSlot(player, 1);
        ItemStack donor = getItemFromSlot(player, 0);
        ItemStack enchantbook = new ItemStack(Items.ENCHANTED_BOOK);

        if (book.isEmpty() || donor.isEmpty()) return;
        if (!donor.isEnchanted()) return;
        if (player.experienceLevel < 5) return;
        if (book.getItem() != Items.BOOK) return;

        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(donor);

        if (enchantments.isEmpty()) {
            return;
        }

        Map.Entry<Enchantment, Integer> firstEnchant = enchantments.entrySet().iterator().next();
        EnchantedBookItem.addEnchantment(enchantbook, new EnchantmentInstance(firstEnchant.getKey(), firstEnchant.getValue()));
        enchantments.remove(firstEnchant.getKey());
        EnchantmentHelper.setEnchantments(enchantments, donor);

        RemoveItemFromSlot(player, 1, 1);
        player.giveExperienceLevels(-5);

        donor.hurt((int) (donor.getMaxDamage() / 4), RandomSource.create(), null);

        SetItemToSlot(player, enchantbook, 2, 1);

        player.containerMenu.broadcastChanges();
    }
}
