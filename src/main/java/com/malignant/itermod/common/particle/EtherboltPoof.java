package com.malignant.itermod.common.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EtherboltPoof extends TextureSheetParticle {
    public static EtherboltPoofProvider provider(SpriteSet spriteSet) {
        return new EtherboltPoofProvider(spriteSet);
    }

    public static class EtherboltPoofProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public EtherboltPoofProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new EtherboltPoof(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }

    private final SpriteSet spriteSet;

    protected EtherboltPoof(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        this.quadSize *= 3f;
        this.lifetime = (int) 11;
        this.gravity = 0f;
        this.hasPhysics = false;
        this.xd = vx * 0.5;
        this.yd = vy * 0.5;
        this.zd = vz * 0.5;
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.removed) {
            this.setSprite(this.spriteSet.get((this.age / 2) % 6 + 1, 6));
        }
    }
}
