package com.thirdlife.itermod.common.entity.misc;

import com.thirdlife.itermod.common.registry.ModParticleTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;


public class FrostSpikeEntity extends AbstractMagicProjectile {

    public FrostSpikeEntity(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
    }

    public FrostSpikeEntity(EntityType<? extends AbstractArrow> type, Level level, float baseDamage) {
        super(type, level, baseDamage);
    }

    public FrostSpikeEntity(EntityType<? extends AbstractArrow> type, Level level, LivingEntity shooter, float baseDamage) {
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
        float power = (float) (1 + this.getProjectileDamage()/20);
        if (target.fireImmune()) {
            power *= 1.25f;
        }
        target.setTicksFrozen((int) Math.min(target.getTicksFrozen(), 140 + power * 500));
        areaEffect(power);
    }

    @Override
    protected void spawnTrailParticles() {
        Level level = this.level();
        Vec3 pos = this.position();
            double offsetX = (this.random.nextDouble() - 0.5) * 0.2;
            double offsetY = (this.random.nextDouble() - 0.5) * 0.2;
            double offsetZ = (this.random.nextDouble() - 0.5) * 0.2;
            level.addParticle(ModParticleTypes.SNOWFLAKE.get(),
                    pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ,
                    Mth.nextFloat(random, -0.025f, 0.025f),
                    Mth.nextFloat(random, -0.025f, 0.025f),
                    Mth.nextFloat(random, -0.025f, 0.025f));
    }

    @Override
    public void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        this.playImpactSound();
        this.particleBurst(32);
        float power = (float) (1 + this.getProjectileDamage()/20);
        areaEffect(power);
    }

    public void particleBurst(int k){
        if (level() instanceof ServerLevel serverLevel){
            serverLevel.sendParticles(ModParticleTypes.SNOW_POOF.get(), this.getX(), this.getY(), this.getZ(), 1,
                    0, 0, 0, 0);
            serverLevel.sendParticles(ModParticleTypes.SNOWFLAKE.get(), this.getX(), this.getY(), this.getZ(), k,
                    0, 0, 0, Mth.nextFloat(random, 0.25f, 0.5f));
        }
    }

    public void areaEffect(float power){
        if (level().isClientSide()) return;

        final Vec3 center = new Vec3(this.getX(), this.getY(), this.getZ());
        float radius = 2 + power*0.25f;
        float damage = 3 + power * 0.5f;

        List<LivingEntity> hitEntities = level().getEntitiesOfClass
                (LivingEntity.class, new AABB(center, center)
                        .inflate((radius)));

        for (LivingEntity entity : hitEntities) {
            if (!(entity == this.getOwner())) {

                if (entity.fireImmune()) {
                    damage *= 1.5f;
                }

                entity.hurt(new DamageSource(level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.FREEZE), this.getOwner()), damage);
                entity.setTicksFrozen((int) Math.min(entity.getTicksFrozen(), 140 + power * 250));
            }
        }
    }

    @Override
    public boolean isNoGravity() {
        return (this.tickCount < 15);
    }

    @Override
    protected void playImpactSound() {
        this.playSound(SoundEvents.GLASS_BREAK, 0.75F, 1.25F);
    }
}