package com.malignant.iter.common.item.armor;

import com.malignant.iter.common.registry.ModAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public abstract class AzureArmor extends ArmorItem {

    public AzureArmor(Type type, Properties properties) {
        super(ModArmorMaterials.AZURE, type, properties);
    }

    public static class Helmet extends AzureArmor {
        public Helmet() {
            super(Type.HELMET, new Properties());
        }

        @Override
        public ItemAttributeModifiers getDefaultAttributeModifiers() {
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

            builder.add(ModAttributes.ETHER_BURNOUT_THRESHOLD,
                    new AttributeModifier(ResourceLocation.parse("iter:azure_head_threshold"),
                            12, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.HEAD);

            builder.add(ModAttributes.ETHER_BURNOUT_DISSIPATION,
                    new AttributeModifier(ResourceLocation.parse("iter:azure_head_dissipation"),
                            0.05, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.HEAD);

            builder.add(ModAttributes.SPELL_POWER,
                    new AttributeModifier(ResourceLocation.parse("iter:azure_head_spellpower"),
                            0.25, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.HEAD);

            builder.add(ModAttributes.CASTING_SPEED,
                    new AttributeModifier(ResourceLocation.parse("iter:azure_head_casting_speed"),
                            0.015, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    EquipmentSlotGroup.HEAD);

            builder.add(ModAttributes.ETHER_EFFICIENCY,
                    new AttributeModifier(ResourceLocation.parse("iter:azure_head_efficiency"),
                            0.00625, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    EquipmentSlotGroup.HEAD);

            return builder.build();
        }
    }

    public static class Chestplate extends AzureArmor {
        public Chestplate() {
            super(Type.CHESTPLATE, new Properties());
        }

        @Override
        public ItemAttributeModifiers getDefaultAttributeModifiers() {
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

            builder.add(ModAttributes.ETHER_BURNOUT_THRESHOLD,
                    new AttributeModifier(ResourceLocation.parse("iter:azure_chest_threshold"),
                            14, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.CHEST);

            builder.add(ModAttributes.ETHER_BURNOUT_DISSIPATION,
                    new AttributeModifier(ResourceLocation.parse("iter:azure_chest_dissipation"),
                            0.05, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.CHEST);

            builder.add(ModAttributes.SPELL_POWER,
                    new AttributeModifier(ResourceLocation.parse("iter:azure_chest_spellpower"),
                            0.25, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.CHEST);

            builder.add(ModAttributes.CASTING_SPEED,
                    new AttributeModifier(ResourceLocation.parse("iter:azure_chest_casting_speed"),
                            0.015, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    EquipmentSlotGroup.CHEST);

            builder.add(ModAttributes.ETHER_EFFICIENCY,
                    new AttributeModifier(ResourceLocation.parse("iter:azure_chest_efficiency"),
                            0.00625, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    EquipmentSlotGroup.CHEST);

            return builder.build();
        }
    }

    public static class Leggings extends AzureArmor {
        public Leggings() {
            super(Type.LEGGINGS, new Properties());
        }

        @Override
        public ItemAttributeModifiers getDefaultAttributeModifiers() {
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

            builder.add(ModAttributes.ETHER_BURNOUT_THRESHOLD,
                    new AttributeModifier(ResourceLocation.parse("iter:azure_legs_threshold"),
                            12, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.LEGS);

            builder.add(ModAttributes.ETHER_BURNOUT_DISSIPATION,
                    new AttributeModifier(ResourceLocation.parse("iter:azure_legs_dissipation"),
                            0.05, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.LEGS);

            builder.add(ModAttributes.SPELL_POWER,
                    new AttributeModifier(ResourceLocation.parse("iter:azure_legs_spellpower"),
                            0.25, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.LEGS);

            builder.add(ModAttributes.CASTING_SPEED,
                    new AttributeModifier(ResourceLocation.parse("iter:azure_legs_casting_speed"),
                            0.015, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    EquipmentSlotGroup.LEGS);

            builder.add(ModAttributes.ETHER_EFFICIENCY,
                    new AttributeModifier(ResourceLocation.parse("iter:azure_legs_efficiency"),
                            0.00625, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    EquipmentSlotGroup.LEGS);

            return builder.build();
        }
    }

    public static class Boots extends AzureArmor {
        public Boots() {
            super(Type.BOOTS, new Properties());
        }

        @Override
        public ItemAttributeModifiers getDefaultAttributeModifiers() {
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

            builder.add(ModAttributes.ETHER_BURNOUT_THRESHOLD,
                    new AttributeModifier(ResourceLocation.parse("iter:azure_feet_threshold"),
                            12, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.FEET);

            builder.add(ModAttributes.ETHER_BURNOUT_DISSIPATION,
                    new AttributeModifier(ResourceLocation.parse("iter:azure_feet_dissipation"),
                            0.05, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.FEET);

            builder.add(ModAttributes.SPELL_POWER,
                    new AttributeModifier(ResourceLocation.parse("iter:azure_feet_spellpower"),
                            0.25, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.FEET);

            builder.add(ModAttributes.CASTING_SPEED,
                    new AttributeModifier(ResourceLocation.parse("iter:azure_feet_casting_speed"),
                            0.015, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    EquipmentSlotGroup.FEET);

            builder.add(ModAttributes.ETHER_EFFICIENCY,
                    new AttributeModifier(ResourceLocation.parse("iter:azure_feet_efficiency"),
                            0.00625, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    EquipmentSlotGroup.FEET);

            return builder.build();
        }
    }
}