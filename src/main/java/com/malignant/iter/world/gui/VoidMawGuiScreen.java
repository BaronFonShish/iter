package com.malignant.iter.world.gui;

import com.malignant.iter.common.payload.VoidMawPayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class VoidMawGuiScreen extends AbstractContainerScreen<VoidMawGuiMenu> {
    private final static HashMap<String, Object> guistate = VoidMawGuiMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    private ImageButton button_cleanse;

    private static final ResourceLocation TEXTURE = ResourceLocation.parse("iter:textures/gui/void_maw_gui.png");

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

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
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
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            Objects.requireNonNull(this.minecraft).player.closeContainer();
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

        // Create cleanse button with WidgetSprites
        WidgetSprites buttonSprites = new WidgetSprites(
                ResourceLocation.parse("iter:textures/gui/atlas/void_maw_button.png"),
                ResourceLocation.parse("iter:textures/gui/atlas/void_maw_button_hovered.png")
        );

        button_cleanse = new ImageButton(
                this.leftPos + 71,
                this.topPos + 64,
                32,
                16,
                buttonSprites,
                e -> {
                    PacketDistributor.sendToServer(new VoidMawPayload(0, x, y, z));
                }
        );

        this.addRenderableWidget(button_cleanse);
    }
}