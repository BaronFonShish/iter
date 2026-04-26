package com.malignant.itermod.common.item.magic.foci;
import com.malignant.itermod.common.item.magic.defaults.SpellFocus;
import net.minecraft.world.item.Rarity;


public class BoneStaff extends SpellFocus {

    public BoneStaff() {
        super(new SpellFocusProperties()
                .durability(162)
                .rarity(Rarity.COMMON)
                .enchantability(12),
                1,
                3,
                0,
                -0.2f,
                0,
                0.05f,
                0
        );
    }
}
