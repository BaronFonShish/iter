package com.thirdlife.itermod.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ScytheItem extends TieredItem {

    public ScytheItem(Tiers tier, Properties properties) {
        super(tier, properties.durability((int)(tier.getUses())));
    }


    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.SWEEPING_EDGE) {
            return true;
        }
        return enchantment.category == EnchantmentCategory.WEAPON || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers (@NotNull final EquipmentSlot slot, final ItemStack itemStack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        if (slot == EquipmentSlot.MAINHAND)
        {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_UUID, "Weapon modifier",
                    ((this.getTier().getAttackDamageBonus() + 3) * 0.75f),
                    AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2.6d, AttributeModifier.Operation.ADDITION));

            Map<Enchantment, Integer> itemEnchants = itemStack.getAllEnchantments();
        }

        return builder.build();
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });

        float areaDamage = (float) (this.getTier().getAttackDamageBonus() * 0.4);
        float radius = (3 + (areaDamage * 0.5f) + (pStack.getEnchantmentLevel(Enchantments.SWEEPING_EDGE) * 0.5f))/2;
        ScytheAreaDamage(pAttacker,pTarget, pTarget.level(), pTarget.getX(), pTarget.getY(), pTarget.getZ(), areaDamage, radius);

        return true;
    }

    private void ScytheAreaDamage(LivingEntity attacker, LivingEntity target, Level level, double centerX, double centerY, double centerZ, float areaDamage, float radius) {
        final Vec3 center = new Vec3(centerX, centerY, centerZ);

        List<LivingEntity> nearbyEntities = level.getEntitiesOfClass
                        (LivingEntity.class, new AABB(center, center)
                                .inflate((radius)));

        for (LivingEntity entity : nearbyEntities) {
            if ((entity == attacker) || (entity == target)) {
                continue;
            }

            if (attacker instanceof Player player) {
                entity.hurt(level.damageSources().playerAttack(player), areaDamage);
            } else {
                entity.hurt(level.damageSources().mobAttack(attacker), areaDamage);
            }
        }
    }

    @Override
    public boolean canAttackBlock (@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player pPlayer)
    {
        return !pPlayer.isCreative();
    }
}