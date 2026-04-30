package com.malignant.iter.common.item;

import com.malignant.iter.common.registry.ModDataComponents;
import com.malignant.iter.common.registry.ModEnchantments;
import com.malignant.iter.common.registry.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

public class Bloodletter extends DaggerItem {

    public Bloodletter(Properties properties) {
        super(Tiers.IRON, properties.durability((int) (Tiers.IRON.getUses() * 1.15)));
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, EquipmentSlot.MAINHAND);

        int flaying = pStack.getEnchantmentLevel((Holder<Enchantment>) ModEnchantments.FLAYING);
        if (flaying > 0) {
            String flayingUUID = pStack.get(ModDataComponents.FLAYING_UUID);
            int flayingStack = pStack.getOrDefault(ModDataComponents.FLAYING_STACK, 0);

            if (pTarget.getStringUUID().equals(flayingUUID)) {
                pTarget.hurt(pTarget.damageSources().magic(), (float) flayingStack);
                int maxStacks = 4 + (flaying * 2);
                if (maxStacks > flayingStack) {
                    pStack.set(ModDataComponents.FLAYING_STACK, flayingStack + 1);
                }
            } else {
                pStack.set(ModDataComponents.FLAYING_STACK, 0);
                pStack.set(ModDataComponents.FLAYING_UUID, pTarget.getStringUUID());
            }
        }

        if (pAttacker instanceof Player player) {
            if (player.getOffhandItem().getItem() == Items.GLASS_BOTTLE &&
                    pTarget.getHealth() < pTarget.getMaxHealth() / 2 &&
                    ((pTarget instanceof Animal) || (pTarget instanceof Villager))) {
                player.getOffhandItem().shrink(1);
                Inventory playerinv = player.getInventory();
                playerinv.add(ModItems.BLOOD_BOTTLE.get().getDefaultInstance());
                pTarget.hurt(pTarget.damageSources().generic(), 5);
            }
        }
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (player.getHealth() >= 16 && player.getOffhandItem().getItem() == Items.GLASS_BOTTLE) {
            player.swing(hand, true);

            if (player instanceof ServerPlayer serverPlayer) {
                if (serverPlayer.gameMode.getGameModeForPlayer() != GameType.CREATIVE) {
                    player.getCooldowns().addCooldown(this, 100);
                    player.getOffhandItem().shrink(1);
                    itemstack.hurtAndBreak(2, player, EquipmentSlot.MAINHAND);
                    player.hurt(player.damageSources().generic(), 14);
                }
            }

            Inventory playerinv = player.getInventory();
            playerinv.add(ModItems.BLOOD_BOTTLE.get().getDefaultInstance());
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID,
                                Tiers.IRON.getAttackDamageBonus() * 0.5f + 1,
                                AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID,
                                -2.2,
                                AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ENTITY_INTERACTION_RANGE,
                        new AttributeModifier(ResourceLocation.parse("iter:bloodletter_reach"),
                                -0.5,
                                AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }
}