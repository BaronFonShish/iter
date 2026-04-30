package com.malignant.iter.common.event;

import com.malignant.iter.common.variables.IterPlayerDataUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = "iter")
public class PlayerEtherCalc {

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer serverPlayer) {
            IterPlayerDataUtils.syncOnLogin(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer serverPlayer) {
            IterPlayerDataUtils.syncOnDimensionChange(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer serverPlayer) {
            IterPlayerDataUtils.syncOnRespawn(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer newPlayer) {
            if (event.getOriginal() instanceof ServerPlayer oldPlayer) {
                IterPlayerDataUtils.syncOnClone(newPlayer, oldPlayer);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer player) {

            float dissipation = IterPlayerDataUtils.getDynamicDissipation(player);
            float burnout = IterPlayerDataUtils.getBurnout(player);

            if (burnout > 0) {
                if (dissipation >= burnout) {
                    IterPlayerDataUtils.setBurnout(player, 0);
                } else {
                    IterPlayerDataUtils.addBurnout(player, -dissipation);
                }
            }

            if (burnout < 0) {
                IterPlayerDataUtils.setBurnout(player, 0);
            }

            if (player.level().getGameTime() % 20 == 0) {
                int threshold_1 = (int) IterPlayerDataUtils.getThreshold(player);
                int threshold_2 = (int) (threshold_1 * 1.05f + 3);
                int threshold_3 = (int) (threshold_2 * 1.025f + 2);

                GameType gameMode = player.gameMode.getGameModeForPlayer();
                if (gameMode == GameType.SURVIVAL || gameMode == GameType.ADVENTURE) {
                    float currentBurnout = IterPlayerDataUtils.getBurnout(player);

                    if (currentBurnout >= threshold_1) {
                        player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 50, 0, false, true));
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 25, 0, false, true));
                        player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20, 0, false, true)); // CONFUSION -> NAUSEA
                    }
                    if (currentBurnout >= threshold_2) {
                        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 25, 1, false, true));
                        player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 40, 0, false, true));
                        player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 25, 1, false, true));
                    }
                    if (currentBurnout >= threshold_3) {
                        if (!player.hasEffect(MobEffects.WITHER)) {
                            player.addEffect(new MobEffectInstance(MobEffects.WITHER, 40, 0, false, true));
                        }
                    }
                }
            }
        }
    }
}