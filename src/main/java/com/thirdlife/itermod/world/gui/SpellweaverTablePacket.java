package com.thirdlife.itermod.world.gui;

import com.thirdlife.itermod.common.variables.IterPlayerDataUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpellweaverTablePacket {
    private final int action;
    private final boolean switchState;

    // Constructor for action 0 (execute)
    public SpellweaverTablePacket(int action) {
        this.action = action;
        this.switchState = false;
    }

    // Constructor for action 1 (flip switch)
    public SpellweaverTablePacket(int action, boolean switchState) {
        this.action = action;
        this.switchState = switchState;
    }

    private SpellweaverTablePacket(FriendlyByteBuf buffer) {
        this.action = buffer.readInt();
        this.switchState = buffer.readBoolean();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(action);
        buffer.writeBoolean(switchState);
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
                    IterPlayerDataUtils.setSpellweaverSwitch(player, message.switchState);
                    player.getPersistentData().putBoolean("iter_spellweaver_table_switch", message.switchState);
                }
            }
        });
        context.setPacketHandled(true);
    }
}