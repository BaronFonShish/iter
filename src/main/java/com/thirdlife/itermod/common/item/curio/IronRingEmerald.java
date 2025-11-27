package com.thirdlife.itermod.common.item.curio;

import com.thirdlife.itermod.common.event.RingEffectManager;
import com.thirdlife.itermod.common.registry.ModAttributes;
import com.thirdlife.itermod.common.registry.ModItems;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class IronRingEmerald extends Item implements ICurioItem {
    public IronRingEmerald() {
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        ringSend(slotContext);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        ringSend(slotContext);
    }

    public void ringSend(SlotContext slotContext) {
        RingEffectManager.TieredRingStuff("iter_ring_attack_speed", "Ring Attack Speed", Attributes.ATTACK_SPEED,
                ModItems.IRON_RING_EMERALD.get(), ModItems.GOLDEN_RING_EMERALD.get(), ModItems.NETHERITE_RING_EMERALD.get(),
                0.05f, 0.075f, 0.1f, slotContext.entity(), 2);
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

