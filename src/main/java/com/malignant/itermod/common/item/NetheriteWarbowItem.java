package com.malignant.itermod.common.item;

import net.minecraft.world.item.Rarity;


public class NetheriteWarbowItem extends AbstractIterBow {

    public NetheriteWarbowItem() {
        super(new Properties()
                .rarity(Rarity.COMMON)
                .durability(1028)
                .fireResistant());
    }

    public boolean isFireResistant() {
        return true;
    }

    @Override
    public double getMultiplicativePower() {
        return 1.075;
    }

    @Override
    public double getAdditivePower() {
        return 0.5;
    }

    @Override
    public int getMaxDrawDuration() {
        return 20;
    }

    @Override
    public float getVelocityMultiplier() {
        return 4F;
    }
}
