package com.thirdlife.itermod.common.event;

import com.mojang.realmsclient.dto.PlayerInfo;
import com.thirdlife.itermod.common.registry.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class SpiderEggHatchEvent {

    public static void check(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) {
            return;
        }

        if (isValid(entity)) {
            spawnSpiderlings(world, x, y, z);
        }
    }

    public static void force(LevelAccessor world, double x, double y, double z) {
        spawnSpiderlings(world, x, y, z);
    }

    private static boolean isValid(Entity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            ItemStack mainHandItem = getMainHandItem(entity);
            return (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, mainHandItem) == 0) && (serverPlayer.gameMode.getGameModeForPlayer() != GameType.CREATIVE);
        }
        return false;
    }

    private static ItemStack getMainHandItem(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            return livingEntity.getMainHandItem();
        }
        return ItemStack.EMPTY;
    }

    public static void spawnSpiderlings(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof ServerLevel serverLevel)) {
            return;
        }

        RandomSource random = world.getRandom();
        int spiderlingCount = Mth.nextInt(random, 2, 4);

        for (int i = 0; i < spiderlingCount; i++) {

            double spawnX = x + Mth.nextDouble(random, 0.3, 0.7);
            double spawnY = y + Mth.nextDouble(random, 0.2, 0.8);
            double spawnZ = z + Mth.nextDouble(random, 0.3, 0.7);

            BlockPos spawnPos = BlockPos.containing(spawnX, spawnY, spawnZ);

            Entity entityToSpawn = ModEntities.SPIDERLING.get().spawn(serverLevel, spawnPos, MobSpawnType.MOB_SUMMONED);

            if (entityToSpawn != null) {
                entityToSpawn.setYRot(random.nextFloat() * 360.0F);
            }
        }
    }
}