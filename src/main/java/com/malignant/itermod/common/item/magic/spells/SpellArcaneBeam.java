package com.malignant.itermod.common.item.magic.spells;

import com.malignant.itermod.common.entity.misc.StraightBeam;
import com.malignant.itermod.common.item.magic.defaults.SpellItem;
import com.malignant.itermod.common.registry.ModParticleTypes;
import com.malignant.itermod.common.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class SpellArcaneBeam extends SpellItem {

    public SpellArcaneBeam() {super(new Properties(), "arcane", "force", "ether", 3, 15, 5, 35);}

    @Override
    public void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower) {

        Vec3 startPos = player.getEyePosition();
        int iterations = (int) (200 + (50 * spellpower));
        Vec3 lookDirection = player.getLookAngle();

        float dist = 0;
        double xdir = lookDirection.x;
        double ydir = lookDirection.y;
        double zdir = lookDirection.z;
        boolean flag = true;

        Vec3 endPos = Vec3.ZERO;

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                ModSounds.CAST_ARCANE.get(), SoundSource.PLAYERS, 0.8F, 1.0F);

        for (int i = 0; i < iterations; i++) {
            Vec3 currentpos = new Vec3(startPos.x + xdir * dist, startPos.y + ydir * dist, startPos.z + zdir * dist);
            if (flag) {
                if (level.getBlockState(BlockPos.containing(currentpos)).isSolid()){
                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ModParticleTypes.ETHERBOLT_POOF.get(), currentpos.x, currentpos.y, currentpos.z, 1,
                                0, 0, 0, 0);
                        serverLevel.sendParticles(ModParticleTypes.ETHERBOLT_IMPACT.get(), currentpos.x, currentpos.y, currentpos.z,
                                8, 0.025, 0.025, 0.025, 0.025);
                    }
                    flag = false;
                    endPos = currentpos;
                    break;
                }

                List<Entity> entfound = level.getEntitiesOfClass(Entity.class, new AABB(currentpos, currentpos).inflate(1/5f), e -> true).stream().sorted(Comparator.comparingDouble(entcnd -> entcnd.distanceToSqr(currentpos))).toList();

                for (Entity entityiterator : entfound) {
                    if ((entityiterator instanceof LivingEntity) && !(player == entityiterator)) {
                        entityiterator.hurt(new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC), player),
                                10f * spellpower);
                        if (level instanceof ServerLevel serverLevel) {
                            serverLevel.sendParticles(ModParticleTypes.ETHERBOLT_POOF.get(), currentpos.x, currentpos.y, currentpos.z, 1,
                                    0, 0, 0, 0);
                            serverLevel.sendParticles(ModParticleTypes.ETHERBOLT_IMPACT.get(), currentpos.x, currentpos.y, currentpos.z,
                                    8, 0.025, 0.025, 0.025, 0.025);
                        }
                        flag = false;
                        endPos = currentpos;
                        break;
                    }
                }
                dist = dist + 0.2f;
            }
            if (i+1 == iterations) { endPos = currentpos;}
        }


        Vec3 up = new Vec3(0, 1, 0);
        Vec3 side = up.cross(lookDirection).normalize();

        float up_offset = 0.3f;
        float side_offset = 0.25f;
        if (player.getMainArm() == HumanoidArm.RIGHT){
            side_offset *= -1;
        }


        startPos = startPos
                .add(side.scale(side_offset)
                .add(0, up_offset, 0));


            StraightBeam beam = new StraightBeam(level, startPos, endPos, 10, 0.15f * spellpower, 0.85f,
                    false, true, false, true, "beam");
            level.addFreshEntity(beam);
    }
}