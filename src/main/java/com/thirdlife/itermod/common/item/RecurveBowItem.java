package com.thirdlife.itermod.common.item;

import net.minecraft.world.item.*;


public class RecurveBowItem extends AbstractIterBow {

    public RecurveBowItem() {
        super(new Properties()
                .rarity(Rarity.COMMON)
                .durability(514));
    }

    @Override
    public double getMultiplicativePower() {
        return 1;
    }

    @Override
    public double getAdditivePower() {
        return 0.25;
    }

    @Override
    public int getMaxDrawDuration() {
        return 20;
    }

    @Override
    public float getVelocityMultiplier() {
        return 3.25F;
    }
}
