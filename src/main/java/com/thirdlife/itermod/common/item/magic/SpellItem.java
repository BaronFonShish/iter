package com.thirdlife.itermod.common.item.magic;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class SpellItem extends Item{

    private final int castTime;
    private final int cooldown;
    private final int etherCost;

    public SpellItem(Properties properties, int castTime, int etherCost, int cooldown) {
        super(properties);
        this.castTime = castTime;
        this.etherCost = etherCost;
        this.cooldown = cooldown;

    }

    public int getCastTime(ItemStack wand) {
        return castTime;
    }

    public int getCooldown(ItemStack wand) {
        return cooldown;
    }

    public int getManaCost(ItemStack wand) {
        return etherCost;
    }

    public abstract void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack);

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }
}