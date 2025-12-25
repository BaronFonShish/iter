package com.thirdlife.itermod.common.item.magic.foci;
import com.thirdlife.itermod.common.item.magic.defaults.SpellFocus;
import net.minecraft.world.item.Rarity;


public class DiamondStaff extends SpellFocus {

    public DiamondStaff() {
        super(new SpellFocusProperties()
                .durability(1750)
                .rarity(Rarity.COMMON)
                .enchantability(16),
                2,
                4.5f,
                0.05f,
                0.025f
        );
    }
}
