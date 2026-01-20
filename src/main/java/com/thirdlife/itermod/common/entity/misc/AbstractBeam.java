package com.thirdlife.itermod.common.entity.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;


public abstract class AbstractBeam extends Entity {

    private static final EntityDataAccessor<Integer> DATA_LIFETIME = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> DATA_TARGET_X = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_TARGET_Y = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_TARGET_Z = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Float> DATA_WIDTH = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_ALPHA = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Boolean> DATA_FADING = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SHRINKING = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_FLICKERING = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<String> DATA_TEXTURE = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.STRING);

    public float targetx;
    public float targety;
    public float targetz;
    public boolean fading;
    public boolean shrinking;
    public boolean flickering;
    public int lifetime;
    public float width;
    public float alpha;
    public String texture = "beam";

    public AbstractBeam(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
        this.noCulling = true;
        this.texture = this.entityData.get(DATA_TEXTURE);
    }

    BlockPos pos = BlockPos.containing(getEndPos().x, getEndPos().y, getEndPos().z);

    public AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().minmax(new AABB(pos).inflate(2.0));
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_LIFETIME, 25);
        this.entityData.define(DATA_TARGET_X, 0f);
        this.entityData.define(DATA_TARGET_Y, 0f);
        this.entityData.define(DATA_TARGET_Z, 0f);
        this.entityData.define(DATA_WIDTH, 0.1f);
        this.entityData.define(DATA_ALPHA, 0.75f);
        this.entityData.define(DATA_FADING, false);
        this.entityData.define(DATA_SHRINKING, false);
        this.entityData.define(DATA_FLICKERING, false);
        this.entityData.define(DATA_TEXTURE, "beam");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putFloat("TargetX", targetx);
        tag.putFloat("TargetY", targety);
        tag.putFloat("TargetZ", targetz);
        tag.putFloat("width", width);
        tag.putFloat("alpha", alpha);

        tag.putBoolean("fading", fading);
        tag.putBoolean("shrinking", shrinking);
        tag.putBoolean("flickering", flickering);

        tag.putInt("lifetime", lifetime);

        tag.putString("texture", texture);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.targetx = tag.getFloat("TargetX");
        this.entityData.set(DATA_TARGET_X, this.targetx);
        this.targety = tag.getFloat("TargetY");
        this.entityData.set(DATA_TARGET_Y, this.targety);
        this.targetz = tag.getFloat("TargetZ");
        this.entityData.set(DATA_TARGET_Z, this.targetz);

        this.width = tag.getFloat("width");
        this.entityData.set(DATA_WIDTH, this.width);
        this.alpha = tag.getFloat("alpha");
        this.entityData.set(DATA_ALPHA, this.alpha);
        this.lifetime = tag.getInt("lifetime");
        this.entityData.set(DATA_LIFETIME, this.lifetime);

        this.fading = tag.getBoolean("fading");
        this.entityData.set(DATA_FADING, this.fading);
        this.shrinking = tag.getBoolean("shrinking");
        this.entityData.set(DATA_SHRINKING, this.shrinking);
        this.flickering = tag.getBoolean("flickering");
        this.entityData.set(DATA_FLICKERING, this.flickering);

        this.texture = tag.getString("texture");
        this.entityData.set(DATA_TEXTURE, this.texture);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (DATA_TARGET_X.equals(key)) {
            this.targetx = this.entityData.get(DATA_TARGET_X);
        }
        if (DATA_TARGET_Y.equals(key)) {
            this.targety = this.entityData.get(DATA_TARGET_Y);
        }
        if (DATA_TARGET_Z.equals(key)) {
            this.targetz = this.entityData.get(DATA_TARGET_Z);
        }

        if (DATA_WIDTH.equals(key)) {
            this.width = this.entityData.get(DATA_WIDTH);
        }
        if (DATA_ALPHA.equals(key)) {
            this.alpha = this.entityData.get(DATA_ALPHA);
        }
        if (DATA_LIFETIME.equals(key)) {
            this.lifetime = this.entityData.get(DATA_LIFETIME);
        }

        if (DATA_FADING.equals(key)) {
            this.fading = this.entityData.get(DATA_FADING);
        }
        if (DATA_SHRINKING.equals(key)) {
            this.shrinking = this.entityData.get(DATA_SHRINKING);
        }
        if (DATA_FLICKERING.equals(key)) {
            this.flickering = this.entityData.get(DATA_FLICKERING);
        }

        if (DATA_TEXTURE.equals(key)) {
            this.texture = this.entityData.get(DATA_TEXTURE);
        }
    }

    @Override
    public void tick(){
        super.tick();
        if (this.tickCount > lifetime) discard();
    }

    public void setEndPos(Vec3 pos){
        this.targetx = (float) pos.x();
        this.targety = (float) pos.y();
        this.targetz = (float) pos.z();

        this.entityData.set(DATA_TARGET_X, (float) pos.x());
        this.entityData.set(DATA_TARGET_Y, (float) pos.y());
        this.entityData.set(DATA_TARGET_Z, (float) pos.z());
    }

    public void setEndPos(float x, float y, float z){
        this.targetx = x;
        this.targety = y;
        this.targetz = z;

        this.entityData.set(DATA_TARGET_X, x);
        this.entityData.set(DATA_TARGET_Y, y);
        this.entityData.set(DATA_TARGET_Z, z);
    }

    public Vec3 getEndPos() {
        if (this.level().isClientSide) {
            return new Vec3(
                    this.entityData.get(DATA_TARGET_X),
                    this.entityData.get(DATA_TARGET_Y),
                    this.entityData.get(DATA_TARGET_Z)
            );
        }
        return new Vec3(targetx, targety, targetz);
    }

    public void setWidth(float width) {
        this.width = width;
        this.entityData.set(DATA_WIDTH, width);
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
        this.entityData.set(DATA_LIFETIME, lifetime);
    }

    public void setFading(boolean fading) {
        this.fading = fading;
        this.entityData.set(DATA_FADING, fading);
    }

    public void setShrinking(boolean shrinking) {
        this.shrinking = shrinking;
        this.entityData.set(DATA_SHRINKING, shrinking);
    }

    public String getTexture() {
        return this.entityData.get(DATA_TEXTURE);
    }

    public Float getAlpha() {
        return this.alpha;
    }

    public void setTexture(String texture) {
        this.texture = texture;
        this.entityData.set(DATA_TEXTURE, texture);
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        this.entityData.set(DATA_ALPHA, alpha);
    }

    public void setFlickering(boolean flickering) {
        this.flickering = flickering;
        this.entityData.set(DATA_FLICKERING, flickering);
    }

}
