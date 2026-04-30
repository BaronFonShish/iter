package com.malignant.iter.common.entity.misc;

import com.malignant.iter.common.registry.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class JaggedBeam extends AbstractBeam {

    private static final EntityDataAccessor<Float> DATA_DEVIATION =
            SynchedEntityData.defineId(JaggedBeam.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_SEGMENT_LENGTH =
            SynchedEntityData.defineId(JaggedBeam.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<CompoundTag> DATA_POINTS =
            SynchedEntityData.defineId(JaggedBeam.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<Boolean> DATA_REFRESH =
            SynchedEntityData.defineId(JaggedBeam.class, EntityDataSerializers.BOOLEAN);

    public float deviation;
    public float segment_length;
    private List<Vec3> points = new ArrayList<>();
    public boolean refresh;

    public JaggedBeam(EntityType<? extends JaggedBeam> type, Level level) {
        super(type, level);
    }

    public JaggedBeam(Level level, Vec3 startpos, Vec3 endpos, int lifetime, float width, float segment_length, float deviation, float alpha,
                      boolean fading, boolean shrinking, boolean flickering, boolean scrolling, boolean refresh, String texture) {
        super(ModEntities.JAGGED_BEAM.get(), level);
        this.setPos(startpos);
        this.setEndPos(endpos);
        this.setLifetime(lifetime);
        this.setWidth(width);
        this.setAlpha(alpha);
        this.setFading(fading);
        this.setShrinking(shrinking);
        this.setFlickering(flickering);
        this.setScrolling(scrolling);
        this.setTexture(texture);
        this.setRefresh(refresh);

        this.setDeviation(deviation);
        this.setSegmentLength(segment_length);

        this.points = defineSegments();
        savePointsToData();
    }

    private List<Vec3> defineSegments() {
        Vec3 direction = this.getEndPos().subtract(this.position());
        Vec3 up = new Vec3(0, 1, 0);
        Vec3 right;
        Vec3 normalizedDir = direction.normalize();

        if (Math.abs(normalizedDir.y) > 0.99) {
            up = new Vec3(1, 0, 0);
            right = up.cross(normalizedDir).normalize();
        } else {
            right = normalizedDir.cross(up).normalize();
            up = right.cross(normalizedDir).normalize();
        }

        List<Vec3> segments = new ArrayList<>();
        segments.add(this.position());

        int segmentAmount = (int) (direction.length() / this.segment_length);

        if (segmentAmount == 0) {
            segments.add(this.getEndPos());
            return segments;
        }

        for (int i = 0; i <= segmentAmount; i++) {
            float progress = (float) i / segmentAmount;
            Vec3 point = this.position().add(direction.scale(progress));

            float deviationScale = 4 * progress * (1 - progress);

            float rightOffset = (this.level().random.nextFloat() - 0.5f) * deviationScale * deviation;
            float upOffset = (this.level().random.nextFloat() - 0.5f) * deviationScale * deviation;

            point = point.add(right.scale(rightOffset)).add(up.scale(upOffset));
            segments.add(point);
        }
        return segments;
    }

    @Override
    public void tick() {
        super.tick();

        if (getRefresh()) {
            this.points = defineSegments();
            savePointsToData();
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_DEVIATION, 0.15f);
        builder.define(DATA_SEGMENT_LENGTH, 1.25f);
        builder.define(DATA_POINTS, new CompoundTag());
        builder.define(DATA_REFRESH, false);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("deviation", deviation);
        tag.putFloat("segment_length", segment_length);
        ListTag pointsList = new ListTag();
        for (Vec3 point : this.points) {
            CompoundTag pointTag = new CompoundTag();
            pointTag.putDouble("x", point.x);
            pointTag.putDouble("y", point.y);
            pointTag.putDouble("z", point.z);
            pointsList.add(pointTag);
        }
        tag.put("points", pointsList);
        tag.putBoolean("refresh", refresh);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.deviation = tag.getFloat("deviation");
        this.getEntityData().set(DATA_DEVIATION, this.deviation);
        this.segment_length = tag.getFloat("segment_length");
        this.getEntityData().set(DATA_SEGMENT_LENGTH, this.segment_length);

        if (tag.contains("points")) {
            ListTag pointsList = tag.getList("points", 10);
            this.points.clear();
            for (int i = 0; i < pointsList.size(); i++) {
                CompoundTag pointTag = pointsList.getCompound(i);
                Vec3 point = new Vec3(
                        pointTag.getDouble("x"),
                        pointTag.getDouble("y"),
                        pointTag.getDouble("z")
                );
                this.points.add(point);
            }
            savePointsToData();
        }

        this.refresh = tag.getBoolean("refresh");
        this.getEntityData().set(DATA_REFRESH, this.refresh);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (DATA_DEVIATION.equals(key)) {
            this.deviation = this.getEntityData().get(DATA_DEVIATION);
        }
        if (DATA_SEGMENT_LENGTH.equals(key)) {
            this.segment_length = this.getEntityData().get(DATA_SEGMENT_LENGTH);
        }
        if (DATA_REFRESH.equals(key)) {
            this.refresh = this.getEntityData().get(DATA_REFRESH);
        }
        if (DATA_POINTS.equals(key)) {
            loadPointsFromData();
        }
    }

    public Float getDeviation() {
        return this.deviation;
    }

    public Float getSegmentLength() {
        return this.segment_length;
    }

    public boolean getRefresh() {
        return this.refresh;
    }

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
        this.getEntityData().set(DATA_REFRESH, refresh);
    }

    public void setDeviation(float deviation) {
        this.deviation = deviation;
        this.getEntityData().set(DATA_DEVIATION, deviation);
    }

    public void setSegmentLength(float segment_length) {
        this.segment_length = segment_length;
        this.getEntityData().set(DATA_SEGMENT_LENGTH, segment_length);
    }

    private void savePointsToData() {
        CompoundTag pointsTag = new CompoundTag();
        ListTag listTag = new ListTag();

        for (Vec3 point : points) {
            CompoundTag pointTag = new CompoundTag();
            pointTag.putDouble("x", point.x);
            pointTag.putDouble("y", point.y);
            pointTag.putDouble("z", point.z);
            listTag.add(pointTag);
        }

        pointsTag.put("points", listTag);
        pointsTag.putInt("count", points.size());
        this.getEntityData().set(DATA_POINTS, pointsTag);
    }

    public List<Vec3> getPoints() {
        if (this.points.isEmpty()) {
            loadPointsFromData();
        }
        return this.points;
    }

    private void loadPointsFromData() {
        CompoundTag pointsTag = this.getEntityData().get(DATA_POINTS);
        if (pointsTag != null && pointsTag.contains("points")) {
            ListTag listTag = pointsTag.getList("points", 10);
            this.points.clear();

            for (int i = 0; i < listTag.size(); i++) {
                CompoundTag pointTag = listTag.getCompound(i);
                Vec3 point = new Vec3(
                        pointTag.getDouble("x"),
                        pointTag.getDouble("y"),
                        pointTag.getDouble("z")
                );
                this.points.add(point);
            }
        }
    }
}