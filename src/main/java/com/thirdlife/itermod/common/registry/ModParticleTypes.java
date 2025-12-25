package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.iterMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticleTypes {
    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, iterMod.MOD_ID);
    public static final RegistryObject<SimpleParticleType> ARCANE_PARTICLE = REGISTRY.register("arcane_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ETHERBOLT_TRAIL = REGISTRY.register("etherbolt_trail", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ETHERBOLT_IMPACT = REGISTRY.register("etherbolt_impact", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ETHERBOLT_POOF = REGISTRY.register("etherbolt_poof", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SNOWFLAKE = REGISTRY.register("snowflake", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SNOW_POOF = REGISTRY.register("snow_poof", () -> new SimpleParticleType(false));
}
