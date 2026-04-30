package com.malignant.iter.world.gui;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.Map;
import java.util.function.Consumer;
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

        ItemEnchantments donorEnchantments = donor.getEnchantments();

        if (donorEnchantments.isEmpty()) {
            return;
        }

        Holder<Enchantment> firstEnchantment = donorEnchantments.keySet().iterator().next();
        int enchantLevel = donorEnchantments.getLevel(firstEnchantment);

        EnchantedBookItem.createForEnchantment(new EnchantmentInstance(firstEnchantment, enchantLevel));

        ItemEnchantments.Mutable remainingEnchantments = new ItemEnchantments.Mutable(donorEnchantments);
        remainingEnchantments.removeIf(holder -> holder.equals(firstEnchantment));


        donor.set(DataComponents.ENCHANTMENTS, remainingEnchantments.toImmutable());

        RemoveItemFromSlot(player, 1, 1);
        player.giveExperienceLevels(-5);

        ServerPlayer serverPlayer = player instanceof ServerPlayer sp ? sp : null;

        Consumer<Item> onBroken = (brokenItem) -> {};

        donor.hurtAndBreak((int) (donor.getMaxDamage() / 4), (ServerLevel) player.level(), player, onBroken);

        SetItemToSlot(player, enchantbook, 2, 1);

        player.containerMenu.broadcastChanges();
    }
}