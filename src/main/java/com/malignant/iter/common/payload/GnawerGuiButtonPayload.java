package com.malignant.iter.common.payload;

import com.malignant.iter.IterMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record GnawerGuiButtonPayload(int buttonID, int x, int y, int z) implements CustomPacketPayload {
    public static final Type<GnawerGuiButtonPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, "gnawer_gui_button"));

    public static final StreamCodec<FriendlyByteBuf, GnawerGuiButtonPayload> STREAM_CODEC =
            StreamCodec.ofMember(GnawerGuiButtonPayload::write, GnawerGuiButtonPayload::new);

    public GnawerGuiButtonPayload(FriendlyByteBuf buf) {
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