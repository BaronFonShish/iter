package com.malignant.iter.common.variables;

import com.malignant.iter.common.payload.*;
import com.malignant.iter.common.registry.ModAttributes;
import com.malignant.iter.common.registry.ModCapabilities;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

public class IterPlayerDataUtils {

    /// Getters

    public static IterPlayerData getPlayerData(Player player) {
        return ModCapabilities.getMageData(player);
    }

    public static float getBurnout(Player player) {
        IterPlayerData data = getPlayerData(player);
        return data != null ? data.getEtherBurnout() : 0.0f;
    }

    public static float getThreshold(Player player) {
        AttributeInstance thresholdAttr = player.getAttribute(ModAttributes.ETHER_BURNOUT_THRESHOLD);
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
        AttributeInstance flightTimeAttr = player.getAttribute(ModAttributes.FLIGHT_TIME);
        return flightTimeAttr != null && flightTimeAttr.getValue() > 0;
    }

    public static float getDynamicDissipation(Player player){
        AttributeInstance dissipationBase = player.getAttribute(ModAttributes.ETHER_BURNOUT_DISSIPATION);
        float dissipation = dissipationBase != null ? (float) dissipationBase.getValue() : 0.02f;

        AttributeInstance thresholdAttr = player.getAttribute(ModAttributes.ETHER_BURNOUT_THRESHOLD);
        float threshold = thresholdAttr != null ? (float) thresholdAttr.getValue() : 50f;

        dissipation = (dissipation + (threshold * 0.00025f));
        float foodmult = 0.25f + (player.getFoodData().getFoodLevel()/20f) * 0.9f;
        dissipation *= foodmult;
        dissipation /= 20;
        return dissipation;
    }

    /// Setters

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

    /// Sync

    // burnout
    public static void syncBurnoutToClient(ServerPlayer player, float burnout) {
        player.connection.send(new BurnoutPayload(burnout));
    }

    public static void syncBurnoutToServer(float burnout) {
        PacketDistributor.sendToServer(new BurnoutPayload(burnout));
    }

    public static void syncBurnout(Player player, float burnout) {
        if (player.level().isClientSide) {
            syncBurnoutToServer(burnout);
        } else if (player instanceof ServerPlayer serverPlayer) {
            syncBurnoutToClient(serverPlayer, burnout);
        }
    }

    // flight time
    public static void syncFlightTimeToClient(ServerPlayer player, float flightTime) {
        player.connection.send(new FlightTimePayload(flightTime));
    }

    public static void syncFlightTimeToServer(float flightTime) {
        PacketDistributor.sendToServer(new FlightTimePayload(flightTime));
    }

    public static void syncFlightTime(Player player, float flightTime) {
        if (player.level().isClientSide) {
            syncFlightTimeToServer(flightTime);
        } else if (player instanceof ServerPlayer serverPlayer) {
            syncFlightTimeToClient(serverPlayer, flightTime);
        }
    }

    // flying state
    public static void syncFlyingToClient(ServerPlayer player, boolean flying) {
        player.connection.send(new FlyingPayload(flying));
    }

    public static void syncFlyingToServer(boolean flying) {
        PacketDistributor.sendToServer(new FlyingPayload(flying));
    }

    public static void syncFlying(Player player, boolean flying) {
        if (player.level().isClientSide) {
            syncFlyingToServer(flying);
        } else if (player instanceof ServerPlayer serverPlayer) {
            syncFlyingToClient(serverPlayer, flying);
        }
    }

    // spell slot
    public static void syncSpellSlotToClient(ServerPlayer player, int slot) {
        player.connection.send(new SpellSlotPayload(slot));
    }

    public static void syncSpellSlotToServer(int slot) {
        PacketDistributor.sendToServer(new SpellSlotPayload(slot));
    }

    public static void syncSpellSlot(Player player, int slot) {
        if (player.level().isClientSide) {
            syncSpellSlotToServer(slot);
        } else if (player instanceof ServerPlayer serverPlayer) {
            syncSpellSlotToClient(serverPlayer, slot);
        }
    }

    // spell book
    public static void syncSpellBookToClient(ServerPlayer player, ItemStack spellBook) {
        player.connection.send(new SpellBookPayload(spellBook));
    }

    public static void syncSpellBookToServer(ItemStack spellBook) {
        PacketDistributor.sendToServer(new SpellBookPayload(spellBook));
    }

    public static void syncSpellBook(Player player, ItemStack spellBook) {
        if (player.level().isClientSide) {
            syncSpellBookToServer(spellBook);
        } else if (player instanceof ServerPlayer serverPlayer) {
            syncSpellBookToClient(serverPlayer, spellBook);
        }
    }

    // spellweaver switch
    public static void syncSpellweaverSwitchToClient(ServerPlayer player, boolean state) {
        player.connection.send(new SpellweaverSwitchPayload(state));
    }

    public static void syncSpellweaverSwitchToServer(boolean state) {
        PacketDistributor.sendToServer(new SpellweaverSwitchPayload(state));
    }

    public static void syncSpellweaverSwitch(Player player, boolean state) {
        if (player.level().isClientSide) {
            syncSpellweaverSwitchToServer(state);
        } else if (player instanceof ServerPlayer serverPlayer) {
            syncSpellweaverSwitchToClient(serverPlayer, state);
        }
    }

    // spell luck
    public static void syncSpellLuckToClient(ServerPlayer player, float luck) {
        player.connection.send(new SpellLuckPayload(luck));
    }

    public static void syncSpellLuckToServer(float luck) {
        PacketDistributor.sendToServer(new SpellLuckPayload(luck));
    }

    public static void syncSpellLuck(Player player, float luck) {
        if (player.level().isClientSide) {
            syncSpellLuckToServer(luck);
        } else if (player instanceof ServerPlayer serverPlayer) {
            syncSpellLuckToClient(serverPlayer, luck);
        }
    }

    // full sync
    public static void syncAllToClient(ServerPlayer player) {
        IterPlayerData data = getPlayerData(player);
        if (data != null) {
            player.connection.send(new FullSyncPayload(
                    data.getEtherBurnout(),
                    data.getSelectedSpellSlot(),
                    data.getSelectedSpellBook(),
                    data.getSpellLuck(),
                    data.getSpellweaverSwitch(),
                    data.getFlightTime(),
                    data.getFlying()
            ));
        }
    }
    public static void syncOnLogin(ServerPlayer player) {
        syncAllToClient(player);
    }

    public static void syncOnDimensionChange(ServerPlayer player) {
        syncAllToClient(player);
    }

    public static void syncOnRespawn(ServerPlayer player) {
        syncAllToClient(player);
    }

    public static void syncOnClone(ServerPlayer newPlayer, Player original) {
        IterPlayerData originalData = getPlayerData(original);
        IterPlayerData newData = getPlayerData(newPlayer);

        if (originalData != null && newData != null) {
            newData.copyFrom(originalData);
            syncAllToClient(newPlayer);
        }
    }
}