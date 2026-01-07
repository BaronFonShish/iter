package com.thirdlife.itermod.common.variables;

import com.thirdlife.itermod.common.registry.ModAttributes;
import com.thirdlife.itermod.common.registry.ModCapabilities;
import com.thirdlife.itermod.iterMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

public class IterPlayerDataUtils {

    /// Getters

    public static IterPlayerData getPlayerData(Player player) {
        return player.getCapability(ModCapabilities.ITER_PLAYER_DATA).resolve().orElse(null);
    }

    public static float getBurnout(Player player) {
        IterPlayerData data = getPlayerData(player);
        return data != null ? data.getEtherBurnout() : 0.0f;
    }

    public static float getThreshold(Player player) {
        AttributeInstance thresholdAttr = player.getAttribute(ModAttributes.ETHER_BURNOUT_THRESHOLD.get());
        float threshold = thresholdAttr != null ? (float) thresholdAttr.getValue() : 0.01f;
        return threshold;
    }

    public static int getSpellSlot(Player player){
        IterPlayerData data = getPlayerData(player);
        return data != null ? data.getSelectedSpellSlot() : 1;
    }

    public static ItemStack getSpellBook(Player player) {
        IterPlayerData data = getPlayerData(player);
        return data != null ? data.getSelectedSpellBook() : ItemStack.EMPTY;
    }

    public static boolean getSpellweaverSwitch(Player player) {
        IterPlayerData data = getPlayerData(player);
        return data != null ? data.getSpellweaverSwitch() : false;
    }

    public static float getSpellLuck(Player player) {
        IterPlayerData data = getPlayerData(player);
        return data != null ? data.getSpellLuck() : 0f;
    }

    public static float getFlightTime(Player player) {
        IterPlayerData data = getPlayerData(player);
        return data != null ? data.getFlightTime() : 0f;
    }

    public static boolean isFlying(Player player) {
        IterPlayerData data = getPlayerData(player);
        return data != null ? data.getFlying(): false;
    }

    public static boolean ifCanFly(Player player) {
        AttributeInstance flightTimeAttr = player.getAttribute(ModAttributes.FLIGHT_TIME.get());
        return flightTimeAttr != null && flightTimeAttr.getValue() > 0;
    }


    public static float getDynamicDissipation(Player player){
        AttributeInstance dissipationBase = player.getAttribute(ModAttributes.ETHER_BURNOUT_DISSIPATION.get());
        float dissipation = dissipationBase != null ? (float) dissipationBase.getValue() : 0.02f;

        AttributeInstance thresholdAttr = player.getAttribute(ModAttributes.ETHER_BURNOUT_THRESHOLD.get());
        float threshold = thresholdAttr != null ? (float) thresholdAttr.getValue() : 50f;

        dissipation = (dissipation + (threshold * 0.0005f));
        dissipation /= 20;
        return dissipation;
    }

    /// Setters и операции.

    public static void setBurnout(Player player, float burnout) {
        IterPlayerData data = getPlayerData(player);
        if (data != null) {
            data.setEtherBurnout(burnout);
            syncBurnout(player, burnout);
        }
    }

    public static void addBurnout(Player player, float amount) {
        IterPlayerData data = getPlayerData(player);
        if (data != null) {
            float burnout = data.getEtherBurnout() + amount;
            // Для вычитания просто минус поставить
            data.setEtherBurnout(burnout);
            syncBurnout(player, burnout);
        }
    }

    public static void setSpellSlot(Player player, int slot) {
        IterPlayerData data = getPlayerData(player);
        if (data != null) {
            data.setSelectedSpellSlot(slot);
            syncSpellSlot(player, slot);
        }
    }

    public static void setSpellweaverSwitch(Player player, boolean state) {
        IterPlayerData data = getPlayerData(player);
        if (data != null) {
            data.setSpellweaverSwitch(state);
            syncSpellweaverSwitch(player, state);
        }
    }

    public static void addSpellLuck(Player player, float amount) {
        IterPlayerData data = getPlayerData(player);
        if (data != null) {
            float luck = data.getSpellLuck() + amount;
            data.setSpellLuck(luck);
            syncSpellLuck(player, luck);
        }
    }

    public static void setFlightTime(Player player, float amount) {
        IterPlayerData data = getPlayerData(player);
        if (data != null) {
            data.setFlightTime(amount);
            syncFlightTime(player, amount);
        }
    }

    public static void setFlying(Player player, boolean flystate) {
        IterPlayerData data = getPlayerData(player);
        if (data != null) {
            data.setFlying(flystate);
            syncFlying(player, flystate);
        }
    }

    /// Синхронизация. SC = Сервер -> Клиент, CS = Клиент -> Сервер, без - универсальный.

    // Выгорание

    public static void syncBurnoutSC(ServerPlayer player, float burnout) {
        iterMod.PACKET_HANDLER.send(
                PacketDistributor.PLAYER.with(() -> player),
                IterPlayerDataPacket.burnout(burnout)
        );
    }

    public static void syncBurnoutCS(float burnout) {
        iterMod.PACKET_HANDLER.sendToServer(IterPlayerDataPacket.burnout(burnout));
    }

    public static void syncBurnout(Player player, float burnout) {
        if (player.level().isClientSide) {
            syncBurnoutCS(burnout);
        } else if (player instanceof ServerPlayer serverPlayer) {
            syncBurnoutSC(serverPlayer, burnout);
        }
    }

    // Полёт

    public static void syncFlightTimeSC(ServerPlayer player, float flighttime) {
        iterMod.PACKET_HANDLER.send(
                PacketDistributor.PLAYER.with(() -> player),
                IterPlayerDataPacket.flightTime(flighttime)
        );
    }

    public static void syncFlightTimeCS(float flighttime) {
        iterMod.PACKET_HANDLER.sendToServer(IterPlayerDataPacket.flightTime(flighttime));
    }

    public static void syncFlightTime(Player player, float flighttime) {
        if (player.level().isClientSide) {
            syncFlightTimeCS(flighttime);
        } else if (player instanceof ServerPlayer serverPlayer) {
            syncFlightTimeSC(serverPlayer, flighttime);
        }
    }

    // Состояние полёта

    public static void syncFlyingSC(ServerPlayer player, boolean flystate) {
        iterMod.PACKET_HANDLER.send(
                PacketDistributor.PLAYER.with(() -> player),
                IterPlayerDataPacket.flying(flystate)
        );
    }

    public static void syncFlyingCS(boolean flystate) {
        iterMod.PACKET_HANDLER.sendToServer(IterPlayerDataPacket.flying(flystate));
    }

    public static void syncFlying(Player player, boolean flystate) {
        if (player.level().isClientSide) {
            syncFlyingCS(flystate);
        } else if (player instanceof ServerPlayer serverPlayer) {
            syncFlyingSC(serverPlayer, flystate);
        }
    }

    // Слот

    public static void syncSpellSlotSC(ServerPlayer player, int slot) {
        iterMod.PACKET_HANDLER.send(
                PacketDistributor.PLAYER.with(() -> player),
                IterPlayerDataPacket.spellSlot(slot)
        );
    }

    public static void syncSpellSlotCS(int slot) {
        iterMod.PACKET_HANDLER.sendToServer(IterPlayerDataPacket.spellSlot(slot));
    }

    public static void syncSpellSlot(Player player, int slot) {
        if (player.level().isClientSide) {
            syncSpellSlotCS(slot);
        } else if (player instanceof ServerPlayer serverPlayer) {
            syncSpellSlotSC(serverPlayer, slot);
        }
    }

    // Спеллбук

    public static void syncSpellBookSC(ServerPlayer player, ItemStack item) {
        iterMod.PACKET_HANDLER.send(
                PacketDistributor.PLAYER.with(() -> player),
                IterPlayerDataPacket.spellBook(item)
        );
    }

    public static void syncSpellBookCS(ItemStack item) {
        iterMod.PACKET_HANDLER.sendToServer(IterPlayerDataPacket.spellBook(item));
    }

    public static void syncSpellBook(Player player, ItemStack item) {
        if (player.level().isClientSide) {
            syncSpellBookCS(item);
        } else if (player instanceof ServerPlayer serverPlayer) {
            syncSpellBookSC(serverPlayer, item);
        }
    }

    // Стейт стола заклинателя

    public static void syncSpellweaverSwitchSC(ServerPlayer player, boolean state) {
        iterMod.PACKET_HANDLER.send(
                PacketDistributor.PLAYER.with(() -> player),
                IterPlayerDataPacket.spellweaverSwitch(state)
        );
    }

    public static void syncSpellweaverSwitchCS(boolean state) {
        iterMod.PACKET_HANDLER.sendToServer(IterPlayerDataPacket.spellweaverSwitch(state));
    }

    public static void syncSpellweaverSwitch(Player player, boolean state) {
        if (player.level().isClientSide) {
            syncSpellweaverSwitchCS(state);
        } else if (player instanceof ServerPlayer serverPlayer) {
            syncSpellweaverSwitchSC(serverPlayer, state);
        }
    }

    // Удача дропа спелла

    public static void syncSpellLuckSC(ServerPlayer player, float luck) {
        iterMod.PACKET_HANDLER.send(
                PacketDistributor.PLAYER.with(() -> player),
                IterPlayerDataPacket.spellLuck(luck)
        );
    }

    public static void syncSpellLuckCS(float luck) {
        iterMod.PACKET_HANDLER.sendToServer(IterPlayerDataPacket.spellLuck(luck));
    }

    public static void syncSpellLuck(Player player, float luck) {
        if (player.level().isClientSide) {
            syncSpellLuckCS(luck);
        } else if (player instanceof ServerPlayer serverPlayer) {
            syncSpellLuckSC(serverPlayer, luck);
        }
    }

    // Все

    public static void syncAllSC(ServerPlayer player) {
        IterPlayerData data = getPlayerData(player);
        if (data != null) {
            iterMod.PACKET_HANDLER.send(
                    PacketDistributor.PLAYER.with(() -> player),
                    IterPlayerDataPacket.fullSync(
                            data.getEtherBurnout(),
                            data.getSelectedSpellSlot(),
                            data.getSpellLuck(),
                            data.getSelectedSpellBook(),
                            data.getSpellweaverSwitch(),
                            data.getFlightTime(),
                            data.getFlying()
                    )
            );
        }
    }

    public static void syncAll(Player player) {
        IterPlayerData data = getPlayerData(player);
        if (data != null) {
            if (player.level().isClientSide) {
                syncBurnout(player, data.getEtherBurnout());
                syncSpellSlot(player, data.getSelectedSpellSlot());
                syncSpellBook(player, data.getSelectedSpellBook());
                syncSpellLuck(player, data.getSpellLuck());
                syncFlightTime(player, data.getFlightTime());
                syncFlying(player, data.getFlying());
            } else if (player instanceof ServerPlayer serverPlayer) {
                syncAllSC(serverPlayer);
            }
        }
    }


    public static void syncOnLogin(ServerPlayer player) {
        syncAllSC(player);
    }

    public static void syncOnDimensionChange(ServerPlayer player) {
        syncAllSC(player);
    }

    public static void syncOnRespawn(ServerPlayer player) {
        syncAllSC(player);
    }

    public static void syncOnClone(ServerPlayer newPlayer, Player original) {

        IterPlayerData originalData = getPlayerData(original);
        IterPlayerData newData = getPlayerData(newPlayer);

        if (originalData != null && newData != null) {
            newData.copyFrom(originalData);
            syncAllSC(newPlayer);
        }
    }
}
