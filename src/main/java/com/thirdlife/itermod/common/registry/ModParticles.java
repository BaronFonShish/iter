package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.common.particle.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
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
