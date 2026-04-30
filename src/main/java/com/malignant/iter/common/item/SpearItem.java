package com.malignant.iter.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SpearItem extends TieredItem {

    public SpearItem(Tiers tier, Properties properties) {
        super(tier, properties.durability((int)(tier.getUses() * 1.25)).attributes(SpearItem.createAttributes(tier)));
    }


    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return super.supportsEnchantment(stack, enchantment) && !enchantment.is(Enchantments.SWEEPING_EDGE);
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, EquipmentSlot.MAINHAND);
        return true;
    }

    public static ItemAttributeModifiers createAttributes(Tier tier) {

        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                        (tier.getAttackDamageBonus() + 3), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)

                .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID,
                        -2.7d, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)

                .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(ResourceLocation.parse("iter:attack_range"),
                        1.5d, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    @Override
    public boolean canAttackBlock (@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player pPlayer)
    {
        return !pPlayer.isCreative();
    }
}