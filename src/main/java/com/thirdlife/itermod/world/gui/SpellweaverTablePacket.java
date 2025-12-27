package com.thirdlife.itermod.world.gui;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpellweaverTablePacket {
    private final int action;

    public SpellweaverTablePacket(int action) {
        this.action = action;
    }

    private SpellweaverTablePacket(FriendlyByteBuf buffer) {
        this.action = buffer.readInt();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(action);
    }

    public static SpellweaverTablePacket decode(FriendlyByteBuf buffer) {
        return new SpellweaverTablePacket(buffer);
    }

    public static void handle(SpellweaverTablePacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                if (message.action == 0) {
                    SpellweaverTableFunction.execute(player);
                } else if (message.action == 1) {
                    SpellweaverTableFunction.flipswitch(player);
                }
            }
        });
        context.setPacketHandled(true);
    }
}
