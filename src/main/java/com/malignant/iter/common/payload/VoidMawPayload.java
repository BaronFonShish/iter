package com.malignant.iter.common.payload;

import com.malignant.iter.IterMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record VoidMawPayload(int buttonID, int x, int y, int z) implements CustomPacketPayload {
    public static final Type<VoidMawPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, "void_maw"));

    public static final StreamCodec<FriendlyByteBuf, VoidMawPayload> STREAM_CODEC =
            StreamCodec.ofMember(VoidMawPayload::write, VoidMawPayload::new);

    public VoidMawPayload(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(buttonID);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}