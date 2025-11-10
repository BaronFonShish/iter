package com.thirdlife.itermod.common.item.curio;

import com.thirdlife.itermod.common.event.RingEffectManager;
import com.thirdlife.itermod.common.registry.ModItems;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class GoldenRingDiamond extends Item implements ICurioItem {
    public GoldenRingDiamond() {
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        RingEffectManager.TieredRingStuff("iter_ring_defense", "Ring Defense", Attributes.ARMOR,
                ModItems.IRON_RING_DIAMOND.get(), ModItems.GOLDEN_RING_DIAMOND.get(), ModItems.NETHERITE_RING_DIAMOND.get(),
                2, 3, 4, slotContext.entity(), 1);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        RingEffectManager.TieredRingStuff("iter_ring_defense", "Ring Defense", Attributes.ARMOR,
                ModItems.IRON_RING_DIAMOND.get(), ModItems.GOLDEN_RING_DIAMOND.get(), ModItems.NETHERITE_RING_DIAMOND.get(),
                2, 3, 4, slotContext.entity(), 1);
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        return new ItemStack(ModItems.GOLDEN_RING.get());
    }
}

