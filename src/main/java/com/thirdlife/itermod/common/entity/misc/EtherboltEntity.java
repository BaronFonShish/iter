package com.thirdlife.itermod.common.entity.misc;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;

import net.minecraft.world.level.Level;

import net.minecraft.world.phys.Vec3;


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
            for (int i = 0; i < 8; ++i) {
                double x = target.getX() + (this.random.nextDouble() - 0.5) * target.getBbWidth();
                double y = target.getY() + this.random.nextDouble() * target.getBbHeight();
                double z = target.getZ() + (this.random.nextDouble() - 0.5) * target.getBbWidth();

                level.addParticle(ParticleTypes.ENCHANTED_HIT, x, y, z, 0, 0, 0);
            }
        }
    }

    @Override
    protected void spawnParticles() {
        Level level = this.level();
        Vec3 pos = this.position();

        for (int i = 0; i < 3; i++) {
            double offsetX = (this.random.nextDouble() - 0.5) * 0.2;
            double offsetY = (this.random.nextDouble() - 0.5) * 0.2;
            double offsetZ = (this.random.nextDouble() - 0.5) * 0.2;

            level.addParticle(ParticleTypes.WITCH,
                    pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ,
                    0, 0, 0);
        }
    }

    @Override
    protected void playImpactSound() {
        this.playSound(SoundEvents.AMETHYST_CLUSTER_BREAK, 1.0F, 1.5F);
    }
}