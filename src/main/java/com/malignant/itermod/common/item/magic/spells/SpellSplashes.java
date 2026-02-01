package com.malignant.itermod.common.item.magic.spells;

import com.malignant.itermod.common.item.magic.defaults.SpellItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class SpellSplashes extends SpellItem {

    public SpellSplashes() {super(new Properties(), "primal", "form", "water",1, 10, 2, 16);}

    @Override
    public void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower) {
        int iterations = (int) (15 + (15 * spellpower));

        float dist = 0;
        double yheight = player.getEyePosition().y;
        double xdir = player.getLookAngle().x;
        double ydir = player.getLookAngle().y;
        double zdir = player.getLookAngle().z;
        boolean flag = true;

        level.playSound(null, player.blockPosition(),
                SoundEvents.GENERIC_SPLASH,
                SoundSource.PLAYERS, 0.5f, 1.2f);

        if (player.isShiftKeyDown()){
            if (player.isOnFire()){
                level.playSound(null, player.blockPosition(),
                        SoundEvents.FIRE_EXTINGUISH,
                        SoundSource.PLAYERS, 0.5f, 1.2f);
            }
            player.clearFire();
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.SPLASH,
                        player.getX(),
                        yheight-0.5f,
                        player.getZ(),
                        30,
                        0.35, 0.75, 0.35, 0.025);
            }
        } else {

        for (int i=0; i<iterations; i++) {
            if (flag) {
                BlockPos position = BlockPos.containing(
                        player.getX() + xdir * dist,
                        yheight + ydir * dist,
                        player.getZ() + zdir * dist);
                BlockState blockState = level.getBlockState(position);
                if (blockState.is(BlockTags.create(new ResourceLocation("minecraft:campfires")))
                        || (blockState.is(BlockTags.create(new ResourceLocation("minecraft:candles"))))) {
                    if (blockState.getBlock().getStateDefinition().getProperty("lit") instanceof BooleanProperty lit) {
                        level.setBlock(position, blockState.setValue(lit, false), 3);
                    }
                }

                if (level.getBlockState(BlockPos.containing(player.getX() + xdir * dist, yheight + ydir * dist, player.getZ() + zdir * dist)).is(Blocks.FIRE)) {
                    level.setBlock(position, Blocks.AIR.defaultBlockState(), 3);
                    level.playSound(null, position,
                            SoundEvents.FIRE_EXTINGUISH,
                            SoundSource.PLAYERS, 0.5f, 1.2f);
                }

                final Vec3 center = new Vec3(player.getX() + xdir * dist, yheight + ydir * dist, player.getZ() + zdir * dist);
                List<Entity> entfound = level.getEntitiesOfClass(Entity.class, new AABB(center, center).inflate(1 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(entcnd -> entcnd.distanceToSqr(center))).toList();
                for (Entity entityiterator : entfound) {
                    if ((entityiterator instanceof LivingEntity) && !(player == entityiterator) && entityiterator.isOnFire()) {
                        entityiterator.clearFire();
                    }
                }

                if (level.getBlockState(BlockPos.containing(player.getX() + xdir * dist, yheight + ydir * dist, player.getZ() + zdir * dist)).isSolid()) {
                    flag = false;
                    break;
                }

                dist = dist + 0.2f;
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.SPLASH,
                            player.getX() + xdir * dist,
                            yheight + ydir * dist,
                            player.getZ() + zdir * dist,
                            2,
                            0.35, 0.35, 0.35, 0.025);
                  }
              }
            }
        }
    }
}