package com.malignant.iter.common.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.malignant.iter.common.item.magic.defaults.SpellFocus;
import com.malignant.iter.common.variables.IterPlayerDataUtils;
import com.malignant.iter.common.registry.ModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = "iter", value = Dist.CLIENT)
public class MageOverlay {

    private static final ResourceLocation MANA_OVERLAY = ResourceLocation.parse("iter:textures/gui/mana_overlay.png");
    private static final ResourceLocation ETHERBAR_FULL = ResourceLocation.parse("iter:textures/gui/etherbar_full.png");

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void eventHandler(RenderGuiEvent.Pre event) {
        Minecraft minecraft = Minecraft.getInstance();
        Player entity = minecraft.player;

        if (entity == null) return;

        GuiGraphics guiGraphics = event.getGuiGraphics();
        int width = minecraft.getWindow().getGuiScaledWidth();
        int height = minecraft.getWindow().getGuiScaledWidth();

        RenderSystem.enableBlend();

        boolean display = (entity.getMainHandItem().getItem() instanceof SpellFocus) ||
                (entity.getMainHandItem().is(ModTags.Items.SPELL_FOCUS));

        if (display) {
            float burnout = IterPlayerDataUtils.getBurnout(entity);
            float threshold = IterPlayerDataUtils.getThreshold(entity);
            float perc = 1f - (burnout / threshold);
            int progress = Math.min(Math.max(0, (int)(perc * 83)), 84);

            // stone background
            guiGraphics.blit(MANA_OVERLAY, 6, 5, 0, 0, 120, 50, 120, 50);

            // spell number
            String spellNumber = MageOverlayUtils.rahh(entity);
            guiGraphics.drawString(minecraft.font, spellNumber, 11, 42, -16737844, false);

            // 0/50
            guiGraphics.drawString(minecraft.font,
                    ((int) burnout + "/" + (int) threshold),
                    11, 25, -16750900, false);

            // spell name
            guiGraphics.drawString(minecraft.font, MageOverlayUtils.Grrr(entity), 23, 42, -3381505, false);

            // ether bar
            guiGraphics.blit(ETHERBAR_FULL, 9, 8, 0, 0, 83 - progress, 9, 83, 9);
        }

        RenderSystem.disableBlend();
    }
}