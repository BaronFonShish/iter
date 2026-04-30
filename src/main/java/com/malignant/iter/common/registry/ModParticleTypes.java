package com.malignant.iter.common.registry;

import com.malignant.iter.IterMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModParticleTypes {
    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(Registries.PARTICLE_TYPE, IterMod.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ARCANE_PARTICLE =
            REGISTRY.register("arcane_particle", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ETHERBOLT_TRAIL =
            REGISTRY.register("etherbolt_trail", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ETHERBOLT_IMPACT =
            REGISTRY.register("etherbolt_impact", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ETHERBOLT_POOF =
            REGISTRY.register("etherbolt_poof", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FLAME =
            REGISTRY.register("flame", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FLAME_TRAIL =
            REGISTRY.register("flame_trail", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SNOWFLAKE =
            REGISTRY.register("snowflake", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SNOW_POOF =
            REGISTRY.register("snow_poof", () -> new SimpleParticleType(false));
}
