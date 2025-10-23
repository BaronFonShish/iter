package com.thirdlife.itermod.common.enchantment;

import com.thirdlife.itermod.common.item.ScytheItem;
import com.thirdlife.itermod.common.registry.ModItems;
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
