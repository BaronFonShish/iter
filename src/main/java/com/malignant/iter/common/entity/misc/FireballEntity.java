package com.malignant.iter.common.entity.misc;

import com.malignant.iter.common.registry.ModParticleTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

import static net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE;


public class FireballEntity extends AbstractMagicProjectile {

    public FireballEntity(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
    }

    public FireballEntity(EntityType<? extends AbstractArrow> type, Level level, float baseDamage) {
        super(type, level, baseDamage);
    }

    public FireballEntity(EntityType<? extends AbstractArrow> type, Level level, LivingEntity shooter, float baseDamage) {
        super(type, level, shooter, baseDamage);
    }

    @Override
    protected void hitEffects(LivingEntity target) {
        super.hitEffects(target);
        this.playImpactSound();
        this.particleBurst(32);
        float power = (0.5f + this.getProjectileDamage()/10);
        target.setRemainingFireTicks((int) (200 * power));
        areaEffect(power);
    }

    @Override
    protected void spawnTrailParticles() {
        Level level = this.level();
        Vec3 pos = this.position();
            double offsetX = (this.random.nextDouble() - 0.5) * 0.5;
            double offsetY = (this.random.nextDouble() - 0.5) * 0.5;
            double offsetZ = (this.random.nextDouble() - 0.5) * 0.5;
            level.addParticle(ModParticleTypes.FLAME_TRAIL.get(),
                    pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ,
                    Mth.nextFloat(random, -0.05f, 0.05f),
                    Mth.nextFloat(random, -0.05f, 0.05f),
                    Mth.nextFloat(random, -0.05f, 0.05f));
            level.addParticle(ParticleTypes.LARGE_SMOKE,
                pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ,
                Mth.nextFloat(random, -0.05f, 0.05f),
                Mth.nextFloat(random, -0.05f, 0.05f),
                Mth.nextFloat(random, -0.05f, 0.05f));
    }

    @Override
    public void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        this.playImpactSound();
        this.particleBurst(32);

        float power = (0.5f + this.getProjectileDamage()/10);
        areaEffect(power);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return null;
    }

    public void particleBurst(int k){
        if (level() instanceof ServerLevel serverLevel){
            serverLevel.sendParticles(ModParticleTypes.FLAME.get(), this.getX(), this.getY(), this.getZ(), k,
                    0, 0, 0, Mth.nextFloat(random, 0.15f, 0.5f));
            serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(), k/2,
                    0, 0, 0, Mth.nextFloat(random, 0.15f, 0.5f));
            serverLevel.sendParticles(ParticleTypes.LAVA, this.getX(), this.getY(), this.getZ(), k/2,
                    0, 0, 0, Mth.nextFloat(random, 0.15f, 0.5f));
            serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY(), this.getZ(), k,
                    0, 0, 0, 0.2f);
            serverLevel.sendParticles(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), k/2,
                    0, 0, 0, Mth.nextFloat(random, 0.15f, 0.5f));
            serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 1,
                    0, 0, 0, 0);
        }
    }

    @Override
    protected void playImpactSound() {
        this.playSound(GENERIC_EXPLODE.value(), 1.0F, 1.0F);
    }

    public void areaEffect(float power){
        if (level().isClientSide()) return;

        final Vec3 center = new Vec3(this.getX(), this.getY(), this.getZ());
        float radius = 5f + power * 0.25f;
        float damage = 10 + power * 0.5f;

        List<LivingEntity> hitEntities = level().getEntitiesOfClass
                (LivingEntity.class, new AABB(center, center)
                        .inflate((radius)));

        for (LivingEntity entity : hitEntities) {
            if (!(entity == this.getOwner())) {

                if (entity.getTicksFrozen() > 140) {
                    damage *= 2f;
                }

                entity.hurt(new DamageSource(level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.ON_FIRE), this.getOwner()), damage);
                entity.setRemainingFireTicks((int) (120 * power));
            }
        }
    }
}