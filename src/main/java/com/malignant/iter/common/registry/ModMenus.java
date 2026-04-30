package com.malignant.iter.common.registry;

import com.malignant.iter.IterMod;
import com.malignant.iter.world.gui.GnawerGuiMenu;
import com.malignant.iter.world.gui.VoidMawGuiMenu;
import com.malignant.iter.world.gui.SpellBookGuiMenu;
import com.malignant.iter.world.gui.SpellweaverTableGuiMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, IterMod.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<SpellBookGuiMenu>> SPELLBOOK_GUI =
            REGISTRY.register("spellbook_gui", () -> IMenuTypeExtension.create(SpellBookGuiMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<SpellweaverTableGuiMenu>> SPELLWEAVER_TABLE_GUI =
            REGISTRY.register("spellweaver_table_gui", () -> IMenuTypeExtension.create(SpellweaverTableGuiMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<GnawerGuiMenu>> GNAWER_GUI =
            REGISTRY.register("gnawer_gui", () -> IMenuTypeExtension.create(GnawerGuiMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<VoidMawGuiMenu>> VOID_MAW_GUI =
            REGISTRY.register("void_maw_gui", () -> IMenuTypeExtension.create(VoidMawGuiMenu::new));
}
