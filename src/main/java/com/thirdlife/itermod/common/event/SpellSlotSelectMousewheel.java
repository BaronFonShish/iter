package com.thirdlife.itermod.common.event;

import com.thirdlife.itermod.common.registry.ModCapabilities;
import com.thirdlife.itermod.common.registry.ModKeyBinds;
import com.thirdlife.itermod.common.variables.EtherBurnoutPacket;
import com.thirdlife.itermod.common.variables.MageData;
import com.thirdlife.itermod.common.variables.MageUtils;
import com.thirdlife.itermod.common.variables.SpellSlotPacket;
import com.thirdlife.itermod.iterMod;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = "iter", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class SpellSlotSelectMousewheel {

    private static boolean wasHandledLastTick = false;

    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        if (player == null || minecraft.screen != null) {
            return;
        }

        if (ModKeyBinds.SPELL_SLOT_SELECT.isDown()) {
            event.setCanceled(true);

            if (!wasHandledLastTick) {
                handleSpellSlotScroll(player, event.getScrollDelta());
                wasHandledLastTick = true;
            }
        } else {
            wasHandledLastTick = false;
        }
    }

    private static void handleSpellSlotScroll(Player player, double scrollDelta) {

        LazyOptional<MageData> mageDataOpt = player.getCapability(ModCapabilities.MAGE_DATA);
        if (!mageDataOpt.isPresent()) return;

        MageData mageData = mageDataOpt.resolve().get();
        int currentSlot = mageData.getSelectedSpellSlot();

        int direction = scrollDelta > 0 ? 1 : -1;

        int newSlot = (currentSlot + direction) % 6;
        if (newSlot < 1) {
            newSlot = 6;
        }

        mageData.setSelectedSpellSlot(newSlot);

        MageUtils.syncSpellSlotClient(player);

        player.playSound(SoundEvents.UI_BUTTON_CLICK.get(), 0.3f, 1.0f);

    }

    // Reset the debounce flag every tick
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            wasHandledLastTick = false;
        }
    }
}
