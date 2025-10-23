package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.iterMod;
import com.thirdlife.itermod.world.gui.SpellBookGuiMenu;
import com.thirdlife.itermod.world.gui.SpellbookGuiScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModScreens {
    @SubscribeEvent
    public static void clientLoad(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenus.SPELLBOOK_GUI.get(), SpellbookGuiScreen::new);
        });
    }
}
