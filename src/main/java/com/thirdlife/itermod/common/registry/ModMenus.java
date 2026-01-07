package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.iterMod;
import com.thirdlife.itermod.world.gui.GnawerGuiMenu;
import com.thirdlife.itermod.world.gui.VoidMawGuiMenu;
import com.thirdlife.itermod.world.gui.SpellBookGuiMenu;
import com.thirdlife.itermod.world.gui.SpellweaverTableGuiMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, iterMod.MOD_ID);
    public static final RegistryObject<MenuType<SpellBookGuiMenu>> SPELLBOOK_GUI = REGISTRY.register("spellbook_gui", () -> IForgeMenuType.create(SpellBookGuiMenu::new));
    public static final RegistryObject<MenuType<SpellweaverTableGuiMenu>> SPELLWEAVER_TABLE_GUI = REGISTRY.register("spellweaver_table_gui", () -> IForgeMenuType.create(SpellweaverTableGuiMenu::new));
    public static final RegistryObject<MenuType<GnawerGuiMenu>> GNAWER_GUI = REGISTRY.register("gnawer_gui", () -> IForgeMenuType.create(GnawerGuiMenu::new));
    public static final RegistryObject<MenuType<VoidMawGuiMenu>> VOID_MAW_GUI = REGISTRY.register("void_maw_gui", () -> IForgeMenuType.create(VoidMawGuiMenu::new));
}
