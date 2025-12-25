package com.thirdlife.itermod.common.item.magic.foci;
import com.thirdlife.itermod.common.item.magic.defaults.SpellFocus;
import net.minecraft.world.item.Rarity;


public class AncientStaff extends SpellFocus {

    public AncientStaff() {
        super(new SpellFocusProperties()
                .durability(400)
                .rarity(Rarity.UNCOMMON)
                .enchantability(14),
                2,
                3,
                -0.2f,
                -0.05f
        );
    }
}
