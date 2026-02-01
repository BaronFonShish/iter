package com.malignant.itermod.common.item;

import com.malignant.itermod.common.registry.ModItems;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.Nullable;

public class MagmanumItem extends Item {

    public MagmanumItem(Properties pProperties) {
        super(pProperties.rarity(Rarity.UNCOMMON).fireResistant());
    }


    @Override
    public boolean canBeHurtBy(DamageSource damageSource) {
        return !damageSource.is(DamageTypes.EXPLOSION) &&
                !damageSource.is(DamageTypeTags.IS_FIRE);
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType)
    {
        return 6400;
    }

    @SubscribeEvent
    public static void furnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event) {
        ItemStack itemstack = event.getItemStack();
        if (itemstack.getItem() == ModItems.MAGMANUM.get())
            event.setBurnTime(6400);

    }
}