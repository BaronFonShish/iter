package com.malignant.iter.common.item.magic.foci;
import com.malignant.iter.common.item.magic.defaults.SpellFocus;
import net.minecraft.world.item.Rarity;


public class NostelonStaff extends SpellFocus {

    public NostelonStaff() {
        super(new SpellFocusProperties()
                .durability(750)
                .rarity(Rarity.COMMON)
                .enchantability(16),
                2,
                4,
                0.05f,
                0.05f,
                0,
                0,
                0
        );
    }
}
