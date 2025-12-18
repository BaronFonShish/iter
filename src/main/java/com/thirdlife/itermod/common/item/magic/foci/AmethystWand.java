package com.thirdlife.itermod.common.item.magic.foci;

import com.thirdlife.itermod.common.item.magic.defaults.SpellFocus;
import net.minecraft.world.item.Rarity;

public class AmethystWand extends SpellFocus {

    public AmethystWand() {
        super(new SpellFocusProperties()
                .durability(192)
                .rarity(Rarity.COMMON)
                .enchantability(12),
                1,
                4.5f,
                0.05f,
                0.025f
        );
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }
}
