package com.thirdlife.itermod.common.variables;

import com.thirdlife.itermod.common.registry.ModAttributes;
import com.thirdlife.itermod.common.registry.ModCapabilities;
import com.thirdlife.itermod.iterMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

public class MageUtils {

    public static MageData getMageData(Player player) {
        return player.getCapability(ModCapabilities.MAGE_DATA).resolve().orElse(null);
    }

    public static float getBurnout(Player player) {
        MageData data = getMageData(player);
        return data != null ? data.getEtherBurnout() : 0.0f;
    }

    public static float getThreshold(Player player) {
        AttributeInstance thresholdAttr = player.getAttribute(ModAttributes.ETHER_BURNOUT_THRESHOLD.get());
        float threshold = thresholdAttr != null ? (float) thresholdAttr.getValue() : 0.01f;
        return threshold;
    }

    public static int getSpellSlot(Player player){
        MageData data = getMageData(player);
        return data != null ? data.getSelectedSpellSlot() : 1;
    }

    public static void syncBurnout(ServerPlayer player) {
        player.getCapability(ModCapabilities.MAGE_DATA).ifPresent(data -> {
            iterMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> player),
                    new EtherBurnoutPacket(data.getEtherBurnout()));
        });
    }

    public static void syncSpellSlot(ServerPlayer player) {
        player.getCapability(ModCapabilities.MAGE_DATA).ifPresent(data -> {
            iterMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> player),
                    new SpellSlotPacket(data.getSelectedSpellSlot()));
        });
    }

    public static void syncSpellSlotClient(Player player) {
        player.getCapability(ModCapabilities.MAGE_DATA).ifPresent(data -> {
            iterMod.PACKET_HANDLER.sendToServer (new SpellSlotPacket(data.getSelectedSpellSlot()));
        });
    }


    public static void syncSpellSlotServer(int newSlot) {
        iterMod.PACKET_HANDLER.sendToServer(new SpellSlotPacketServer(newSlot));
    }
}
