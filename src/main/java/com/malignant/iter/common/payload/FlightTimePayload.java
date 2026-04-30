package com.malignant.iter.common.payload;

import com.malignant.iter.IterMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record FlightTimePayload(float flightTime) implements CustomPacketPayload {
    public static final Type<FlightTimePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, "flight_time"));

    public static final StreamCodec<FriendlyByteBuf, FlightTimePayload> STREAM_CODEC =
            StreamCodec.ofMember(FlightTimePayload::write, FlightTimePayload::new);

    private FlightTimePayload(FriendlyByteBuf buf) {
        this(buf.readFloat());
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeFloat(flightTime);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}