package com.malignant.itermod.common.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.malignant.itermod.common.registry.ModAttributes;
import com.malignant.itermod.common.registry.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public abstract class ApprenticeArmor extends ArmorItem {
    public ApprenticeArmor(ArmorItem.Type type, Item.Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.Type type) {
                return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 9;
            }

            @Override
            public int getDefenseForType(ArmorItem.Type type) {
                return new int[]{1, 2, 2, 1}[type.getSlot().getIndex()];
            }

            @Override
            public int getEnchantmentValue() {
                return 10;
            }

            @Override
            public SoundEvent getEquipSound() {
                return SoundEvents.ARMOR_EQUIP_LEATHER;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(ModItems.ABSTRUSE_CLOTH.get()));
            }

            @Override
            public String getName() {
                return "apprentice";
            }

            @Override
            public float getToughness() {
                return 0f;
            }

            @Override
            public float getKnockbackResistance() {
                return 0f;
            }
        }, type, properties);
    }

    public static class Helmet extends ApprenticeArmor {
        public Helmet() {
            super(ArmorItem.Type.HELMET, new Item.Properties());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "iter:textures/entity/armor/apprentice_robes_layer_1.png";
        }

        @Override
        public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers (@NotNull final EquipmentSlot slot, final ItemStack itemStack)
        {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
            builder.putAll(super.getAttributeModifiers(slot, itemStack));

            if (slot == EquipmentSlot.HEAD)
            {
                builder.put(ModAttributes.ETHER_BURNOUT_THRESHOLD.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_head_threshold"),
                        "Armor modifier", 5, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.ETHER_BURNOUT_DISSIPATION.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_head_dissipation"),
                        "Armor modifier", 0.025, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.SPELL_POWER.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_head_spellpower"),
                            "Armor modifier", 0.125, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.CASTING_SPEED.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_head_casting_speed"),
                        "Armor modifier", 0.01, AttributeModifier.Operation.MULTIPLY_BASE));

                builder.put(ModAttributes.ETHER_EFFICIENCY.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_head_efficiency"),
                        "Armor modifier", 0.0025, AttributeModifier.Operation.MULTIPLY_BASE));

                Map<Enchantment, Integer> itemEnchants = itemStack.getAllEnchantments();
            }
            return builder.build();
        }
    }

    public static class Chestplate extends ApprenticeArmor {
        public Chestplate() {
            super(ArmorItem.Type.CHESTPLATE, new Item.Properties());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "iter:textures/entity/armor/apprentice_robes_layer_1.png";
        }

        @Override
        public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers (@NotNull final EquipmentSlot slot, final ItemStack itemStack)
        {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
            builder.putAll(super.getAttributeModifiers(slot, itemStack));
            if (slot == EquipmentSlot.CHEST)
            {
                builder.put(ModAttributes.ETHER_BURNOUT_THRESHOLD.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_chest_threshold"),
                        "Armor modifier", 10, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.ETHER_BURNOUT_DISSIPATION.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_chest_dissipation"),
                        "Armor modifier", 0.025, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.SPELL_POWER.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_chest_spellpower"),
                        "Armor modifier", 0.125, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.CASTING_SPEED.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_chest_casting_speed"),
                        "Armor modifier", 0.01, AttributeModifier.Operation.MULTIPLY_BASE));

                builder.put(ModAttributes.ETHER_EFFICIENCY.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_chest_efficiency"),
                        "Armor modifier", 0.0025, AttributeModifier.Operation.MULTIPLY_BASE));

                Map<Enchantment, Integer> itemEnchants = itemStack.getAllEnchantments();
            }
            return builder.build();
        }
    }

    public static class Leggings extends ApprenticeArmor {
        public Leggings() {
            super(ArmorItem.Type.LEGGINGS, new Item.Properties());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "iter:textures/entity/armor/apprentice_robes_layer_2.png";
        }

        @Override
        public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers (@NotNull final EquipmentSlot slot, final ItemStack itemStack)
        {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
            builder.putAll(super.getAttributeModifiers(slot, itemStack));
            if (slot == EquipmentSlot.LEGS)
            {
                builder.put(ModAttributes.ETHER_BURNOUT_THRESHOLD.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_legs_threshold"),
                        "Armor modifier", 5, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.ETHER_BURNOUT_DISSIPATION.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_legs_dissipation"),
                        "Armor modifier", 0.025, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.SPELL_POWER.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_legs_spellpower"),
                        "Armor modifier", 0.125, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.CASTING_SPEED.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_legs_casting_speed"),
                        "Armor modifier", 0.01, AttributeModifier.Operation.MULTIPLY_BASE));

                builder.put(ModAttributes.ETHER_EFFICIENCY.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_legs_efficiency"),
                        "Armor modifier", 0.0025, AttributeModifier.Operation.MULTIPLY_BASE));

                Map<Enchantment, Integer> itemEnchants = itemStack.getAllEnchantments();
            }
            return builder.build();
        }
    }

    public static class Boots extends ApprenticeArmor {
        public Boots() {
            super(ArmorItem.Type.BOOTS, new Item.Properties());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "iter:textures/entity/armor/apprentice_robes_layer_1.png";
        }

        @Override
        public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers (@NotNull final EquipmentSlot slot, final ItemStack itemStack)
        {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
            builder.putAll(super.getAttributeModifiers(slot, itemStack));
            if (slot == EquipmentSlot.FEET)
            {
                builder.put(ModAttributes.ETHER_BURNOUT_THRESHOLD.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_feet_threshold"),
                        "Armor modifier", 5, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.ETHER_BURNOUT_DISSIPATION.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_feet_dissipation"),
                        "Armor modifier", 0.025, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.SPELL_POWER.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_feet_spellpower"),
                        "Armor modifier", 0.125, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.CASTING_SPEED.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_feet_casting_speed"),
                        "Armor modifier", 0.01, AttributeModifier.Operation.MULTIPLY_BASE));

                builder.put(ModAttributes.ETHER_EFFICIENCY.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_feet_efficiency"),
                        "Armor modifier", 0.0025, AttributeModifier.Operation.MULTIPLY_BASE));

                Map<Enchantment, Integer> itemEnchants = itemStack.getAllEnchantments();
            }
            return builder.build();
        }
    }
}
