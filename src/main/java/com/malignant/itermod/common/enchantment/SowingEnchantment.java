package com.malignant.itermod.common.enchantment;

import com.malignant.itermod.common.item.ScytheItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SowingEnchantment extends Enchantment {
    public SowingEnchantment(EquipmentSlot... slots){
        super(Rarity.COMMON, EnchantmentCategory.WEAPON, slots);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack itemstack) {
        return (itemstack.getItem() instanceof ScytheItem);
    }
}
