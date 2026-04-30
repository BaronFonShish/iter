package com.malignant.iter.common.event;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Objects;
import java.util.UUID;

public class RingEffectManager {

    public static void tieredRingStuff(String id, Attribute attribute,
                                       Item ironTier, Item goldenTier, Item netheriteTier,
                                       float t1, float t2, float t3, Entity entity, int operationType) {
        float tierMod = 0f;

        if (entity instanceof LivingEntity livingEntity) {
            if (CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, netheriteTier).isPresent()) {
                tierMod = t3;
            } else if (CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, goldenTier).isPresent()) {
                tierMod = t2;
            } else if (CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ironTier).isPresent()) {
                tierMod = t1;
            }

            var attributeInstance = livingEntity.getAttribute((Holder<Attribute>) attribute);
            if (attributeInstance != null) {
                attributeInstance.removeModifier(ResourceLocation.parse(id));

                if (tierMod > 0) {
                    AttributeModifier.Operation operation = switch (operationType) {
                        case 1 -> AttributeModifier.Operation.ADD_VALUE;
                        case 2 -> AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
                        default -> AttributeModifier.Operation.ADD_VALUE;
                    };
                    attributeInstance.addTransientModifier(new AttributeModifier(ResourceLocation.parse(id), tierMod, operation));
                }
            }
        }
    }
}