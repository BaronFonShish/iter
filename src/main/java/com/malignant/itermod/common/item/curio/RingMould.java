package com.malignant.itermod.common.item.curio;

import com.malignant.itermod.common.registry.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class RingMould extends Item implements ICurioItem {
    public RingMould() {
        super(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON));
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        return new ItemStack(ModItems.IRON_RING.get());
    }

}
