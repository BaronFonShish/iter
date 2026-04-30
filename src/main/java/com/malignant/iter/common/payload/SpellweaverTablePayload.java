package com.malignant.iter.common.payload;

import com.malignant.iter.IterMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SpellweaverTablePayload(int action, boolean switchState) implements CustomPacketPayload {
    public static final Type<SpellweaverTablePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, "spellweaver_table"));

    public static final StreamCodec<FriendlyByteBuf, SpellweaverTablePayload> STREAM_CODEC =
            StreamCodec.ofMember(SpellweaverTablePayload::write, SpellweaverTablePayload::new);

    public SpellweaverTablePayload(int action) {
        this(action, false);
    }

    public SpellweaverTablePayload(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readBoolean());
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(action);
        buf.writeBoolean(switchState);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}