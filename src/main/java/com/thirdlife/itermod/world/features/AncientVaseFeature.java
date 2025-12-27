package com.thirdlife.itermod.world.features;

import com.thirdlife.itermod.common.IterModConfig;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.SimpleRandomSelectorFeature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;

public class AncientVaseFeature extends SimpleRandomSelectorFeature {

    public AncientVaseFeature() {
        super(SimpleRandomFeatureConfiguration.CODEC);
    }

    public boolean place(FeaturePlaceContext<SimpleRandomFeatureConfiguration> context) {
        Level world = context.level().getLevel();
        int x = context.origin().getX();
        int y = context.origin().getY();
        int z = context.origin().getZ();

        if (!IterModConfig.COMMON.ancientVases.get()) return false;
        return super.place(context);
    }
}
