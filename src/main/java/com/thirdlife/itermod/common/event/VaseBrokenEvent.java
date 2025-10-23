package com.thirdlife.itermod.common.event;

import com.thirdlife.itermod.common.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;

public class VaseBrokenEvent {

    public static void check(LevelAccessor world, double x, double y, double z, Player entity) {
        if (entity == null) {
            return;
        }

        if (entity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL){
                spawnRandom(world, x, y, z);
            };
        }
    }

    private static void spawnRandom(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof ServerLevel serverLevel)) {
            return;
        }

        RandomSource random = world.getRandom();
        double toSpawn = Mth.nextFloat(random, 0f, 1f);

        if (toSpawn >= 0.95f) {

            double spawnX = x + Mth.nextDouble(random, 0.4f, 0.6f);
            double spawnY = y + Mth.nextDouble(random, 0.4f, 0.6f);
            double spawnZ = z + Mth.nextDouble(random, 0.4f, 0.6f);

            BlockPos spawnPos = BlockPos.containing(spawnX, spawnY, spawnZ);

            Entity entityToSpawn = EntityType.BAT.spawn(serverLevel, spawnPos, MobSpawnType.MOB_SUMMONED);

            toSpawn = Mth.nextFloat(random, 0f, 1f);

            if (toSpawn >= 0.99f){
                entityToSpawn = EntityType.ZOMBIE.spawn(serverLevel, spawnPos, MobSpawnType.MOB_SUMMONED);
                if (entityToSpawn instanceof Zombie zombie) {
                    zombie.setBaby(true);
                }
            } else if (toSpawn >= 0.8f){
                entityToSpawn = EntityType.SILVERFISH.spawn(serverLevel, spawnPos, MobSpawnType.MOB_SUMMONED);
            } else if (toSpawn >= 0.5f){
                entityToSpawn = ModEntities.SPIDERLING.get().spawn(serverLevel, spawnPos, MobSpawnType.MOB_SUMMONED);
            }

            if (entityToSpawn != null) {
                entityToSpawn.setYRot(random.nextFloat() * 360.0F);
            }
        }
    }
}