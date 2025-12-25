package com.thirdlife.itermod.common.enchantment;

import com.thirdlife.itermod.common.item.magic.defaults.SpellFocus;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class RigourEnchantment extends Enchantment {
    public RigourEnchantment(EquipmentSlot... slots){
        super(Rarity.RARE, EnchantmentCategory.WEAPON, slots);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack itemstack) {
        return (itemstack.getItem() instanceof SpellFocus);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isTradeable() {
        return true;
    }

    @Override
    public boolean isTreasureOnly() {
        return false;
    }
}
