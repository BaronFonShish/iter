package com.malignant.itermod.common.item.magic.spells;

import com.malignant.itermod.common.item.magic.defaults.SpellItem;
import com.malignant.itermod.common.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpellRecall extends SpellItem {

    public SpellRecall() {
        super(new Properties(), "arcane", "conveyance", "ether",3, 100, 100, (int) (3600));
    }

    @Override
    public void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower) {

        if (level.isClientSide) return;

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                ModSounds.CAST_ARCANE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.PORTAL,
                    player.getX(),
                    player.getEyeY(),
                    player.getZ(),
                    30,
                    0.35, 0.5, 0.35, 0.025);
            serverLevel.sendParticles(ParticleTypes.REVERSE_PORTAL,
                    player.getX(),
                    player.getEyeY(),
                    player.getZ(),
                    30,
                    0.35, 0.5, 0.35, 0.025);
        }
        if (player instanceof ServerPlayer serverPlayer && !player.level().isClientSide()) {
            ResourceKey<Level> destination = serverPlayer.getRespawnDimension();

            BlockPos spawnPos = serverPlayer.getRespawnPosition();

            if (spawnPos == null) {
                ServerLevel overworld = serverPlayer.server.getLevel(Level.OVERWORLD);
                if (overworld == null) {
                    overworld = (ServerLevel) level;
                }
                spawnPos = overworld.getSharedSpawnPos();
                destination = overworld.dimension();
            }


            ServerLevel nextLevel = serverPlayer.server.getLevel(destination);
            if (nextLevel == null) {
                nextLevel = (ServerLevel) level;
                spawnPos = nextLevel.getSharedSpawnPos();
            }

            if (nextLevel != null) {
                serverPlayer.teleportTo(nextLevel, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(),
                        serverPlayer.getYRot(),
                        serverPlayer.getXRot());
                serverPlayer.fallDistance = 0;
                serverPlayer.connection.send(new ClientboundPlayerAbilitiesPacket(serverPlayer.getAbilities()));
                for (MobEffectInstance _effectinstance : serverPlayer.getActiveEffects())
                    serverPlayer.connection.send(new ClientboundUpdateMobEffectPacket(serverPlayer.getId(), _effectinstance));
                serverPlayer.connection.send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));

                serverPlayer.addEffect(new MobEffectInstance(MobEffects.DARKNESS, (int) (50 + 50/spellpower), 0, false, true));
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, (int) (25 + 25/spellpower), 0, false, true));
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (50 + 50/spellpower), 1, false, true));
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.CONFUSION, (int) (25 + 25/spellpower), 0, false, true));

                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.PORTAL,
                            player.getX(),
                            player.getEyeY(),
                            player.getZ(),
                            30,
                            0.35, 0.5, 0.35, 0.025);
                    serverLevel.sendParticles(ParticleTypes.REVERSE_PORTAL,
                            player.getX(),
                            player.getEyeY(),
                            player.getZ(),
                            30,
                            0.35, 0.5, 0.35, 0.025);
                }
            }
        }
    }
}
