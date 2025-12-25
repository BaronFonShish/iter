package com.thirdlife.itermod.common.item.magic.foci;

import com.thirdlife.itermod.common.item.magic.defaults.SpellFocus;
import net.minecraft.world.item.Rarity;

public class AmethystWand extends SpellFocus {

    public AmethystWand() {
        super(new SpellFocusProperties()
                .durability(250)
                .rarity(Rarity.COMMON)
                .enchantability(15),
                1,
                2.5f,
                0.01f,
                0
        );
    }
}
