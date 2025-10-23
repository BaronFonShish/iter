package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.common.enchantment.FlayingEnchantment;
import com.thirdlife.itermod.common.enchantment.SowingEnchantment;
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
}
