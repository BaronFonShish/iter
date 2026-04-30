package com.malignant.iter.common.misc;

import com.malignant.iter.common.registry.ModItems;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
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
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return null;
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