package com.malignant.iter.common.payload;

import com.malignant.iter.IterMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record FullSyncPayload(float burnout, int spellSlot, ItemStack spellBook,
                              float spellLuck, boolean spellweaverSwitch,
                              float flightTime, boolean flying) implements CustomPacketPayload {
    public static final Type<FullSyncPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, "full_sync"));

    public static final StreamCodec<FriendlyByteBuf, FullSyncPayload> STREAM_CODEC =
            StreamCodec.ofMember(FullSyncPayload::write, FullSyncPayload::new);

    private FullSyncPayload(FriendlyByteBuf buf) {
        this(buf.readFloat(), buf.readInt(),
                ItemStack.OPTIONAL_STREAM_CODEC.decode((RegistryFriendlyByteBuf) buf),
                buf.readFloat(), buf.readBoolean(),
                buf.readFloat(), buf.readBoolean());
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeFloat(burnout);
        buf.writeInt(spellSlot);
        ItemStack.OPTIONAL_STREAM_CODEC.encode((RegistryFriendlyByteBuf) buf, spellBook);
        buf.writeFloat(spellLuck);
        buf.writeBoolean(spellweaverSwitch);
        buf.writeFloat(flightTime);
        buf.writeBoolean(flying);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}