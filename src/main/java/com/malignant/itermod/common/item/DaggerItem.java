package com.malignant.itermod.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.malignant.itermod.common.enchantment.FlayingEnchantment;
import com.malignant.itermod.common.registry.ModEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class DaggerItem extends TieredItem {


    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return ((enchantment.category == EnchantmentCategory.WEAPON) && (enchantment != Enchantments.SWEEPING_EDGE))|| super.canApplyAtEnchantingTable(stack, enchantment);
    }


    public DaggerItem(Tiers tier, Properties properties) {
        super(tier, properties.durability((int)(tier.getUses() * 0.95)));
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });

        int Flaying = pStack.getEnchantmentLevel(ModEnchantments.FLAYING.get());
        if (Flaying > 0){
            String flayingUUID = pStack.getOrCreateTag().getString("flayingUUID");
            int flayingStack = pStack.getOrCreateTag().getInt("flayingStack");
            if (pTarget.getStringUUID().equals(flayingUUID)){
                pTarget.hurt(pTarget.damageSources().magic(), (float) flayingStack);
                if ((FlayingEnchantment.getMaxStacks(Flaying)) > flayingStack){
                    pStack.getOrCreateTag().putInt("flayingStack", flayingStack+1);
                }
            } else {
                pStack.getOrCreateTag().putInt("flayingStack", 0);
                pStack.getOrCreateTag().putString("flayingUUID", pTarget.getStringUUID());
            }
        }
        return true;
    }

    public static final String ReachUUID = "c38ac4f4-d813-4ba7-a986-07c164f89266";

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers (@NotNull final EquipmentSlot slot, final ItemStack itemStack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        if (slot == EquipmentSlot.MAINHAND)
        {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_UUID, "Weapon modifier",
                    this.getTier().getAttackDamageBonus() * 0.5f + 1,
                    AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2d, AttributeModifier.Operation.ADDITION));
            builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.fromString(ReachUUID), "Weapon modifier", -0.5d, AttributeModifier.Operation.ADDITION));

            Map<Enchantment, Integer> itemEnchants = itemStack.getAllEnchantments();
        }

        return builder.build();
    }

    @Override
    public boolean canAttackBlock (@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player pPlayer)
    {
        return !pPlayer.isCreative();
    }
}