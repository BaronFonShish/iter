package com.malignant.itermod.common.entity.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.UUID;


public abstract class AbstractBeam extends Entity {

    private static final EntityDataAccessor<Integer> DATA_LIFETIME = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> DATA_TARGET_X = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_TARGET_Y = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_TARGET_Z = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Optional<UUID>> DATA_TARGET_ENTITY = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.OPTIONAL_UUID);

    private static final EntityDataAccessor<Float> DATA_WIDTH = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_ALPHA = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Boolean> DATA_FADING = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SHRINKING = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_FLICKERING = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SCROLLING = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<String> DATA_TEXTURE = SynchedEntityData.defineId(AbstractBeam.class, EntityDataSerializers.STRING);

    public float targetx;
    public float targety;
    public float targetz;
    public Optional<UUID> targetEntityUuid = Optional.empty();
    public boolean fading;
    public boolean shrinking;
    public boolean flickering;
    public boolean scrolling;
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
        this.entityData.define(DATA_TARGET_ENTITY, Optional.empty());
        this.entityData.define(DATA_WIDTH, 0.1f);
        this.entityData.define(DATA_ALPHA, 0.75f);
        this.entityData.define(DATA_FADING, false);
        this.entityData.define(DATA_SHRINKING, false);
        this.entityData.define(DATA_FLICKERING, false);
        this.entityData.define(DATA_SCROLLING, false);
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
        tag.putBoolean("scrolling", scrolling);

        if (targetEntityUuid.isPresent()) {
            tag.putUUID("TargetEntity", targetEntityUuid.get());
        }

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

        if (tag.contains("TargetEntity")) {
            UUID uuid = tag.getUUID("TargetEntity");
            this.targetEntityUuid = Optional.of(uuid);
            this.entityData.set(DATA_TARGET_ENTITY, this.targetEntityUuid);
        } else {
            this.targetEntityUuid = Optional.empty();
            this.entityData.set(DATA_TARGET_ENTITY, Optional.empty());
        }

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
        this.scrolling = tag.getBoolean("scrolling");
        this.entityData.set(DATA_SCROLLING, this.scrolling);

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

        if (DATA_TARGET_ENTITY.equals(key)) {
            this.targetEntityUuid = this.entityData.get(DATA_TARGET_ENTITY);
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
        if (DATA_SCROLLING.equals(key)) {
            this.scrolling = this.entityData.get(DATA_SCROLLING);
        }

        if (DATA_TEXTURE.equals(key)) {
            this.texture = this.entityData.get(DATA_TEXTURE);
        }
    }

    @Override
    public void tick(){
        super.tick();

        if (hasTargetEntity()) {
            Optional<Entity> target = getTargetEntity();
            if (target.isPresent() && target.get().isAlive()) {
                Vec3 entityPos = target.get().position();
                entityPos = entityPos.add(0, target.get().getBbHeight() / 2, 0);
                setEndPos(entityPos);
            }
        }

        if (this.tickCount > lifetime) discard();
    }

    public void setTargetEntity(Entity entity) {
        if (entity != null) {
            this.targetEntityUuid = Optional.of(entity.getUUID());
            this.entityData.set(DATA_TARGET_ENTITY, this.targetEntityUuid);
        } else {
            clearTargetEntity();
        }
    }

    public void clearTargetEntity() {
        this.targetEntityUuid = Optional.empty();
        this.entityData.set(DATA_TARGET_ENTITY, Optional.empty());
    }

    public Optional<Entity> getTargetEntity() {
        if (!targetEntityUuid.isPresent()) {
            return Optional.empty();
        }

        Entity entity = this.level().getPlayerByUUID(targetEntityUuid.get());
        if (entity != null) {
            return Optional.of(entity);
        }

        if (this.level() instanceof ServerLevel serverLevel) {
            entity = serverLevel.getEntity(targetEntityUuid.get());
            return Optional.ofNullable(entity);
        }

        return Optional.empty();
    }

    public boolean hasTargetEntity() {
        return targetEntityUuid.isPresent();
    }

    public Vec3 getTargetEntityPosition() {
        return getTargetEntity()
                .filter(Entity::isAlive)
                .map(Entity::position)
                .orElse(getEndPos());
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
        if (hasTargetEntity()) {
            Optional<Entity> target = getTargetEntity();
            if (target.isPresent() && target.get().isAlive()) {
                Vec3 entityPos = new Vec3(
                        target.get().getX(),
                        target.get().getY() + target.get().getBbHeight() / 3,
                        target.get().getZ()
                );
                if (this.level().isClientSide) {
                    this.targetx = (float) entityPos.x;
                    this.targety = (float) entityPos.y;
                    this.targetz = (float) entityPos.z;
                }
                return entityPos;
            }
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

    public void setScrolling(boolean scrolling) {
        this.scrolling = scrolling;
        this.entityData.set(DATA_SCROLLING, scrolling);
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
