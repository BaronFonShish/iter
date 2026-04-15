package com.malignant.itermod.common.item.magic.spells;

import com.malignant.itermod.common.item.magic.defaults.SpellItem;
import com.malignant.itermod.common.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SpellGust extends SpellItem {

    public SpellGust() {super(new Properties(), "primal", "force", "air",1, 10, 2, 14);}

    @Override
    public void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower) {
        int iterations = (int) (10 + (10 * spellpower));

        float dist = 0;
        double yheight = player.getEyePosition().y;
        double xdir = player.getLookAngle().x;
        double ydir = player.getLookAngle().y;
        double zdir = player.getLookAngle().z;
        boolean flag = true;

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                ModSounds.CAST_ARCANE.get(), SoundSource.PLAYERS, 0.5F, 1.0F);

        level.playSound(null, player.blockPosition(),
                SoundEvents.PHANTOM_FLAP,
                SoundSource.PLAYERS, 0.8f, 1.2f);

        for (int i = 0; i < iterations; i++) {
            if (flag) {
                if (level instanceof ServerLevel serverLevel) {

                    serverLevel.sendParticles(ParticleTypes.POOF,
                            player.getX() + xdir * dist,
                            yheight + ydir * dist,
                            player.getZ() + zdir * dist,
                            1, (player.getRandom().nextFloat() * 0.05 * i),
                            (player.getRandom().nextFloat() * 0.05 * i),
                            (player.getRandom().nextFloat() * 0.05 * i), 0);

//                    serverLevel.addParticle(ParticleTypes.POOF,
//                            player.getX() + xdir * dist + ((player.getRandom().nextFloat() - 0.5f) * 0.05 * i),
//                            yheight + ydir * dist + ((player.getRandom().nextFloat() - 0.5f) * 0.05 * i),
//                            player.getZ() + zdir * dist + ((player.getRandom().nextFloat() - 0.5f) * 0.05 * i),
//                            xdir * player.getRandom().nextFloat() * 0.05,
//                            ydir * player.getRandom().nextFloat() * 0.05,
//                            zdir * player.getRandom().nextFloat() * 0.05);
                }

                if (level.getBlockState(BlockPos.containing(player.getX() + xdir * dist, yheight + ydir * dist, player.getZ() + zdir * dist)).isSolid()) {
                    flag = false;
                    break;
                }

                final Vec3 center = new Vec3(player.getX() + xdir * dist, yheight + ydir * dist, player.getZ() + zdir * dist);
                List<Entity> entfound = level.getEntitiesOfClass(Entity.class, new AABB(center, center).inflate(1 + dist / 15f), e -> true).stream().sorted(Comparator.comparingDouble(entcnd -> entcnd.distanceToSqr(center))).toList();

                for (Entity entityiterator : entfound) {
                    if ((entityiterator instanceof LivingEntity) || (entityiterator instanceof ItemEntity) && (player != entityiterator)) {
                        Vec3 newMotion = entityiterator.getDeltaMovement().add(new Vec3(
                                xdir * spellpower * 0.125,
                                ydir * spellpower * 0.125,
                                zdir * spellpower * 0.125));
                        entityiterator.setDeltaMovement(newMotion);
                    }
                }
                dist = dist + 0.2f;
            }
        }
    }
}