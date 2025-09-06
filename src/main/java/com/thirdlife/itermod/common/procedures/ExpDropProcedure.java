package com.thirdlife.itermod.common.procedures;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

public class ExpDropProcedure {

    public static void dropExp(Level level, double x, double y, double z, int totalExperience) {
        if (level.isClientSide()) return;

        int remaining = totalExperience;

        while (remaining > 0) {
            int orbValue = getOrbSize(remaining);
            remaining -= orbValue;
            level.addFreshEntity(new ExperienceOrb(level, x, y, z, orbValue));
        }
    }

    public static void blockBrokenRand(Level level, double x, double y, double z, int min, int max, Entity entity) {
        if (level.isClientSide()) return;
        boolean survivalmode = false;
        if (entity == null) return;
        if (entity instanceof ServerPlayer _serverPlayer) {
            survivalmode = (_serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL);
        } else if (entity.level().isClientSide() && entity instanceof Player _player) {
            survivalmode = (Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.SURVIVAL);
        }
        if (survivalmode) {

          int remaining = Mth.nextInt(RandomSource.create(), min, max);

            while (remaining > 0) {
             int orbValue = getOrbSize(remaining);
             remaining -= orbValue;
             level.addFreshEntity(new ExperienceOrb(level, x, y, z, orbValue));
         }
        }
    }

    public static void blockBrokenSet(Level level, double x, double y, double z, int total, Entity entity) {
        if (level.isClientSide()) return;
        boolean survivalmode = false;
        if (entity == null) return;
        if (entity instanceof ServerPlayer _serverPlayer) {
            survivalmode = (_serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL);
        } else if (entity.level().isClientSide() && entity instanceof Player _player) {
            survivalmode = (Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.SURVIVAL);
        }
        if (survivalmode) {

            int remaining = total;

            while (remaining > 0) {
                int orbValue = getOrbSize(remaining);
                remaining -= orbValue;
                level.addFreshEntity(new ExperienceOrb(level, x, y, z, orbValue));
            }
        }
    }

    private static int getOrbSize(int experience) {
        if (experience >= 2477) return 2477;
        if (experience >= 1237) return 1237;
        if (experience >= 617) return 617;
        if (experience >= 307) return 307;
        if (experience >= 149) return 149;
        if (experience >= 73) return 73;
        if (experience >= 37) return 37;
        if (experience >= 17) return 17;
        if (experience >= 7) return 7;
        if (experience >= 3) return 3;
        return 1;
    }
}