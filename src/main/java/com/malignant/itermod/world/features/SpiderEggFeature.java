package com.malignant.itermod.world.features;

import com.malignant.itermod.common.IterModConfig;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class SpiderEggFeature extends SimpleBlockFeature {

    public SpiderEggFeature() {
        super(SimpleBlockConfiguration.CODEC);
    }

    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        Level world = context.level().getLevel();
        int x = context.origin().getX();
        int y = context.origin().getY();
        int z = context.origin().getZ();

        if (!IterModConfig.COMMON.spiderEggs.get()) return false;
        return super.place(context);
    }

}
