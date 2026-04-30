package com.malignant.iter.common.item;

import com.malignant.iter.common.registry.ModDataComponents;
import com.malignant.iter.common.registry.ModEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;


public class DaggerItem extends TieredItem {


    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return super.supportsEnchantment(stack, enchantment) && !enchantment.is(Enchantments.SWEEPING_EDGE);
    }


    public DaggerItem(Tier tier, Properties properties) {
        super(tier, properties.durability((int) (tier.getUses() * 0.95))
                .attributes(DaggerItem.createAttributes(tier)));
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, EquipmentSlot.MAINHAND);

        int Flaying = pStack.getEnchantmentLevel(ModEnchantments.getHolder(ModEnchantments.FLAYING));
        if (Flaying > 0){
            String flayingUUID = pStack.get(ModDataComponents.FLAYING_UUID.get());
            int flayingStack = pStack.getOrDefault(ModDataComponents.FLAYING_STACK.get(), 0);
            if (pTarget.getStringUUID().equals(flayingUUID)){
                pTarget.hurt(pTarget.damageSources().magic(), (float) flayingStack);
                if (4 + (pStack.getEnchantmentLevel(ModEnchantments.getHolder(ModEnchantments.FLAYING))*2) > flayingStack){
                    pStack.set(ModDataComponents.FLAYING_STACK.get(), flayingStack + 1);
                }
            } else {
                pStack.set(ModDataComponents.FLAYING_STACK, 0);
                pStack.set(ModDataComponents.FLAYING_UUID, pTarget.getStringUUID());
            }
        }
        return true;
    }

    public static ItemAttributeModifiers createAttributes(Tier tier) {

        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                        tier.getAttackDamageBonus() * 0.5f + 1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)

                .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID,
                        -2d, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)

                .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(ResourceLocation.parse("iter:attack_range"),
                        -0.5d, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    @Override
    public boolean canAttackBlock (@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player pPlayer)
    {
        return !pPlayer.isCreative();
    }
}