package com.malignant.itermod.common.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EtherboltImpact extends TextureSheetParticle {
    public static EtherboltImpactProvider provider(SpriteSet spriteSet) {
        return new EtherboltImpactProvider(spriteSet);
    }

    public static class EtherboltImpactProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public EtherboltImpactProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new EtherboltImpact(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }

    private final SpriteSet spriteSet;
    private final int frames;
    private final int perframe;


    protected EtherboltImpact(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;

        RandomSource random = world.random;
        frames = 3;
        perframe = random.nextIntBetweenInclusive(2, 3);

        this.setSize(0.1f, 0.1f);
        this.quadSize *= 1.5f;
        this.lifetime = (int) (frames * perframe)-1;
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
            this.setSprite(this.spriteSet.get((this.age / perframe) % frames + 1, frames));
        }
    }
}
