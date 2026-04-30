package com.malignant.iter.common.payload;

import com.malignant.iter.IterMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SpellSlotPayload(int slot) implements CustomPacketPayload {
    public static final Type<SpellSlotPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, "spell_slot"));

    public static final StreamCodec<FriendlyByteBuf, SpellSlotPayload> STREAM_CODEC =
            StreamCodec.ofMember(SpellSlotPayload::write, SpellSlotPayload::new);

    private SpellSlotPayload(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(slot);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}