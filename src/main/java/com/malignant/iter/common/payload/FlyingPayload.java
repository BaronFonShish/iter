package com.malignant.iter.common.payload;

import com.malignant.iter.IterMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record FlyingPayload(boolean flying) implements CustomPacketPayload {
    public static final Type<FlyingPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, "flying"));

    public static final StreamCodec<FriendlyByteBuf, FlyingPayload> STREAM_CODEC =
            StreamCodec.ofMember(FlyingPayload::write, FlyingPayload::new);

    private FlyingPayload(FriendlyByteBuf buf) {
        this(buf.readBoolean());
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeBoolean(flying);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}