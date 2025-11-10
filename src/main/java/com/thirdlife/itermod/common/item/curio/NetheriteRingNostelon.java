package com.thirdlife.itermod.common.item.curio;

import com.thirdlife.itermod.common.event.RingEffectManager;
import com.thirdlife.itermod.common.registry.ModAttributes;
import com.thirdlife.itermod.common.registry.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class NetheriteRingNostelon extends Item implements ICurioItem {
    public NetheriteRingNostelon() {
        super(new Properties().stacksTo(1).fireResistant().rarity(Rarity.COMMON));
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        RingEffectManager.TieredRingStuff("iter_ring_burnout_dissipation", "Ring Ether Dissipation", ModAttributes.ETHER_BURNOUT_DISSIPATION.get(),
                ModItems.IRON_RING_NOSTELON.get(), ModItems.GOLDEN_RING_NOSTELON.get(), ModItems.NETHERITE_RING_NOSTELON.get(),
                0.025f, 0.0375f, 0.05f, slotContext.entity(), 1);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        RingEffectManager.TieredRingStuff("iter_ring_burnout_dissipation", "Ring Ether Dissipation", ModAttributes.ETHER_BURNOUT_DISSIPATION.get(),
                ModItems.IRON_RING_NOSTELON.get(), ModItems.GOLDEN_RING_NOSTELON.get(), ModItems.NETHERITE_RING_NOSTELON.get(),
                0.025f, 0.0375f, 0.05f, slotContext.entity(), 1);
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        return new ItemStack(ModItems.NETHERITE_RING.get());
    }
}

