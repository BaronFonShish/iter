package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.world.gui.*;
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
