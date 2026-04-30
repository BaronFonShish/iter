package com.malignant.iter.common.event;

import com.malignant.iter.IterMod;
import com.malignant.iter.common.item.magic.defaults.SpellFocus;
import com.malignant.iter.common.registry.ModAttributes;
import com.malignant.iter.common.registry.ModCapabilities;
import com.malignant.iter.common.registry.ModKeyBinds;
import com.malignant.iter.common.registry.ModTags;
import com.malignant.iter.common.variables.IterPlayerData;
import com.malignant.iter.common.variables.IterPlayerDataUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;

@EventBusSubscriber(modid = IterMod.MOD_ID, value = Dist.CLIENT)
public class SpellSlotSelectMousewheel {

    private static boolean wasHandledLastTick = false;

    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null || minecraft.screen != null) {
            return;
        }

        if (!(player.getMainHandItem().getItem() instanceof SpellFocus) ||
                !player.getMainHandItem().is(ModTags.Items.SPELL_FOCUS)) {
            return;
        }

        if (ModKeyBinds.SPELL_SLOT_SELECT.isDown()) {
            event.setCanceled(true);
            if (!wasHandledLastTick) {
                handleSpellSlotScroll(player, event.getScrollDeltaY());
                wasHandledLastTick = true;
            }
        } else {
            wasHandledLastTick = false;
        }
    }

    private static void handleSpellSlotScroll(Player player, double scrollDelta) {
        IterPlayerData iterPlayerData = ModCapabilities.getMageData(player);
        if (iterPlayerData == null) return;

        int currentSlot = iterPlayerData.getSelectedSpellSlot();
        int direction = scrollDelta > 0 ? 1 : -1;
        int newSlot = (currentSlot + direction) % 8;
        if (newSlot < 1) {
            newSlot = 8;
        }
        iterPlayerData.setSelectedSpellSlot(newSlot);
        IterPlayerDataUtils.syncSpellSlot(player, newSlot);
        player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 0.2f, 1.0f);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        wasHandledLastTick = false;
    }
}