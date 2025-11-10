package com.thirdlife.itermod.common.item.curio;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class IronRing extends Item implements ICurioItem {
    public IronRing() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
    }
}
