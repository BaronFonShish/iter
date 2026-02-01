package com.malignant.itermod.common.event;


import com.malignant.itermod.common.variables.IterPlayerDataProvider;
import com.malignant.itermod.common.variables.IterPlayerDataUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.malignant.itermod.common.variables.IterPlayerDataUtils.syncAll;


@Mod.EventBusSubscriber(modid = "iter", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEtherCalc {

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        IterPlayerDataProvider.attach(event);
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer serverPlayer) {
            syncAll(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer serverPlayer) {
            syncAll(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer serverPlayer) {
            syncAll(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath() && !event.getEntity().level().isClientSide() &&
                event.getEntity() instanceof ServerPlayer newPlayer) {
            syncAll(newPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide()) {

            float dissipation = IterPlayerDataUtils.getDynamicDissipation(event.player);
            float burnout = IterPlayerDataUtils.getBurnout(event.player);

            if (burnout > 0) {
                if (dissipation >= burnout) {
                    IterPlayerDataUtils.setBurnout(event.player, 0);
                } else {
                    IterPlayerDataUtils.addBurnout(event.player, -dissipation);
                }
            }

            if (burnout < 0) {
                IterPlayerDataUtils.setBurnout(event.player, 0);
            }

            if ((event.player.level().getGameTime() % 20 == 0)) {
                int threshold_1 = (int) IterPlayerDataUtils.getThreshold(event.player);
                int threshold_2 = (int) (threshold_1 * 1.05f + 3);
                int threshold_3 = (int) (threshold_2 * 1.025f + 2);
                if (event.player instanceof ServerPlayer player) {
                    if (player.gameMode.getGameModeForPlayer() == GameType.SURVIVAL || player.gameMode.getGameModeForPlayer() == GameType.ADVENTURE) {
                        if (burnout >= threshold_1) {
                            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 50, 0, false, true));
                            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 25, 0, false, true));
                            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20, 0, false, true));
                        }
                        if (burnout >= threshold_2) {
                            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 25, 1, false, true));
                            player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 40, 0, false, true));
                            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 25, 1, false, true));
                        }
                        if (burnout >= threshold_3) {
                            if (!player.hasEffect(MobEffects.WITHER)) {
                                player.addEffect(new MobEffectInstance(MobEffects.WITHER, 40, 0, false, true));
                            }
                        }
                    }
                }
            }
        }
    }
}
