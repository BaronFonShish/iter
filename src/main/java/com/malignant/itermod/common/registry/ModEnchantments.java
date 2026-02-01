package com.malignant.itermod.common.registry;

import com.malignant.itermod.common.enchantment.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "iter");

    public static final RegistryObject<Enchantment> SOWING = ENCHANTMENTS.register("sowing",
            () -> new SowingEnchantment(EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> FLAYING = ENCHANTMENTS.register("flaying",
            () -> new FlayingEnchantment(EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> AUTOMATIC = ENCHANTMENTS.register("automatic",
            () -> new AutomaticEnchantment(EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> ATTUNEMENT = ENCHANTMENTS.register("attunement",
            () -> new AttunementEnchantment(EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> DEXTERITY = ENCHANTMENTS.register("dexterity",
            () -> new DexterityEnchantment(EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> RIGOUR = ENCHANTMENTS.register("rigour",
            () -> new RigourEnchantment(EquipmentSlot.MAINHAND));
}
