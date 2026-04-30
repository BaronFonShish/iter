package com.malignant.iter.common.variables;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;


import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;


public class PlayerFlightPacket implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<PlayerFlightPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("iter", "player_flight_packet"));

    public static final StreamCodec<FriendlyByteBuf, PlayerFlightPacket> STREAM_CODEC =
            StreamCodec.of(PlayerFlightPacket::encode, PlayerFlightPacket::decode);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    private final boolean isFlying;

    public PlayerFlightPacket(boolean isFlying) {
        this.isFlying = isFlying;
    }

    public static void encode(FriendlyByteBuf buf, PlayerFlightPacket msg) {
        buf.writeBoolean(msg.isFlying);
    }

    public static PlayerFlightPacket decode(FriendlyByteBuf buf) {
        return new PlayerFlightPacket(buf.readBoolean());
    }

    public static void handle(PlayerFlightPacket msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            if (player != null) {
                IterPlayerDataUtils.setFlying(player, msg.isFlying);
            }
        });// TODO: [forge2neo] setPacketHandled() removed - automatic in NeoForge
    }

    public static void send(boolean isFlying) {
        PacketDistributor.sendToServer(new PlayerFlightPacket(isFlying));
    }
}
