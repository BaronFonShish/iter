package com.malignant.iter.world.gui;

import com.malignant.iter.common.payload.SpellweaverTablePayload;
import com.malignant.iter.common.variables.IterPlayerDataUtils;
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

public class SpellweaverTableGuiScreen extends AbstractContainerScreen<SpellweaverTableGuiMenu> {
    private final static HashMap<String, Object> guistate = SpellweaverTableGuiMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player player;
    private ImageButton imagebutton_switch;
    private ImageButton imagebutton_main;
    private boolean SwitchState = false;

    private static final ResourceLocation TEXTURE = ResourceLocation.parse("iter:textures/gui/spellweaver_table_gui.png");

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

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        int areaX = this.leftPos + 153;
        int areaY = this.topPos + 9;
        int areaWidth = 14;
        int areaHeight = 14;

        if (mouseX >= areaX && mouseX <= areaX + areaWidth &&
                mouseY >= areaY && mouseY <= areaY + areaHeight) {
            List<Component> tooltip = GuiTooltips.SpellweaverFinalTooltip(player);
            guiGraphics.renderTooltip(this.font,
                    tooltip, Optional.empty(),
                    mouseX, mouseY);
        }

        this.renderTooltip(guiGraphics, mouseX, mouseY);
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

        boolean newSwitchState = IterPlayerDataUtils.getSpellweaverSwitch(this.player);
        if (newSwitchState != SwitchState) {
            SwitchState = newSwitchState;
            updateSwitchButton();
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, Component.translatable("gui.iter.spellweaver_table.label"), 40, 6, -3355444, false);
    }

    @Override
    public void init() {
        super.init();

        SwitchState = IterPlayerDataUtils.getSpellweaverSwitch(this.player);
        createButtons();
    }

    private void createButtons() {
        if (imagebutton_switch != null) {
            this.removeWidget(imagebutton_switch);
        }
        if (imagebutton_main != null) {
            this.removeWidget(imagebutton_main);
        }

        ResourceLocation switchTexture = SwitchState
                ? ResourceLocation.parse("iter:textures/gui/atlas/spellweaver_table_switch1.png")
                : ResourceLocation.parse("iter:textures/gui/atlas/spellweaver_table_switch0.png");

        WidgetSprites switchSprites = new WidgetSprites(switchTexture, switchTexture);

        imagebutton_switch = new ImageButton(
                this.leftPos + 43,
                this.topPos + 28,
                16,
                31,
                switchSprites,
                e -> {
                    boolean newSwitchState = !SwitchState;
                    PacketDistributor.sendToServer(new SpellweaverTablePayload(1, newSwitchState));
                    SwitchState = newSwitchState;
                    updateSwitchButton();
                }
        );

        WidgetSprites mainSprites = new WidgetSprites(
                ResourceLocation.parse("iter:textures/gui/atlas/spellweaver_table_write.png"),
                ResourceLocation.parse("iter:textures/gui/atlas/spellweaver_table_write_hovered.png")
        );

        imagebutton_main = new ImageButton(
                this.leftPos + 71,
                this.topPos + 64,
                32,
                16,
                mainSprites,
                e -> {
                    PacketDistributor.sendToServer(new SpellweaverTablePayload(0));
                }
        );

        this.addRenderableWidget(imagebutton_switch);
        this.addRenderableWidget(imagebutton_main);
    }

    private void updateSwitchButton() {
        if (imagebutton_switch != null) {
            this.removeWidget(imagebutton_switch);

            ResourceLocation switchTexture = SwitchState
                    ? ResourceLocation.parse("iter:textures/gui/atlas/spellweaver_table_switch1.png")
                    : ResourceLocation.parse("iter:textures/gui/atlas/spellweaver_table_switch0.png");

            WidgetSprites switchSprites = new WidgetSprites(switchTexture, switchTexture);

            imagebutton_switch = new ImageButton(
                    imagebutton_switch.getX(),
                    imagebutton_switch.getY(),
                    imagebutton_switch.getWidth(),
                    imagebutton_switch.getHeight(),
                    switchSprites,
                    e -> {
                        boolean newSwitchState = !SwitchState;
                        PacketDistributor.sendToServer(new SpellweaverTablePayload(1, newSwitchState));
                        SwitchState = newSwitchState;
                        updateSwitchButton();
                    }
            );

            this.addRenderableWidget(imagebutton_switch);
        }
    }
}