package com.malignant.iter.common.payload;
import com.malignant.iter.common.registry.ModCapabilities;
import com.malignant.iter.common.variables.IterPlayerData;
import com.malignant.iter.world.gui.*;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ModPayloadHandler {

    public static void handleBurnout(BurnoutPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null) {
                IterPlayerData data = ModCapabilities.getMageData(player);
                if (data != null) {
                    data.setEtherBurnout(payload.burnout());
                }
            }
        });
    }

    public static void handleSpellSlot(SpellSlotPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null) {
                IterPlayerData data = ModCapabilities.getMageData(player);
                if (data != null) {
                    data.setSelectedSpellSlot(payload.slot());
                }
            }
        });
    }

    public static void handleSpellBook(SpellBookPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null) {
                IterPlayerData data = ModCapabilities.getMageData(player);
                if (data != null) {
                    data.setSelectedSpellBook(payload.spellBook());
                }
            }
        });
    }

    public static void handleSpellLuck(SpellLuckPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null) {
                IterPlayerData data = ModCapabilities.getMageData(player);
                if (data != null) {
                    data.setSpellLuck(payload.luck());
                }
            }
        });
    }

    public static void handleSpellweaverSwitch(SpellweaverSwitchPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null) {
                IterPlayerData data = ModCapabilities.getMageData(player);
                if (data != null) {
                    data.setSpellweaverSwitch(payload.state());
                }
            }
        });
    }

    public static void handleFlying(FlyingPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null) {
                IterPlayerData data = ModCapabilities.getMageData(player);
                if (data != null) {
                    data.setFlying(payload.flying());
                }
            }
        });
    }

    public static void handleFlightTime(FlightTimePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null) {
                IterPlayerData data = ModCapabilities.getMageData(player);
                if (data != null) {
                    data.setFlightTime(payload.flightTime());
                }
            }
        });
    }

    public static void handleFullSync(FullSyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null) {
                IterPlayerData data = ModCapabilities.getMageData(player);
                if (data != null) {
                    data.setEtherBurnout(payload.burnout());
                    data.setSelectedSpellSlot(payload.spellSlot());
                    data.setSelectedSpellBook(payload.spellBook());
                    data.setSpellLuck(payload.spellLuck());
                    data.setSpellweaverSwitch(payload.spellweaverSwitch());
                    data.setFlightTime(payload.flightTime());
                    data.setFlying(payload.flying());
                }
            }
        });
    }
}