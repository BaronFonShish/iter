package com.malignant.itermod.world.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.malignant.itermod.iterMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class VoidMawGuiScreen extends AbstractContainerScreen<VoidMawGuiMenu> {
    private final static HashMap<String, Object> guistate = VoidMawGuiMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    ImageButton button_cleanse;

    public VoidMawGuiScreen(VoidMawGuiMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    private static final ResourceLocation texture = new ResourceLocation("iter:textures/gui/void_maw_gui.png");

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);

        int areaX = this.leftPos + 153;
        int areaY = this.topPos + 9;
        int areaWidth = 14;
        int areaHeight = 14;

        if (mouseX >= areaX && mouseX <= areaX + areaWidth &&
                mouseY >= areaY && mouseY <= areaY + areaHeight) {
            List<Component> tooltip = GuiTooltips.VoidMaw(entity);
            guiGraphics.renderTooltip(this.font,
                    tooltip, Optional.empty(),
                    mouseX, mouseY);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }

    @Override
    public void containerTick() {
        super.containerTick();
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, Component.translatable("gui.iter.void_maw.label"), 71, 7, -12829636, false);
    }

    @Override
    public void init() {
        super.init();

        button_cleanse = new ImageButton(this.leftPos + 71, this.topPos + 64, 32, 16,
                0, 0, 16, new ResourceLocation("iter:textures/gui/atlas/gnawer_book.png"),
                32, 32, e -> {
            iterMod.PACKET_HANDLER.sendToServer(new VoidMawPacket(0, x, y, z));
        });

        this.addRenderableWidget(button_cleanse);
    }
}
