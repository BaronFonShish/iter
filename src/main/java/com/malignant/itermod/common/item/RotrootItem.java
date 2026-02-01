package com.malignant.itermod.common.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

public class RotrootItem extends Item {

    public RotrootItem(Properties pProperties) {
        super(pProperties.food(new FoodProperties.Builder()
                .nutrition(2)
                .saturationMod(0.3F)
                .build()));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.EAT;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_EAT;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 32;
    }
}