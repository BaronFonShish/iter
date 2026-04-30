package com.malignant.iter.common.registry;
import com.malignant.iter.world.gui.*;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class ModScreens {
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.SPELLBOOK_GUI.get(), SpellbookGuiScreen::new);
        event.register(ModMenus.SPELLWEAVER_TABLE_GUI.get(), SpellweaverTableGuiScreen::new);
        event.register(ModMenus.GNAWER_GUI.get(), GnawerGuiScreen::new);
        event.register(ModMenus.VOID_MAW_GUI.get(), VoidMawGuiScreen::new);
    }
}
