package com.thirdlife.itermod.common.event;


import com.thirdlife.itermod.common.registry.ModTags;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;

public class DisplaySpellcastTier {
    public static String execute(ItemStack itemstack) {
        if (itemstack.is(ModTags.Items.SPELLCAST_TIER_PRIMITIVE)) {
            return Component.translatable("iterpg.spell.level.tier").getString() + "" + Component.translatable("iter.spell.level.primitive").getString();
        }
        else if (itemstack.is(ModTags.Items.SPELLCAST_TIER_NOVICE)) {
            return Component.translatable("iterpg.spell.level.tier").getString() + "" + Component.translatable("iter.spell.level.novice").getString();
        }
        else if (itemstack.is(ModTags.Items.SPELLCAST_TIER_ADVANCED)) {
            return Component.translatable("iterpg.spell.level.tier").getString() + "" + Component.translatable("iter.spell.level.advanced").getString();
        }
        else if (itemstack.is(ModTags.Items.SPELLCAST_TIER_EXPERT)) {
            return Component.translatable("iterpg.spell.level.tier").getString() + "" + Component.translatable("iter.spell.level.expert").getString();
        }
        else if (itemstack.is(ModTags.Items.SPELLCAST_TIER_FABLED)) {
            return Component.translatable("iterpg.spell.level.tier").getString() + "" + Component.translatable("iter.spell.level.fabled").getString();
        }
        return Component.translatable("iterpg.spell.level.tier").getString() + "" + Component.translatable("iter.spell.level.primitive").getString();
    }
}