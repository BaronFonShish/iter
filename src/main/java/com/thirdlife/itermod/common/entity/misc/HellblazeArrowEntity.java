package com.thirdlife.itermod.common.entity.misc;

import com.thirdlife.itermod.common.registry.ModEntities;
import com.thirdlife.itermod.common.registry.ModItems;
import com.thirdlife.itermod.common.registry.ModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;


public class HellblazeArrowEntity extends AbstractArrow implements ItemSupplier {
    public static final ItemStack PROJECTILE_ITEM = new ItemStack(ModItems.HELLBLAZE_ARROW.get());

    public HellblazeArrowEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(ModEntities.HELLBLAZE_ARROW.get(), world);
    }

    public HellblazeArrowEntity(EntityType<? extends HellblazeArrowEntity> type, Level world) {
        super(type, world);
    }

    public HellblazeArrowEntity(EntityType<? extends HellblazeArrowEntity> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public HellblazeArrowEntity(EntityType<? extends HellblazeArrowEntity> type, LivingEntity entity, Level world) {
        super(type, entity, world);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return PROJECTILE_ITEM;
    }

    @Override
    protected ItemStack getPickupItem() {
        return PROJECTILE_ITEM;
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        entity.setArrowCount(entity.getArrowCount() - 1);
    }

    @Override
    public void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        entityHitResult.getEntity().setSecondsOnFire(10);
        ExplodeImpact(this.level(), BlockPos.containing(this.getX(), this.getY(), this.getZ()), this.getOwner());
    }

    @Override
    public void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        ExplodeImpact(this.level(), BlockPos.containing(this.getX(), this.getY(), this.getZ()), this.getOwner());
    }

    @Override
    public void tick() {
        super.tick();

        Level level = this.level();
        Vec3 pos = this.position();
        double offsetX = (this.random.nextDouble() - 0.5) * 0.2;
        double offsetY = (this.random.nextDouble() - 0.5) * 0.2;
        double offsetZ = (this.random.nextDouble() - 0.5) * 0.2;
        level.addParticle(ParticleTypes.FLAME,
                pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ,
                Mth.nextFloat(random, -0.025f, 0.025f),
                Mth.nextFloat(random, -0.025f, 0.025f),
                Mth.nextFloat(random, -0.025f, 0.025f));
        level.addParticle(ParticleTypes.SMOKE,
                pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ,
                Mth.nextFloat(random, -0.025f, 0.025f),
                Mth.nextFloat(random, -0.025f, 0.025f),
                Mth.nextFloat(random, -0.025f, 0.025f));

        if (this.tickCount >= 50){
            this.setNoGravity(false);
        } else this.setNoGravity(true);

        if (this.inGround)
            this.discard();
    }

    private void ExplodeImpact(Level level, BlockPos pos, Entity attacker){
        level().explode(attacker, pos.getX(), pos.getY(), pos.getZ(), 1f, true, Level.ExplosionInteraction.NONE);
    }

    public static HellblazeArrowEntity shoot(Level world, LivingEntity entity, RandomSource source) {
        return shoot(world, entity, source, 2f, 5, 0);
    }

    public static HellblazeArrowEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
        HellblazeArrowEntity entityarrow = new HellblazeArrowEntity(ModEntities.HELLBLAZE_ARROW.get(), entity, world);
        entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
        entityarrow.setSilent(true);
        entityarrow.setCritArrow(false);
        entityarrow.setBaseDamage(damage);
        entityarrow.setKnockback(knockback);
        entityarrow.setNoGravity(true);
        world.addFreshEntity(entityarrow);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.shoot")), SoundSource.PLAYERS,
                1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return entityarrow;
    }

    public static HellblazeArrowEntity shoot(LivingEntity entity, LivingEntity target) {
        HellblazeArrowEntity entityarrow = new HellblazeArrowEntity(ModEntities.HELLBLAZE_ARROW.get(), entity, entity.level());
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + target.getEyeHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 2f * 2, 12.0F);
        entityarrow.setSilent(true);
        entityarrow.setBaseDamage(5);
        entityarrow.setKnockback(0);
        entityarrow.setCritArrow(false);
        entityarrow.setNoGravity(true);
        entity.level().addFreshEntity(entityarrow);
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new
                ResourceLocation("entity.arrow.shoot")), SoundSource.PLAYERS,
                1, 1f / (RandomSource.create().nextFloat() * 0.5f + 1));
        return entityarrow;
    }
}
