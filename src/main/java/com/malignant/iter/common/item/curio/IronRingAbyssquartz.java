package com.malignant.iter.common.item.curio;

import com.malignant.iter.common.event.RingEffectManager;
import com.malignant.iter.common.registry.ModAttributes;
import com.malignant.iter.common.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.core.registries.BuiltInRegistries;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class IronRingAbyssquartz extends Item implements ICurioItem {
    public IronRingAbyssquartz() {
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
        RingEffectStorage.abyssquartz(slotContext);
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        return new ItemStack(ModItems.IRON_RING.get());
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable((("iter.desc." + (BuiltInRegistries.ITEM.getKey(itemstack.getItem()).toString()).replace("iter:", "")))));
    }
}

