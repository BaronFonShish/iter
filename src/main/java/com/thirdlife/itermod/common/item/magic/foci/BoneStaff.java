package com.thirdlife.itermod.common.item.magic.foci;

import com.thirdlife.itermod.common.item.magic.defaults.SpellFocus;
import net.minecraft.world.item.Rarity;

public class BoneStaff extends SpellFocus {

    public BoneStaff() {
        super(new SpellFocusProperties()
                .durability(144)
                .rarity(Rarity.COMMON)
                .enchantability(12));
    }

    @Override
    public int getEnchantmentValue() {
        return 12;
    }

}
