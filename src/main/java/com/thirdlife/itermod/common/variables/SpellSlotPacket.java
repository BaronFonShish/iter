package com.thirdlife.itermod.common.variables;

import com.thirdlife.itermod.common.registry.ModCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpellSlotPacket {
    private final int spellSlot;

    public SpellSlotPacket(int spellSlot) {this.spellSlot = spellSlot;}

    public SpellSlotPacket(FriendlyByteBuf buf) {
        this.spellSlot = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(spellSlot);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {

            Player player = Minecraft.getInstance().player;
            if (player != null) {
                player.getCapability(ModCapabilities.MAGE_DATA).ifPresent(data -> {
                    data.setSelectedSpellSlot(spellSlot);
                });
            }
        });
        context.get().setPacketHandled(true);
    }
}