package com.malignant.iter.common.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

public class RotrootItem extends Item {

    public RotrootItem(Properties pProperties) {
        super(pProperties.food(new FoodProperties.Builder()
                .nutrition(2)
                .saturationModifier(0.3F)
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
    public int getUseDuration(ItemStack pStack, LivingEntity entity) {
        return 32;
    }
}