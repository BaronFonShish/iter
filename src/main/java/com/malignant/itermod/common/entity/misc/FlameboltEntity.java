package com.malignant.itermod.common.entity.misc;

import com.malignant.itermod.common.registry.ModParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;


public class FlameboltEntity extends AbstractMagicProjectile {

    public FlameboltEntity(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
    }

    public FlameboltEntity(EntityType<? extends AbstractArrow> type, Level level, float baseDamage) {
        super(type, level, baseDamage);
    }

    public FlameboltEntity(EntityType<? extends AbstractArrow> type, Level level, LivingEntity shooter, float baseDamage) {
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
        this.particleBurst(32);
        float power = (0.5f + this.getProjectileDamage()/10);
        target.setSecondsOnFire((int) (7 * power));
        areaEffect(power);
    }

    @Override
    protected void spawnTrailParticles() {
        Level level = this.level();
        Vec3 pos = this.position();
            double offsetX = (this.random.nextDouble() - 0.5) * 0.2;
            double offsetY = (this.random.nextDouble() - 0.5) * 0.2;
            double offsetZ = (this.random.nextDouble() - 0.5) * 0.2;
            level.addParticle(ModParticleTypes.FLAME_TRAIL.get(),
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

        float power = (0.5f + this.getProjectileDamage()/10);
        areaEffect(power);
    }

    public void particleBurst(int k){
        if (level() instanceof ServerLevel serverLevel){
            serverLevel.sendParticles(ModParticleTypes.FLAME.get(), this.getX(), this.getY(), this.getZ(), k,
                    0, 0, 0, Mth.nextFloat(random, 0.15f, 0.5f));
        }
    }

    @Override
    protected void playImpactSound() {
        this.playSound(SoundEvents.FIRECHARGE_USE, 0.75F, 1F);
    }

    public void areaEffect(float power){
        if (level().isClientSide()) return;

        final Vec3 center = new Vec3(this.getX(), this.getY(), this.getZ());
        float radius = 2f + power * 0.25f;
        float damage = 3 + power * 0.5f;

        List<LivingEntity> hitEntities = level().getEntitiesOfClass
                (LivingEntity.class, new AABB(center, center)
                        .inflate((radius)));

        for (LivingEntity entity : hitEntities) {
            if (!(entity == this.getOwner())) {

                if (entity.getTicksFrozen() > 140) {
                    damage *= 2f;
                }

                entity.hurt(new DamageSource(level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.ON_FIRE), this.getOwner()), damage);
                entity.setSecondsOnFire((int) (5 * power));
            }
        }
    }
}