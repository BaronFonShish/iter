package com.thirdlife.itermod.common.event;

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
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;
        if (new Object() {
            public boolean checkGamemode(Entity _ent) {
                if (_ent instanceof ServerPlayer _serverPlayer) {
                    return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL;
                } else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
                    return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.SURVIVAL;
                }
                return false;
            }
        }.checkGamemode(entity) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)) == 0) {
            for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(), 2, 4); index0++) {
                if (world instanceof ServerLevel _level) {
                    Entity entityToSpawn = ModEntities.SPIDERLING.get().spawn(_level,
                            BlockPos.containing(x + Mth.nextDouble(RandomSource.create(), 0.3, 0.7), y + Mth.nextDouble(RandomSource.create(), 0.2, 0.8), z + Mth.nextDouble(RandomSource.create(), 0.3, 0.7)), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
                    }
                }
            }
        }
    }
    public static void blockBroken(LevelAccessor world, double x, double y, double z) {
        for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(), 2, 4); index0++) {
            if (world instanceof ServerLevel _level) {
                Entity entityToSpawn = ModEntities.SPIDERLING.get().spawn(_level,
                        BlockPos.containing(x + Mth.nextDouble(RandomSource.create(), 0.3, 0.7), y + Mth.nextDouble(RandomSource.create(), 0.2, 0.8), z + Mth.nextDouble(RandomSource.create(), 0.3, 0.7)), MobSpawnType.MOB_SUMMONED);
                if (entityToSpawn != null) {
                    entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
                }
            }
        }
    }
}
