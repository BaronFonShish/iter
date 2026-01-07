package com.thirdlife.itermod.common.item.magic.foci;
import com.thirdlife.itermod.common.item.magic.defaults.SpellFocus;
import net.minecraft.world.item.Rarity;


public class MagmanumStaff extends SpellFocus {

    public MagmanumStaff() {
        super(new SpellFocusProperties()
                .durability(2020)
                .rarity(Rarity.COMMON)
                .enchantability(12),
                3,
                5f,
                0.05f,
                0.03f
        );
    }
}
