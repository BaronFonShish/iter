package com.thirdlife.itermod.common.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.thirdlife.itermod.common.registry.ModAttributes;
import com.thirdlife.itermod.common.registry.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public abstract class AzureArmor extends ArmorItem {
    public AzureArmor(Type type, Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(Type type) {
                return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 12;
            }

            @Override
            public int getDefenseForType(Type type) {
                return new int[]{2, 2, 3, 2}[type.getSlot().getIndex()];
            }

            @Override
            public int getEnchantmentValue() {
                return 15;
            }

            @Override
            public SoundEvent getEquipSound() {
                return SoundEvents.ARMOR_EQUIP_LEATHER;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(ModItems.SPIDER_SILK.get()));
            }

            @Override
            public String getName() {
                return "azure";
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

    public static class Helmet extends AzureArmor {
        public Helmet() {
            super(Type.HELMET, new Properties());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "iter:textures/entity/armor/azure_mantle_layer_1.png";
        }

        @Override
        public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers (@NotNull final EquipmentSlot slot, final ItemStack itemStack)
        {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
            builder.putAll(super.getAttributeModifiers(slot, itemStack));
            if (slot == EquipmentSlot.HEAD)
            {
                builder.put(ModAttributes.ETHER_BURNOUT_THRESHOLD.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_head_threshold"),
                        "Armor modifier", 12, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.ETHER_BURNOUT_DISSIPATION.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_head_dissipation"),
                        "Armor modifier", 0.05, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.SPELL_POWER.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_head_spellpower"),
                        "Armor modifier", 0.25, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.CASTING_SPEED.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_head_casting_speed"),
                        "Armor modifier", 0.015, AttributeModifier.Operation.MULTIPLY_BASE));

                builder.put(ModAttributes.ETHER_EFFICIENCY.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_head_efficiency"),
                        "Armor modifier", 0.00625, AttributeModifier.Operation.MULTIPLY_BASE));

                Map<Enchantment, Integer> itemEnchants = itemStack.getAllEnchantments();
            }
            return builder.build();
        }
    }

    public static class Chestplate extends AzureArmor {
        public Chestplate() {
            super(Type.CHESTPLATE, new Properties());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "iter:textures/entity/armor/azure_mantle_layer_1.png";
        }

        @Override
        public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers (@NotNull final EquipmentSlot slot, final ItemStack itemStack)
        {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
            builder.putAll(super.getAttributeModifiers(slot, itemStack));
            if (slot == EquipmentSlot.CHEST)
            {
                builder.put(ModAttributes.ETHER_BURNOUT_THRESHOLD.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_chest_threshold"),
                        "Armor modifier", 14, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.ETHER_BURNOUT_DISSIPATION.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_chest_dissipation"),
                        "Armor modifier", 0.05, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.SPELL_POWER.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_chest_spellpower"),
                        "Armor modifier", 0.25, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.CASTING_SPEED.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_chest_casting_speed"),
                        "Armor modifier", 0.015, AttributeModifier.Operation.MULTIPLY_BASE));

                builder.put(ModAttributes.ETHER_EFFICIENCY.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_chest_efficiency"),
                        "Armor modifier", 0.00625, AttributeModifier.Operation.MULTIPLY_BASE));

                Map<Enchantment, Integer> itemEnchants = itemStack.getAllEnchantments();
            }
            return builder.build();
        }
    }

    public static class Leggings extends AzureArmor {
        public Leggings() {
            super(Type.LEGGINGS, new Properties());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "iter:textures/entity/armor/azure_mantle_layer_2.png";
        }

        @Override
        public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers (@NotNull final EquipmentSlot slot, final ItemStack itemStack)
        {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
            builder.putAll(super.getAttributeModifiers(slot, itemStack));
            if (slot == EquipmentSlot.LEGS)
            {
                builder.put(ModAttributes.ETHER_BURNOUT_THRESHOLD.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_legs_threshold"),
                        "Armor modifier", 12, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.ETHER_BURNOUT_DISSIPATION.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_legs_dissipation"),
                        "Armor modifier", 0.05, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.SPELL_POWER.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_legs_spellpower"),
                        "Armor modifier", 0.25, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.CASTING_SPEED.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_legs_casting_speed"),
                        "Armor modifier", 0.015, AttributeModifier.Operation.MULTIPLY_BASE));

                builder.put(ModAttributes.ETHER_EFFICIENCY.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_legs_efficiency"),
                        "Armor modifier", 0.00625, AttributeModifier.Operation.MULTIPLY_BASE));

                Map<Enchantment, Integer> itemEnchants = itemStack.getAllEnchantments();
            }
            return builder.build();
        }
    }

    public static class Boots extends AzureArmor {
        public Boots() {
            super(Type.BOOTS, new Properties());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "iter:textures/entity/armor/azure_mantle_layer_1.png";
        }

        @Override
        public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers (@NotNull final EquipmentSlot slot, final ItemStack itemStack)
        {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
            builder.putAll(super.getAttributeModifiers(slot, itemStack));
            if (slot == EquipmentSlot.FEET)
            {
                builder.put(ModAttributes.ETHER_BURNOUT_THRESHOLD.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_feet_threshold"),
                        "Armor modifier", 12, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.ETHER_BURNOUT_DISSIPATION.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_feet_dissipation"),
                        "Armor modifier", 0.05, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.SPELL_POWER.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_feet_spellpower"),
                        "Armor modifier", 0.25, AttributeModifier.Operation.ADDITION));

                builder.put(ModAttributes.CASTING_SPEED.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_feet_casting_speed"),
                        "Armor modifier", 0.015, AttributeModifier.Operation.MULTIPLY_BASE));

                builder.put(ModAttributes.ETHER_EFFICIENCY.get(), new AttributeModifier(AttributeUUIDStorage.provide("iter_feet_efficiency"),
                        "Armor modifier", 0.00625, AttributeModifier.Operation.MULTIPLY_BASE));

                Map<Enchantment, Integer> itemEnchants = itemStack.getAllEnchantments();
            }
            return builder.build();
        }
    }
}
