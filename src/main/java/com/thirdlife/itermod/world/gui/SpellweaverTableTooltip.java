package com.thirdlife.itermod.world.gui;

import com.thirdlife.itermod.common.item.magic.defaults.SpellItem;
import com.thirdlife.itermod.common.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class SpellweaverTableTooltip {

    public static List<net.minecraft.network.chat.Component> FinalTooltip(Player player){
        List<net.minecraft.network.chat.Component> list = new ArrayList<>();

        if (SpellweaverTableFunction.getItemFromSlot(player, 0).isEmpty()){
            list.addAll(Introduction(player));
            return list;
        }

        if (SpellweaverTableFunction.getswitch(player)){
            list.addAll(Copy(player));
            return list;
        } else { list.addAll(Upgrade(player));
            return list;}
    }

    public static List<net.minecraft.network.chat.Component> Introduction(Player player){
        List<net.minecraft.network.chat.Component> list = new ArrayList<>();
        list.add(net.minecraft.network.chat.Component.translatable("gui.iter.spellweaver_table.tooltip.intro_1"));
        list.add(net.minecraft.network.chat.Component.translatable("gui.iter.spellweaver_table.tooltip.intro_2"));
        return list;
    }

    public static List<net.minecraft.network.chat.Component> Copy(Player player){
        List<net.minecraft.network.chat.Component> list = new ArrayList<>();
        list.add(net.minecraft.network.chat.Component.translatable("gui.iter.spellweaver_table.tooltip.copy"));
        list.add(net.minecraft.network.chat.Component.literal(""));
        if (SpellweaverTableFunction.getItemFromSlot(player, 0).getItem() instanceof SpellItem spell){
            int gistNeeded = Math.max(1, spell.getTier() * 3);
            int getPaper = SpellweaverTableFunction.skimSlots(player, Items.PAPER.getDefaultInstance());
            int getGist = SpellweaverTableFunction.skimSlots(player, ModItems.GIST.get().getDefaultInstance());
            int getInk = SpellweaverTableFunction.skimSlots(player, ModItems.INK_BOTTLE.get().getDefaultInstance());
            Component name = Items.PAPER.getDefaultInstance().getDisplayName();
            list.add(Component.translatable("gui.iter.spellweaver_table.tooltip.itemXofX", name, getPaper, 1));
            name = ModItems.INK_BOTTLE.get().getDefaultInstance().getDisplayName();
            list.add(Component.translatable("gui.iter.spellweaver_table.tooltip.itemXofX", name, getInk, 1));
            name = ModItems.GIST.get().getDefaultInstance().getDisplayName();
            list.add(Component.translatable("gui.iter.spellweaver_table.tooltip.itemXofX", name, getGist, gistNeeded));
        }
        return list;
    }

    public static List<net.minecraft.network.chat.Component> Upgrade(Player player){
        List<net.minecraft.network.chat.Component> list = new ArrayList<>();
        list.add(net.minecraft.network.chat.Component.translatable("gui.iter.spellweaver_table.tooltip.upgrade"));
        list.add(net.minecraft.network.chat.Component.literal(""));
        ItemStack SpellStack = SpellweaverTableFunction.getItemFromSlot(player, 0);
        if (SpellStack.getItem() instanceof SpellItem spell){
            int spongebob = spell.getTier() + spell.getQuality(SpellStack);
            int gistNeeded = Math.max(1, spell.getTier() + spell.getQuality(SpellStack)*2);
            int getGist = SpellweaverTableFunction.skimSlots(player, ModItems.GIST.get().getDefaultInstance());
            Component name = ModItems.GIST.get().getDefaultInstance().getDisplayName();
            list.add(Component.translatable("gui.iter.spellweaver_table.tooltip.itemXofX", name, getGist, gistNeeded));
            Item itemNeeded = SpellweaverTableFunction.getUpgradeCatalyst(spongebob);
            int getitemNeeded = SpellweaverTableFunction.skimSlots(player, itemNeeded.getDefaultInstance());
            list.add(Component.translatable("gui.iter.spellweaver_table.tooltip.itemXofX", itemNeeded.getDefaultInstance().getDisplayName(), getitemNeeded, 1));
        }
        return list;
    }
}
