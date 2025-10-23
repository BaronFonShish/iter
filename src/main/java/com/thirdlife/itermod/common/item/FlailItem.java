package com.thirdlife.itermod.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
//import com.thirdlife.itermod.common.entity.FlailHeadEntity;
import com.thirdlife.itermod.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FlailItem extends TieredItem {

    public FlailItem(Tiers tier, Properties properties) {
        super(tier, properties.durability((int)(tier.getUses() * 1.5)));
    }

    public static final String THROWN_TAG = "Thrown";

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        if (!level.isClientSide && entity instanceof Player player) {
            updateThrownState(stack, level, player);
        }
    }

    private void updateThrownState(ItemStack stack, Level level, Player player) {
        CompoundTag tag = stack.getOrCreateTag();

        // Check if Thrown tag is currently true
        if (tag.getBoolean(THROWN_TAG)) {
            boolean shouldReset = false;

            // Condition 1: Check if flail isn't on cooldown
            if (!player.getCooldowns().isOnCooldown(this)) {
                shouldReset = true;
            }

            // Condition 2: Check if there aren't any FlailHead entities owned by this player
//            if (!hasActiveFlailHeads(level, player)) {
//                shouldReset = true;
//            }

            // If both conditions are met, reset the Thrown tag to false
            if (shouldReset) {
                tag.putBoolean(THROWN_TAG, false);
                stack.setTag(tag);
            }
        }
    }

//    private boolean hasActiveFlailHeads(Level level, Player player) {
//        // Get all FlailHead entities in a reasonable radius
//        List<FlailHeadEntity> flailHeads = level.getEntitiesOfClass(
//                FlailHeadEntity.class,
//                player.getBoundingBox().inflate(20), // 20 block radius
//                entity -> entity.getOwner() == player && entity.isAlive()
//        );
//
//        return !flailHeads.isEmpty();
//    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            // Check if flail is already thrown
            if (stack.getOrCreateTag().getBoolean(THROWN_TAG)) {
                return InteractionResultHolder.fail(stack);
            }

            // Check cooldown
            if (player.getCooldowns().isOnCooldown(this)) {
                return InteractionResultHolder.fail(stack);
            }

            //throwFlail(level, player, stack, hand);
        }

        return InteractionResultHolder.success(stack);
    }

//    private void throwFlail(Level level, Player player, ItemStack stack, InteractionHand hand) {
//
//        CompoundTag tag = stack.getOrCreateTag();
//        tag.putBoolean(THROWN_TAG, true);
//        stack.setTag(tag);
//
//        // Create flail head entity
//        FlailHeadEntity flailHead = new FlailHeadEntity(level, player);
//        flailHead.setTexture(getFlailTexture(stack));
//
//        Vec3 lookVec = player.getLookAngle();
//        Vec3 spawnPos = player.getEyePosition().add(lookVec.scale(1.5));
//
//        flailHead.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
//        flailHead.shoot(lookVec.x, lookVec.y, lookVec.z, 1.5F, 1.0F);
//
//        float flailhead_damage = (float)(player.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
//        flailHead.setDamage(level, flailhead_damage);
//
//        level.addFreshEntity(flailHead);
//
//        player.getCooldowns().addCooldown(this, 40);
//
//        stack.hurtAndBreak(1, player, (p) -> {
//            p.broadcastBreakEvent(hand);
//        });
//    }

//    private String getFlailTexture(ItemStack stack) {
//
//        if (stack.getItem() == ModItems.WOODEN_FLAIL.get()) return "wood";
//        if (stack.getItem() == ModItems.IRON_FLAIL.get()) return "iron";
//        if (stack.getItem() == ModItems.DIAMOND_FLAIL.get()) return "diamond";
//        if (stack.getItem() == ModItems.NETHERITE_FLAIL.get()) return "netherite";
//        return "iron"; // default
//    }

    // Helper method to check if flail is thrown (for rendering)
    public static boolean isThrown(ItemStack stack) {
        return stack.hasTag() && stack.getTag().getBoolean(THROWN_TAG);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        // Don't play reequip animation when Thrown state changes
        return !ItemStack.isSameItem(oldStack, newStack) || slotChanged;
    }

    public static final String ReachUUID = "c38ac4f4-d813-4ba7-a986-07c164f89266";

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers (@NotNull final EquipmentSlot slot, final ItemStack itemStack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        if (slot == EquipmentSlot.MAINHAND)
        {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_UUID, "Weapon modifier",
                    (((this.getTier().getAttackDamageBonus() + 3) * 2f) + 1f),
                    AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_UUID, "Weapon modifier", -3.5d, AttributeModifier.Operation.ADDITION));

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