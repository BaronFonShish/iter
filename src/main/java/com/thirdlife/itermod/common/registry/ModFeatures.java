package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.iterMod;
import com.thirdlife.itermod.world.features.AbyssquartzCrystalFeature;
import com.thirdlife.itermod.world.features.SpiderEggFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber
public class ModFeatures {
    public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, iterMod.MOD_ID);
    public static final RegistryObject<Feature<?>> ABYSSQUARTZ_CRYSTAL_FEATURE = REGISTRY.register("abyssquartz_crystal_feature", AbyssquartzCrystalFeature::new);
    public static final RegistryObject<Feature<?>> SPIDER_EGG_FEATURE = REGISTRY.register("spider_egg_feature", SpiderEggFeature::new);
}
