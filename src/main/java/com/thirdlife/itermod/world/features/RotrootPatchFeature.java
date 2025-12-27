package com.thirdlife.itermod.world.features;

import com.thirdlife.itermod.common.IterModConfig;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class RotrootPatchFeature extends SimpleBlockFeature {

    public RotrootPatchFeature() {
        super(SimpleBlockConfiguration.CODEC);
    }

    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        Level world = context.level().getLevel();
        int x = context.origin().getX();
        int y = context.origin().getY();
        int z = context.origin().getZ();

        if (!IterModConfig.COMMON.rotroots.get()) return false;
        return super.place(context);
    }

}
