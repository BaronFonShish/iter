package com.malignant.itermod.common.item.magic.spells;

import com.malignant.itermod.common.entity.misc.JaggedBeam;
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
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SpellArcaneBeam extends SpellItem {

    public SpellArcaneBeam() {super(new Properties(), "arcane", "force", "ether", 3, 15, 5, 35);}


    @Override
    public void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower) {

        Vec3 lookDirection = player.getLookAngle();
        double range = (25 + (15 * spellpower));
        double closestDist = range;

        Entity target = null;

        Vec3 startPos = player.getEyePosition();
        Vec3 endPos = startPos.add(lookDirection.scale(range));
        Vec3 hitPos = endPos;

        double distance = 0;

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                ModSounds.CAST_ARCANE.get(), SoundSource.PLAYERS, 0.8F, 1.0F);


        boolean foundEntity = false;

        AABB searchBox = new AABB(startPos, endPos).inflate(2.0);
        List<Entity> candidates = level.getEntities(player, searchBox,
                e -> e instanceof LivingEntity && e.isAlive()
        );

        for (Entity entity : candidates) {
            AABB entityBox = entity.getBoundingBox();

            Optional<Vec3> hitPoint = entityBox.clip(startPos, endPos);

            if (hitPoint.isPresent()) {
                distance = startPos.distanceTo(hitPoint.get());
                if (distance < closestDist) {
                    closestDist = distance;
                    hitPos = hitPoint.get();
                    target = entity;
                    foundEntity = true;
                }
            }
        }

        if (foundEntity) {
            target.hurt(new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC), player),
                    10f * spellpower);

        } else {
            ClipContext context = new ClipContext( startPos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null);
            BlockHitResult blockHit = level.clip(context);
            hitPos = blockHit.getLocation();
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

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ModParticleTypes.ETHERBOLT_POOF.get(), hitPos.x, hitPos.y, hitPos.z, 1,
                    0, 0, 0, 0);
            serverLevel.sendParticles(ModParticleTypes.ETHERBOLT_IMPACT.get(), hitPos.x, hitPos.y, hitPos.z,
                    8, 0.025, 0.025, 0.025, 0.025);
        }

        StraightBeam beam = new StraightBeam(level, startPos, endPos, 10, 0.15f * spellpower, 0.85f,
                false, true, false, true, "beam");
        level.addFreshEntity(beam);
    }

}