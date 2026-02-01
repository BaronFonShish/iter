package com.malignant.itermod.common.variables;

import com.malignant.itermod.iterMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerFlightPacket {
    private final boolean isFlying;

    public PlayerFlightPacket(boolean isFlying) {
        this.isFlying = isFlying;
    }

    public static void encode(PlayerFlightPacket msg, FriendlyByteBuf buf) {
        buf.writeBoolean(msg.isFlying);
    }

    public static PlayerFlightPacket decode(FriendlyByteBuf buf) {
        return new PlayerFlightPacket(buf.readBoolean());
    }

    public static void handle(PlayerFlightPacket msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            if (player != null) {
                IterPlayerDataUtils.setFlying(player, msg.isFlying);
            }
        });
        context.get().setPacketHandled(true);
    }

    public static void send(boolean isFlying) {
        iterMod.PACKET_HANDLER.sendToServer(new PlayerFlightPacket(isFlying));
    }
}
