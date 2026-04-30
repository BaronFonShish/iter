package com.malignant.iter.common.registry;

import com.malignant.iter.IterMod;
import com.malignant.iter.world.features.AbyssquartzCrystalFeature;
import com.malignant.iter.world.features.AncientVaseFeature;
import com.malignant.iter.world.features.RotrootPatchFeature;
import com.malignant.iter.world.features.SpiderEggFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
public class ModFeatures {
    public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(Registries.FEATURE, IterMod.MOD_ID);
    public static final DeferredHolder<Feature<?>, Feature<?>> ABYSSQUARTZ_CRYSTAL_FEATURE = REGISTRY.register("abyssquartz_crystal_feature", AbyssquartzCrystalFeature::new);
    public static final DeferredHolder<Feature<?>, Feature<?>> SPIDER_EGG_FEATURE = REGISTRY.register("spider_egg_feature", SpiderEggFeature::new);
    public static final DeferredHolder<Feature<?>, Feature<?>> ROTROOT_PATCH_FEATURE = REGISTRY.register("rotroot_patch_feature", RotrootPatchFeature::new);
    public static final DeferredHolder<Feature<?>, Feature<?>> ANCIENT_VASE_FEATURE = REGISTRY.register("ancient_vase_feature", AncientVaseFeature::new);
}
