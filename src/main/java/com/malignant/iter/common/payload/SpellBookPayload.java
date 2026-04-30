package com.malignant.iter.common.payload;

import com.malignant.iter.IterMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record SpellBookPayload(ItemStack spellBook) implements CustomPacketPayload {
    public static final Type<SpellBookPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, "spell_book"));

    public static final StreamCodec<FriendlyByteBuf, SpellBookPayload> STREAM_CODEC =
            StreamCodec.ofMember(SpellBookPayload::write, SpellBookPayload::new);

    private SpellBookPayload(FriendlyByteBuf buf) {
        this(ItemStack.OPTIONAL_STREAM_CODEC.decode((RegistryFriendlyByteBuf) buf));
    }

    private void write(FriendlyByteBuf buf) {
        ItemStack.OPTIONAL_STREAM_CODEC.encode((RegistryFriendlyByteBuf) buf, spellBook);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}