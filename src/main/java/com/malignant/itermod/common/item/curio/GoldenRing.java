package com.malignant.itermod.common.item.curio;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class GoldenRing extends Item implements ICurioItem {
    public GoldenRing() {
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
    }
}
