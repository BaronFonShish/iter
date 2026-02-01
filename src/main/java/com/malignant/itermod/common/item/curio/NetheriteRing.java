package com.malignant.itermod.common.item.curio;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class NetheriteRing extends Item implements ICurioItem {
    public NetheriteRing() {
        super(new Properties().stacksTo(1).fireResistant().rarity(Rarity.COMMON));
    }
}
