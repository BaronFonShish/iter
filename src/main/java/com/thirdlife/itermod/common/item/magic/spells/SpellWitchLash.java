package com.thirdlife.itermod.common.item.magic.spells;

import com.thirdlife.itermod.common.item.magic.defaults.SpellItem;
import com.thirdlife.itermod.common.registry.ModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
import org.checkerframework.checker.signature.qual.PolySignature;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SpellWitchLash extends SpellItem {

    public SpellWitchLash() {
        super(new Properties(), "occult", "force", "decay",0, 8, 1, 8);
    }

    @Override
    public void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower) {
        int iterations = (int) (15 + (15 * spellpower));

        float dist = 0;
        double yheight = player.getEyePosition().y;
        double xdir = player.getLookAngle().x;
        double ydir = player.getLookAngle().y;
        double zdir = player.getLookAngle().z;
        boolean flag = true;

        level.playSound(null, BlockPos.containing(player.position()), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.experience_orb.pickup"))), SoundSource.PLAYERS, (float) 0.5, (float) 0.85);

        for (int i = 0; i < iterations; i++) {
            if (flag) {
                if ((i % 4 == 0) && (level instanceof ServerLevel serverLevel)) {
                    serverLevel.sendParticles(ParticleTypes.WITCH, player.getX() + xdir * dist, yheight + ydir * dist, player.getZ() + zdir * dist, 1, 0, 0, 0, 0);
                }

                if (level.getBlockState(BlockPos.containing(player.getX() + xdir * dist, yheight + ydir * dist, player.getZ() + zdir * dist)).isSolid()) {
                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.WITCH, player.getX() + xdir * dist, yheight + ydir * dist, player.getZ() + zdir * dist, 8, 0.025, 0.025, 0.025, 0.025);
                    }
                    flag = false;
                    break;
                }

                final Vec3 center = new Vec3(player.getX() + xdir * dist, yheight + ydir * dist, player.getZ() + zdir * dist);
                List<Entity> entfound = level.getEntitiesOfClass(Entity.class, new AABB(center, center).inflate(1 / 10f), e -> true).stream().sorted(Comparator.comparingDouble(entcnd -> entcnd.distanceToSqr(center))).toList();

                for (Entity entityiterator : entfound) {
                    if ((entityiterator instanceof LivingEntity) && !(player == entityiterator)) {
                        entityiterator.hurt(new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC), player),
                                3f * spellpower);
                        if (level instanceof ServerLevel serverLevel) {
                                 serverLevel.sendParticles(ParticleTypes.WITCH, player.getX() + xdir * dist, yheight + ydir * dist, player.getZ() + zdir * dist, 8, 0.025, 0.025, 0.025, 0.025);
                             }
                        flag = false;
                        break;
                    }
                }
                    dist = dist + 0.2f;
            }
        }
    }
}
