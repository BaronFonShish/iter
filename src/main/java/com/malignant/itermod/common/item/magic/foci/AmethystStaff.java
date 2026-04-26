package com.malignant.itermod.common.item.magic.foci;

import com.malignant.itermod.common.item.magic.defaults.SpellFocus;
import net.minecraft.world.item.Rarity;

public class AmethystStaff extends SpellFocus {

    public AmethystStaff() {
        super(new SpellFocusProperties()
                .durability(250)
                .rarity(Rarity.COMMON)
                .enchantability(15),
                2,
                3.5f,
                0.05f,
                0.025f,
                0,
                0,
                0
        );
    }
}
