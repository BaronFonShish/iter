package com.malignant.iter.common.entity.misc;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractMagicProjectile extends AbstractArrow {
    private static final net.minecraft.network.syncher.EntityDataAccessor<Float> PROJECTILE_DAMAGE =
            net.minecraft.network.syncher.SynchedEntityData.defineId(AbstractMagicProjectile.class,
                    net.minecraft.network.syncher.EntityDataSerializers.FLOAT);

    public AbstractMagicProjectile(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
        this.getEntityData().set(PROJECTILE_DAMAGE, 1.0f);
    }

    public AbstractMagicProjectile(EntityType<? extends AbstractArrow> type, Level level, float baseDamage) {
        super(type, level);
        this.getEntityData().set(PROJECTILE_DAMAGE, baseDamage);
    }

    public AbstractMagicProjectile(EntityType<? extends AbstractArrow> type, Level level, LivingEntity shooter, float baseDamage) {
        super(type, level);
        this.getEntityData().set(PROJECTILE_DAMAGE, baseDamage);
    }

    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(PROJECTILE_DAMAGE, 1.0f);
    }

    public void shootWithDamage(LivingEntity owner, float xRot, float yRot, float pitch, float velocity, float inaccuracy, float damage) {
        this.setOwner(owner);
        this.setProjectileDamage(damage);
        this.shootFromRotation(owner, xRot, yRot, pitch, velocity, inaccuracy);
    }

    public void shootWithDamage(LivingEntity owner, Vec3 direction, float velocity,
                                float inaccuracy, float damage) {
        this.setOwner(owner);
        this.setProjectileDamage(damage);
        this.shoot(direction.x, direction.y, direction.z, velocity, inaccuracy);
    }

    public void setProjectileDamage(float damage) {
        this.getEntityData().set(PROJECTILE_DAMAGE, damage);
    }

    public float getProjectileDamage() {
        return this.getEntityData().get(PROJECTILE_DAMAGE);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        Entity owner = this.getOwner();

        float damage = getProjectileDamage();

        if (target instanceof LivingEntity livingTarget) {
            DamageSource damageSource = new DamageSource(
                    this.level().registryAccess()
                            .registryOrThrow(Registries.DAMAGE_TYPE)
                            .getHolderOrThrow(DamageTypes.MOB_PROJECTILE),
                    this,
                    owner
            );

            if (livingTarget.hurt(damageSource, damage)) {
                this.doPostHurtEffects(livingTarget);
                this.hitEffects(livingTarget);
            }
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        BlockState blockstate = this.level().getBlockState(pResult.getBlockPos());
        blockstate.onProjectileHit(this.level(), blockstate, pResult, this);
        Vec3 vec3 = pResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(vec3);
        Vec3 vec31 = vec3.normalize().scale(0.05F);
        this.setPosRaw(this.getX() - vec31.x, this.getY() - vec31.y, this.getZ() - vec31.z);
        this.inGround = true;
    }

    protected void hitEffects(LivingEntity target) {
    }

    protected void playImpactSound() {
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            this.spawnTrailParticles();
        }

        if (this.tickCount > 200) {
            this.discard();
        }

        if (this.inGround) {
            this.discard();
        }
    }

    protected void spawnTrailParticles() {
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }
}