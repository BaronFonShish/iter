package com.malignant.itermod.common.entity.misc;

import com.malignant.itermod.common.registry.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class StraightBeam extends AbstractBeam {

    public StraightBeam(EntityType<? extends StraightBeam> type, Level level) {
        super(type, level);
    }

    public StraightBeam(Level level, Vec3 startpos, Vec3 endpos, int lifetime, float width, float alpha, boolean fading, boolean shrinking, boolean flickering, boolean scrolling, String texture) {
        super(ModEntities.STRAIGHT_BEAM.get(), level);
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
    }
}
