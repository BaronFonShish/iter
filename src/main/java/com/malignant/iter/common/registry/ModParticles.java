package com.malignant.iter.common.registry;

import com.malignant.iter.common.particle.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT)
public class ModParticles {
    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticleTypes.ARCANE_PARTICLE.get(), ArcaneParticle::provider);
        event.registerSpriteSet(ModParticleTypes.ETHERBOLT_TRAIL.get(), EtherboltTrail::provider);
        event.registerSpriteSet(ModParticleTypes.ETHERBOLT_IMPACT.get(), EtherboltImpact::provider);
        event.registerSpriteSet(ModParticleTypes.ETHERBOLT_POOF.get(), EtherboltPoof::provider);
        event.registerSpriteSet(ModParticleTypes.FLAME.get(), Flame::provider);
        event.registerSpriteSet(ModParticleTypes.FLAME_TRAIL.get(), FlameTrail::provider);
        event.registerSpriteSet(ModParticleTypes.SNOWFLAKE.get(), Snowflake::provider);
        event.registerSpriteSet(ModParticleTypes.SNOW_POOF.get(), SnowPoof::provider);
    }
}
