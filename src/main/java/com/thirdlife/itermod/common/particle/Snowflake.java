package com.thirdlife.itermod.common.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Snowflake extends TextureSheetParticle {
    public static SnowflakeProvider provider(SpriteSet spriteSet) {
        return new SnowflakeProvider(spriteSet);
    }

    public static class SnowflakeProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public SnowflakeProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new Snowflake(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }

    private final SpriteSet spriteSet;
    int frames = 7;
    int perframe = 8;
    protected Snowflake(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        this.quadSize *= 3f;
        this.lifetime = (int) (frames * perframe)-1;
        this.gravity = 0f;
        this.hasPhysics = true;
        this.xd = vx * 0.5;
        this.yd = vy * 0.5;
        this.zd = vz * 0.5;
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.removed) {
            this.setSprite(this.spriteSet.get((this.age / perframe) % frames + 1, frames));
            if (this.age<20){
                this.gravity = 0;
            } else this.gravity = 0.25f;
        }
    }
}
