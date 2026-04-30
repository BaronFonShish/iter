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

public class FlailItem extends TieredItem {

    public FlailItem(Tiers tier, Properties properties) {
        super(tier, properties.durability((int)(tier.getUses() * 1.5)).attributes(FlailItem.createAttributes(tier)));
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return super.supportsEnchantment(stack, enchantment) && !enchantment.is(Enchantments.SWEEPING_EDGE) && !enchantment.is(Enchantments.SHARPNESS);
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, EquipmentSlot.MAINHAND);
        return true;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !ItemStack.isSameItem(oldStack, newStack) || slotChanged;
    }

    public static ItemAttributeModifiers createAttributes(Tier tier) {

        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                        (((tier.getAttackDamageBonus() + 3) * 2) + 1), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)

                .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID,
                        -3.4d, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)

                .add(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ResourceLocation.parse("iter:knockback"),
                        0.5d, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    @Override
    public boolean canAttackBlock (@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player pPlayer)
    {
        return !pPlayer.isCreative();
    }
}