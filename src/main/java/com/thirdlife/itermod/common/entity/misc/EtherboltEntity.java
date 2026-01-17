package com.thirdlife.itermod.common.entity.misc;

import com.thirdlife.itermod.common.registry.ModParticleTypes;
import com.thirdlife.itermod.common.registry.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
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
        super.hitEffects(target);
        this.playImpactSound();
        this.particleBurst(10);
    }

    @Override
    protected void spawnTrailParticles() {
        Level level = this.level();
        Vec3 pos = this.position();
            double offsetX = (this.random.nextDouble() - 0.5) * 0.2;
            double offsetY = (this.random.nextDouble() - 0.5) * 0.2;
            double offsetZ = (this.random.nextDouble() - 0.5) * 0.2;
            level.addParticle(ModParticleTypes.ETHERBOLT_TRAIL.get(),
                    pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ,
                    Mth.nextFloat(random, -0.025f, 0.025f),
                    Mth.nextFloat(random, -0.025f, 0.025f),
                    Mth.nextFloat(random, -0.025f, 0.025f));
    }

    @Override
    public void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        this.playImpactSound();
        this.particleBurst(10);
    }

    public void particleBurst(int k){
        if (level() instanceof ServerLevel serverLevel){
            serverLevel.sendParticles(ModParticleTypes.ETHERBOLT_POOF.get(), this.getX(), this.getY(), this.getZ(), 1,
                    0, 0, 0, 0);
            serverLevel.sendParticles(ModParticleTypes.ETHERBOLT_IMPACT.get(), this.getX(), this.getY(), this.getZ(), k,
                    0, 0, 0, Mth.nextFloat(random, 0.025f, 0.15f));
        }
    }

    @Override
    protected void playImpactSound() {
        this.playSound(ModSounds.ETHERBOLT_IMPACT.get(), 0.75F, 1F);
    }
}