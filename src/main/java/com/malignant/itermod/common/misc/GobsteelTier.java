package com.malignant.itermod.common.misc;

import com.malignant.itermod.common.registry.ModItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class GobsteelTier implements Tier {

    public static final GobsteelTier INSTANCE = new GobsteelTier();

    @Override
    public int getUses() {
        return 215;
    }

    @Override
    public float getSpeed() {
        return 5.0F;
    }

    @Override
    public float getAttackDamageBonus() {
        return 2F;
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public int getEnchantmentValue() {
        return 8;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return Ingredient.of(ModItems.GOBSTEEL_SCRAP.get());
    }
}