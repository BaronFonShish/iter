package com.thirdlife.itermod.common.entity.misc;

import com.thirdlife.itermod.common.registry.ModParticleTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;

import net.minecraft.world.level.Level;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Random;


public class EtherboltEntity extends AbstractMagicProjectile {

    public EtherboltEntity(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
    }

    public EtherboltEntity(EntityType<? extends AbstractArrow> type, Level level, float baseDamage) {
        super(type, level, baseDamage);
    }

    public EtherboltEntity(EntityType<? extends AbstractArrow> type, Level level, LivingEntity shooter, float baseDamage) {
        super(type, level, shooter, baseDamage);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    protected void hitEffects(LivingEntity target) {
        // TODO hit effects
        Level level = target.level();

        if (level.isClientSide) {
            particleBurst(16);
        }
    }

    @Override
    protected void spawnParticles() {
        Level level = this.level();
        Vec3 pos = this.position();
            double offsetX = (this.random.nextDouble() - 0.5) * 0.2;
            double offsetY = (this.random.nextDouble() - 0.5) * 0.2;
            double offsetZ = (this.random.nextDouble() - 0.5) * 0.2;
            level.addParticle(ModParticleTypes.ARCANE_PARTICLE.get(),
                    pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ,
                    Mth.nextFloat(random, 0f, 0.05f),
                    Mth.nextFloat(random, 0f, 0.05f),
                    Mth.nextFloat(random, 0f, 0.05f));
    }

    @Override
    public void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        this.playImpactSound();
        particleBurst(16);
    }

    public void particleBurst(int k){
        for (int i=0; i < k; ++i) {
            this.level().addParticle(ModParticleTypes.ARCANE_PARTICLE.get(), this.getX(), this.getY(), this.getZ(),
                    Mth.nextFloat(random, 0.025f, 0.5f),
                    Mth.nextFloat(random, 0.025f, 0.5f),
                    Mth.nextFloat(random, 0.025f, 0.5f));
        }
    }

    @Override
    protected void playImpactSound() {
        this.playSound(SoundEvents.AMETHYST_CLUSTER_BREAK, 1.0F, 1.5F);
    }
}