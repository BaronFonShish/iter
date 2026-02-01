package com.malignant.itermod.common.registry;

import com.malignant.itermod.world.gui.GnawerGuiScreen;
import com.malignant.itermod.world.gui.SpellbookGuiScreen;
import com.malignant.itermod.world.gui.SpellweaverTableGuiScreen;
import com.malignant.itermod.world.gui.VoidMawGuiScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ModScreens {
    @SubscribeEvent
    public static void clientLoad(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenus.SPELLBOOK_GUI.get(), SpellbookGuiScreen::new);
            MenuScreens.register(ModMenus.SPELLWEAVER_TABLE_GUI.get(), SpellweaverTableGuiScreen::new);
            MenuScreens.register(ModMenus.GNAWER_GUI.get(), GnawerGuiScreen::new);
            MenuScreens.register(ModMenus.VOID_MAW_GUI.get(), VoidMawGuiScreen::new);
        });
    }
}
