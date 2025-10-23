package com.thirdlife.itermod.common.variables;

import com.thirdlife.itermod.common.registry.ModCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EtherBurnoutPacket {
    private final float etherBurnout;

    public EtherBurnoutPacket(float etherBurnout) {
        this.etherBurnout = etherBurnout;
    }

    public EtherBurnoutPacket(FriendlyByteBuf buf) {
        this.etherBurnout = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(etherBurnout);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {

            Player player = Minecraft.getInstance().player;
            if (player != null) {
                player.getCapability(ModCapabilities.MAGE_DATA).ifPresent(data -> {
                    data.setEtherBurnout(etherBurnout);
                });
            }
        });
        context.get().setPacketHandled(true);
    }
}