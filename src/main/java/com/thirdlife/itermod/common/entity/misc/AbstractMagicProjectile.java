package com.thirdlife.itermod.common.entity.misc;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraftforge.network.NetworkHooks;

public abstract class AbstractMagicProjectile extends AbstractArrow {
    private static final EntityDataAccessor<Float> PROJECTILE_DAMAGE = SynchedEntityData.defineId(AbstractMagicProjectile.class, EntityDataSerializers.FLOAT);

    public AbstractMagicProjectile(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
        this.entityData.set(PROJECTILE_DAMAGE, 1.0f);
    }

    public AbstractMagicProjectile(EntityType<? extends AbstractArrow> type, Level level, float baseDamage) {
        super(type, level);
        this.entityData.set(PROJECTILE_DAMAGE, baseDamage);
    }

    public AbstractMagicProjectile(EntityType<? extends AbstractArrow> type, Level level, LivingEntity shooter, float baseDamage) {
        super(type, shooter, level);
        this.entityData.set(PROJECTILE_DAMAGE, baseDamage);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PROJECTILE_DAMAGE, 1.0f);
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
        this.entityData.set(PROJECTILE_DAMAGE, damage);
    }

    public float getProjectileDamage() {
        return this.entityData.get(PROJECTILE_DAMAGE);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        Entity owner = this.getOwner();

        float damage = getProjectileDamage();

        if (target instanceof LivingEntity livingTarget) {
            if (livingTarget.hurt(this.damageSources().indirectMagic(this, owner), damage)) {
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
        Vec3 vec31 = vec3.normalize().scale((double)0.05F);
        this.setPosRaw(this.getX() - vec31.x, this.getY() - vec31.y, this.getZ() - vec31.z);
        this.inGround = true;
        this.setSoundEvent(SoundEvents.ARROW_HIT);
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

        if (this.tickCount > 600) {
            this.discard();
        }

        if (this.inGround)
            this.discard();
    }

    protected void spawnTrailParticles() {
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}