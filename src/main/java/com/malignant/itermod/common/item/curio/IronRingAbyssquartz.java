package com.malignant.itermod.common.item.curio;

import com.malignant.itermod.common.event.RingEffectManager;
import com.malignant.itermod.common.registry.ModAttributes;
import com.malignant.itermod.common.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
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
        RingEffectManager.TieredRingStuff("iter_ring_burnout_threshold", "Ring Ether Threshold", ModAttributes.ETHER_BURNOUT_THRESHOLD.get(),
                ModItems.IRON_RING_ABYSSQUARTZ.get(), ModItems.GOLDEN_RING_ABYSSQUARTZ.get(), ModItems.NETHERITE_RING_ABYSSQUARTZ.get(),
                20f, 35f, 50f, slotContext.entity(), 1);
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
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable((("iter.desc." + (ForgeRegistries.ITEMS.getKey(itemstack.getItem()).toString()).replace("iter:", "")))));
    }
}

