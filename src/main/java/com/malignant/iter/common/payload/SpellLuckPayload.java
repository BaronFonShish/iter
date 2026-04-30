package com.malignant.iter.common.payload;

import com.malignant.iter.IterMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SpellLuckPayload(float luck) implements CustomPacketPayload {
    public static final Type<SpellLuckPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, "spell_luck"));

    public static final StreamCodec<FriendlyByteBuf, SpellLuckPayload> STREAM_CODEC =
            StreamCodec.ofMember(SpellLuckPayload::write, SpellLuckPayload::new);

    private SpellLuckPayload(FriendlyByteBuf buf) {
        this(buf.readFloat());
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeFloat(luck);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}