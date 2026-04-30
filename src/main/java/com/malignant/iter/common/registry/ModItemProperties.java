package com.malignant.iter.common.registry;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItemProperties {

    public static void registerItemProperties() {
        registerBowProperties(ModItems.RECURVE_BOW.get());
        registerBowProperties(ModItems.STINGER.get());
        registerBowProperties(ModItems.NETHERITE_WARBOW.get());
    }

    private static void registerBowProperties(Item item) {
        ItemProperties.register(item, ResourceLocation.parse("pull"), (itemStack, clientLevel, livingEntity, seed) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return livingEntity.getUseItem() != itemStack ? 0.0F :
                        (float) (itemStack.getUseDuration(livingEntity) - livingEntity.getUseItemRemainingTicks()) / 20.0F;
            }
        });

        ItemProperties.register(item, ResourceLocation.parse("pulling"), (itemStack, clientLevel, livingEntity, seed) -> {
            return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F;
        });
    }
}