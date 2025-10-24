package com.thirdlife.itermod.common.variables;

import com.thirdlife.itermod.common.registry.ModCapabilities;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpellSlotPacketServer {
    private final int newSlot;

    public SpellSlotPacketServer(int newSlot) {
        this.newSlot = newSlot;
    }

    public SpellSlotPacketServer(FriendlyByteBuf buf) {
        this.newSlot = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(newSlot);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            if (player != null) {
                player.getCapability(ModCapabilities.MAGE_DATA).ifPresent(data -> {
                    data.setSelectedSpellSlot(newSlot);

                    MageUtils.syncSpellSlot(player);
                });
            }
        });
        context.get().setPacketHandled(true);
    }
}