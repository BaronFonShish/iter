package com.malignant.iter.common.payload;

import com.malignant.iter.IterMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SpellweaverSwitchPayload(boolean state) implements CustomPacketPayload {
    public static final Type<SpellweaverSwitchPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, "spellweaver_switch"));

    public static final StreamCodec<FriendlyByteBuf, SpellweaverSwitchPayload> STREAM_CODEC =
            StreamCodec.ofMember(SpellweaverSwitchPayload::write, SpellweaverSwitchPayload::new);

    private SpellweaverSwitchPayload(FriendlyByteBuf buf) {
        this(buf.readBoolean());
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeBoolean(state);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}