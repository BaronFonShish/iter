package com.malignant.itermod.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class StingerItem extends AbstractIterBow {


    public StingerItem() {
        super(new Properties()
                .rarity(Rarity.COMMON)
                .durability(378));
    }

    @Override
    public double getMultiplicativePower() {
        return 0.75;
    }

    @Override
    public double getAdditivePower() {
        return 0;
    }

    @Override
    public int getMaxDrawDuration() {
        return 10;
    }

    @Override
    public float getVelocityMultiplier() {
        return 2F;
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingTicks) {
        if (entity instanceof Player player) {
            int useTime = player.getTicksUsingItem();
            if (useTime >= 12) {
                entity.releaseUsingItem();
            }
        }
    }
}
