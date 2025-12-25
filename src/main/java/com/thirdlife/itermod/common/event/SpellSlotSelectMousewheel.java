package com.thirdlife.itermod.common.event;

import com.thirdlife.itermod.common.item.magic.defaults.SpellFocus;
import com.thirdlife.itermod.common.registry.ModCapabilities;
import com.thirdlife.itermod.common.registry.ModKeyBinds;
import com.thirdlife.itermod.common.registry.ModTags;
import com.thirdlife.itermod.common.variables.IterPlayerData;
import com.thirdlife.itermod.common.variables.IterPlayerDataUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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

        if (!SpellBookUtils.hasSpellbook(player)) return;

        if (!(player.getMainHandItem().getItem() instanceof SpellFocus)||(player.getMainHandItem().is(ModTags.Items.MAGICAL_ITEM))) return;

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

        LazyOptional<IterPlayerData> mageDataOpt = player.getCapability(ModCapabilities.ITER_PLAYER_DATA);
        if (!mageDataOpt.isPresent()) return;

        IterPlayerData iterPlayerData = mageDataOpt.resolve().get();
        int currentSlot = iterPlayerData.getSelectedSpellSlot();

        int direction = scrollDelta > 0 ? 1 : -1;

        int newSlot = (currentSlot + direction) % 8;
        if (newSlot < 1) {
            newSlot = 8;
        }

        iterPlayerData.setSelectedSpellSlot(newSlot);
        IterPlayerDataUtils.syncSpellSlot(player, newSlot);
        player.playSound(SoundEvents.UI_BUTTON_CLICK.get(), 0.2f, 1.0f);

    }

    // Reset the debounce flag every tick
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            wasHandledLastTick = false;
        }
    }
}
