package com.thirdlife.itermod.common.event;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.thirdlife.itermod.common.variables.MageUtils;
import com.thirdlife.itermod.common.registry.ModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "iter", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class MageOverlay {

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void eventHandler(RenderGuiEvent.Pre event) {
        int width = event.getWindow().getGuiScaledWidth();
        int height = event.getWindow().getGuiScaledHeight();
        Level world = null;
        double x = 0;
        double y = 0;
        double z = 0;
        Player entity = Minecraft.getInstance().player;
        if (entity != null) {
            world = entity.level();
            x = entity.getX();
            y = entity.getY();
            z = entity.getZ();
        }
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        boolean display = false;

        display = (entity.getMainHandItem().is(ModTags.Items.SPELL_FOCI));
        if (display) {
            float perc = (1f-(MageUtils.getBurnout(entity)/MageUtils.getThreshold(entity)));
            int progress = Math.min(Math.max(0, (int)(perc * 83)), 84);

            event.getGuiGraphics().blit(new ResourceLocation("iter:textures/gui/mana_overlay.png"), 6, 5, 0, 0, 99, 50, 99, 50);


            String spellNumber = MageOverlayUtils.rahh(entity);
            event.getGuiGraphics().drawString(Minecraft.getInstance().font, spellNumber, 11, 42, -16737844, false);
          event.getGuiGraphics().drawString(Minecraft.getInstance().font,
                  ((int)MageUtils.getBurnout(entity) + "/" + (int)MageUtils.getThreshold(entity))
                  , 11, 25, -16750900, false);
//            event.getGuiGraphics().drawString(Minecraft.getInstance().font, ReturnSpellNameProcedure.execute(entity), 23, 42, -3381505, false);
            event.getGuiGraphics().blit(new ResourceLocation("iter:textures/gui/etherbar_full.png"), 9, 8, 0, 0, 83 - progress, 9, 83, 9);

        }
        RenderSystem.depthMask(true);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }
}
