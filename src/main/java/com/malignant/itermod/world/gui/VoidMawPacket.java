package com.malignant.itermod.world.gui;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class VoidMawPacket {
    private final int buttonID, x, y, z;

    public VoidMawPacket(FriendlyByteBuf buffer) {
        this.buttonID = buffer.readInt();
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
    }

    public VoidMawPacket(int buttonID, int x, int y, int z) {
        this.buttonID = buttonID;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void buffer(VoidMawPacket message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.buttonID);
        buffer.writeInt(message.x);
        buffer.writeInt(message.y);
        buffer.writeInt(message.z);
    }

    public static void handler(VoidMawPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {

            Player entity = context.getSender();
            if (entity == null) return;

            int buttonID = message.buttonID;
            int x = message.x;
            int y = message.y;
            int z = message.z;
            handleButtonAction(entity, buttonID, x, y, z);
        });
        context.setPacketHandled(true);
    }

    public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
        if (entity.level().isClientSide) return;

        if (buttonID == 0 && entity.containerMenu instanceof VoidMawGuiMenu menu) {
            menu.handleButtonClick(entity);
        }
    }
}
