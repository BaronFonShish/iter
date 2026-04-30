package com.malignant.iter.common.registry;

import com.malignant.iter.IterMod;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> SOWING =
            ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, "sowing"));
    public static final ResourceKey<Enchantment> FLAYING =
            ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, "flaying"));
    public static final ResourceKey<Enchantment> AUTOMATIC =
            ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, "automatic"));
    public static final ResourceKey<Enchantment> ATTUNEMENT =
            ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, "attunement"));
    public static final ResourceKey<Enchantment> DEXTERITY =
            ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, "dexterity"));
    public static final ResourceKey<Enchantment> RIGOUR =
            ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, "rigour"));

    public static Holder<Enchantment> getHolder(ResourceKey<Enchantment> enchantment) {
        return IterMod.registryAccess.registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(enchantment);
    }
}