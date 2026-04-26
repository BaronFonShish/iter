package com.malignant.itermod.common.item.magic.foci;
import com.malignant.itermod.common.item.magic.defaults.SpellFocus;
import net.minecraft.world.item.Rarity;


public class HedgemageStaff extends SpellFocus {

    public HedgemageStaff() {
        super(new SpellFocusProperties()
                .durability(175)
                .rarity(Rarity.COMMON)
                .enchantability(12),
                1,
                3f,
                -0.2f,
                0,
                0,
                0,
                0
        );
    }
}
