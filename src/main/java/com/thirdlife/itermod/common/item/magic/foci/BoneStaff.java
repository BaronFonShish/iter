package com.thirdlife.itermod.common.item.magic.foci;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.thirdlife.itermod.common.item.magic.defaults.SpellFocus;
import com.thirdlife.itermod.common.registry.ModAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class BoneStaff extends SpellFocus {

    public BoneStaff() {
        super(new SpellFocusProperties()
                .durability(144)
                .rarity(Rarity.COMMON)
                .enchantability(12),
                4,
                0,
                0
        );
    }

    @Override
    public int getEnchantmentValue() {
        return 12;
    }

}
