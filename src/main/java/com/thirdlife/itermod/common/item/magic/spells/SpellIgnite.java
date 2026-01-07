package com.thirdlife.itermod.common.item.magic.spells;

import com.thirdlife.itermod.common.entity.misc.EtherboltEntity;
import com.thirdlife.itermod.common.item.magic.defaults.SpellItem;
import com.thirdlife.itermod.common.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SpellIgnite extends SpellItem {

    public SpellIgnite() {super(new Properties(), "primal", "force", "fire",1, 20, 2, 8);}

    @Override
    public void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower) {
        int iterations = (int) (10 + (10 * spellpower));

        float dist = 0;
        double yheight = player.getEyePosition().y;
        double xdir = player.getLookAngle().x;
        double ydir = player.getLookAngle().y;
        double zdir = player.getLookAngle().z;
        boolean flag = true;

        level.playSound(null, BlockPos.containing(player.position()), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use"))), SoundSource.PLAYERS, (float) 0.5, (float) 1.25);

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
                        level.setBlock(position, blockState.setValue(lit, true), 3);
                        flag = false;
                    }
                }

                if (BaseFireBlock.canBePlacedAt(level, position.offset(0, 1, 0), Direction.UP) &&
                        level.isEmptyBlock(position.offset(0, 1, 0))) {
                    level.setBlock(position.offset(0, 1, 0), Blocks.FIRE.defaultBlockState(), 3);
                    flag = false;
                }

                final Vec3 center = new Vec3(player.getX() + xdir * dist, yheight + ydir * dist, player.getZ() + zdir * dist);
                List<Entity> entfound = level.getEntitiesOfClass(Entity.class, new AABB(center, center).inflate(1 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(entcnd -> entcnd.distanceToSqr(center))).toList();
                for (Entity entityiterator : entfound) {
                    if ((entityiterator instanceof LivingEntity) && !(player == entityiterator)) {
                        entityiterator.setRemainingFireTicks((int) (80 * spellpower));
                        flag = false;
                    }
                }
                if (!flag){
                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.FLAME,
                                player.getX() + xdir * dist,
                                yheight + ydir * dist,
                                player.getZ() + zdir * dist,
                                8,
                                0.025, 0.025, 0.025, 0.025);
                    }
                    break;
                }
                dist = dist + 0.2f;
            }
        }
    }
}