package com.thirdlife.itermod.common.item.magic.spells;

import com.thirdlife.itermod.common.entity.misc.EtherboltEntity;
import com.thirdlife.itermod.common.item.magic.defaults.SpellItem;
import com.thirdlife.itermod.common.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class SpellLeap extends SpellItem {

    public SpellLeap() {super(new Properties(), "arcane", "conveyance", "air",2, 5, 7, 75);}

    @Override
    public void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower) {

        if (level.isClientSide()) return;

        float power = 2.5f + spellpower;

        if (!player.onGround()){
            power *=0.5f;
        }

        Vec3 motion = new Vec3((player.getDeltaMovement().x() + player.getLookAngle().x * power),
                player.getDeltaMovement().y() + 0.25 + (player.getLookAngle().y * power)*0.5f,
                player.getDeltaMovement().z() + (player.getLookAngle().z * power));

        player.setDeltaMovement(motion);
        player.fallDistance = 0;
        player.hurtMarked = true;

        level.playSound(null, player.blockPosition(),
                SoundEvents.PHANTOM_FLAP,
                SoundSource.PLAYERS, 0.8f, 1.2f);

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.POOF,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    16,
                    0.15, 0.05, 0.15, 0.05);
        }
    }
}
