package com.malignant.iter.common.item.armor;

import com.malignant.iter.IterMod;
import com.malignant.iter.common.registry.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;


public class ModArmorMaterials {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, IterMod.MOD_ID);

    public static final Holder<ArmorMaterial> APPRENTICE = ARMOR_MATERIALS.register("apprentice",
            () -> new ArmorMaterial(
                    createApprenticeDefenseMap(),
                    10,
                    SoundEvents.ARMOR_EQUIP_LEATHER,
                    () -> Ingredient.of(ModItems.ABSTRUSE_CLOTH.get()),
                    List.of(
                            new ArmorMaterial.Layer(
                                    ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, "apprentice"),
                                    "_layer_1",
                                    false
                            )
                    ),
                    0.0f,
                    0.0f
            ));

    private static EnumMap<ArmorItem.Type, Integer> createApprenticeDefenseMap() {
        EnumMap<ArmorItem.Type, Integer> defense = new EnumMap<>(ArmorItem.Type.class);
        defense.put(ArmorItem.Type.BOOTS, 1);
        defense.put(ArmorItem.Type.LEGGINGS, 2);
        defense.put(ArmorItem.Type.CHESTPLATE, 2);
        defense.put(ArmorItem.Type.HELMET, 1);
        defense.put(ArmorItem.Type.BODY, 2);
        return defense;
    }

    public static final Holder<ArmorMaterial> AZURE = ARMOR_MATERIALS.register("azure",
            () -> new ArmorMaterial(
                    createAzureDefenseMap(),
                    15,
                    SoundEvents.ARMOR_EQUIP_LEATHER,
                    () -> Ingredient.of(ModItems.SPIDER_SILK.get()),
                    List.of(
                            new ArmorMaterial.Layer(
                                    ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, "azure_mantle"),
                                    "_layer_1",
                                    false
                            ),
                            new ArmorMaterial.Layer(
                                    ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, "azure_mantle"),
                                    "_layer_2",
                                    false
                            )
                    ),
                    0.0f,
                    0.0f
            ));

    private static EnumMap<ArmorItem.Type, Integer> createAzureDefenseMap() {
        EnumMap<ArmorItem.Type, Integer> defense = new EnumMap<>(ArmorItem.Type.class);
        defense.put(ArmorItem.Type.BOOTS, 2);
        defense.put(ArmorItem.Type.LEGGINGS, 2);
        defense.put(ArmorItem.Type.CHESTPLATE, 3);
        defense.put(ArmorItem.Type.HELMET, 2);
        defense.put(ArmorItem.Type.BODY, 3);
        return defense;
    }
}