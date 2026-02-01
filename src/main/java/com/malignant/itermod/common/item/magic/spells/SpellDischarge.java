package com.malignant.itermod.common.item.magic.spells;

import com.malignant.itermod.common.entity.misc.JaggedBeam;
import com.malignant.itermod.common.item.magic.defaults.SpellItem;
import com.malignant.itermod.common.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
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

public class SpellDischarge extends SpellItem {

    public SpellDischarge() {super(new Properties(), "primal", "force", "lightning", 2, 10, 4, 27);}

    @Override
    public void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower) {

        Vec3 startPos = player.getEyePosition();
        int iterations = (int) (50 + (25 * spellpower));
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
                        serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, currentpos.x, currentpos.y, currentpos.z,
                                1, 0, 0, 0, 0.025);
                    }
                    flag = false;
                    endPos = currentpos;
                    break;
                }

                List<Entity> entfound = level.getEntitiesOfClass(Entity.class, new AABB(currentpos, currentpos).inflate(1/5f), e -> true).stream().sorted(Comparator.comparingDouble(entcnd -> entcnd.distanceToSqr(currentpos))).toList();

                for (Entity entityiterator : entfound) {
                    if ((entityiterator instanceof LivingEntity) && !(player == entityiterator)) {
                        entityiterator.hurt(new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC), player),
                                returnDamage(spellpower, i, iterations));
                        if (level instanceof ServerLevel serverLevel) {
                            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, currentpos.x, currentpos.y, currentpos.z,
                                    1, 0, 0, 0, 0.025);
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

        JaggedBeam beam = new JaggedBeam(level, startPos, endPos, 12, 0.1f * spellpower, 1f, 0.75f,0.5f,
                            false, true, true, true, true,"lightning");
                    level.addFreshEntity(beam);
    }

    public float returnDamage(float spellpower, int i, int iterations){
        float base = 3f * spellpower;
        float falloff = 1f - (float) (i/iterations);
        falloff *= 6f * spellpower;
        return base + falloff;
    }
}