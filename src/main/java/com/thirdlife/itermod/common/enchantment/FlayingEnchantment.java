package com.thirdlife.itermod.common.enchantment;

import com.thirdlife.itermod.common.item.DaggerItem;
import com.thirdlife.itermod.common.item.ScytheItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FlayingEnchantment extends Enchantment {
    public FlayingEnchantment(EquipmentSlot... slots){
        super(Rarity.UNCOMMON, EnchantmentCategory.WEAPON, slots);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack itemstack) {
        return (itemstack.getItem() instanceof DaggerItem);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    public static int getMaxStacks(int level) {
        return switch (level) {
            case 1 -> 5;
            case 2 -> 8;
            case 3 -> 10;
            default -> 0;
        };
    }

    public static float getDamagePerStack(int level) {
        return level * 2.5f;
    }
}
