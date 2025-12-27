package com.thirdlife.itermod.world.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.thirdlife.itermod.common.variables.IterPlayerDataUtils;
import com.thirdlife.itermod.iterMod;
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
import java.util.Objects;
import java.util.Optional;

public class SpellweaverTableGuiScreen extends AbstractContainerScreen<SpellweaverTableGuiMenu> {
    private final static HashMap<String, Object> guistate = SpellweaverTableGuiMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player player;
    ImageButton imagebutton_switch;
    ImageButton imagebutton_main;

    public SpellweaverTableGuiScreen(SpellweaverTableGuiMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.player = container.entity;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    private boolean clientSwitchState = false;

    private static final ResourceLocation texture = new ResourceLocation("iter:textures/gui/spellweaver_table_gui.png");

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        int areaX = this.leftPos + 153;
        int areaY = this.topPos + 9;
        int areaWidth = 14;
        int areaHeight = 14;

        if (mouseX >= areaX && mouseX <= areaX + areaWidth &&
                mouseY >= areaY && mouseY <= areaY + areaHeight) {
            List<Component> tooltip = SpellweaverTableTooltip.FinalTooltip(player);
            guiGraphics.renderTooltip(this.font,
                    tooltip, Optional.empty(),
                    mouseX, mouseY);
        }

        this.renderTooltip(guiGraphics, mouseX, mouseY);
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
            assert Objects.requireNonNull(this.minecraft).player != null;
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
        guiGraphics.drawString(this.font, Component.translatable("gui.iter.spellweaver_table.label"), 40, 6, -3355444, false);
    }

    @Override
    public void init() {
        super.init();

        boolean currentSwitchState = IterPlayerDataUtils.getSpellweaverSwitch(this.player);

        ResourceLocation switchbuttonlocation = currentSwitchState
                ? new ResourceLocation("iter:textures/gui/atlas/spellweaver_table_switch1.png")
                : new ResourceLocation("iter:textures/gui/atlas/spellweaver_table_switch0.png");

        imagebutton_switch = new ImageButton(this.leftPos + 43, this.topPos + 28, 16, 31,
                0, 0, 31, switchbuttonlocation, 16, 62, e -> {

            boolean newSwitchState = !currentSwitchState;
            iterMod.PACKET_HANDLER.sendToServer(new SpellweaverTablePacket(1, newSwitchState));

        });

        this.addRenderableWidget(imagebutton_switch);
        this.addRenderableWidget(imagebutton_main);
    }

    public void updateSwitchState(boolean state) {
        this.clientSwitchState = state;
    }
}

