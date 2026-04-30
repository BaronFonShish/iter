package com.malignant.iter.common.item;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;

import java.util.List;

public abstract class AbstractIterBow extends BowItem {

    public AbstractIterBow(Properties properties) {
        super(properties);
    }

    public abstract double getMultiplicativePower();
    public abstract double getAdditivePower();
    public abstract int getCustomMaxDrawDuration();
    public abstract float getVelocityMultiplier();

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (entity instanceof Player player) {
            ItemStack projectileStack = player.getProjectile(stack);
            if (projectileStack.isEmpty()) {
                return;
            }

            int i = this.getUseDuration(stack, entity) - timeLeft;
            i = EventHooks.onArrowLoose(stack, level, player, i, !projectileStack.isEmpty());
            if (i < 0) {
                return;
            }

            float f = getPowerForTime(i, getCustomMaxDrawDuration());
            if (!((double) f < 0.1)) {
                List<ItemStack> list = draw(stack, projectileStack, player);
                if (level instanceof ServerLevel serverlevel && !list.isEmpty()) {
                    // Custom arrow creation and damage calculation
                    this.shootCustomArrows(serverlevel, player, player.getUsedItemHand(), stack, list,
                            f * getVelocityMultiplier(), 1.0F, f == 1.0F, null);
                }

                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS,
                        1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    protected void shootCustomArrows(ServerLevel level, LivingEntity shooter, net.minecraft.world.InteractionHand hand,
                                     ItemStack bowStack, List<ItemStack> projectiles, float velocity,
                                     float inaccuracy, boolean isCritical, @org.jetbrains.annotations.Nullable LivingEntity target) {
        for (ItemStack projectileStack : projectiles) {
            if (!(projectileStack.getItem() instanceof ArrowItem arrowItem)) {
                continue;
            }

            Projectile projectile = arrowItem.createArrow(level, projectileStack, shooter, null);

            if (projectile instanceof net.minecraft.world.entity.projectile.AbstractArrow arrow) {
                double baseDamage = arrow.getBaseDamage();
                int powerLevel = EnchantmentHelper.getItemEnchantmentLevel((Holder<Enchantment>) Enchantments.POWER, bowStack);
                double powerEnchantBonus = (powerLevel > 0) ? (powerLevel * 0.5 + 0.5) : 0;
                double finalDamage = (baseDamage + powerEnchantBonus) * getMultiplicativePower() + getAdditivePower();
                arrow.setBaseDamage(finalDamage);


                if (EnchantmentHelper.getItemEnchantmentLevel((Holder<Enchantment>) Enchantments.FLAME, bowStack) > 0) {
                    arrow.setRemainingFireTicks(100);
                }

                // Apply critical hit
                if (isCritical) {
                    arrow.setCritArrow(true);
                }

                // Set pickup behavior for creative/infinite
                boolean isCreative = shooter instanceof Player player && player.getAbilities().instabuild;
                boolean hasInfinity = EnchantmentHelper.getItemEnchantmentLevel((Holder<Enchantment>) Enchantments.INFINITY, bowStack) > 0;
                if (hasInfinity || isCreative) {
                    arrow.pickup = net.minecraft.world.entity.projectile.AbstractArrow.Pickup.CREATIVE_ONLY;
                }
            }

            projectile.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, velocity, inaccuracy);
            level.addFreshEntity(projectile);
        }

        // Damage the bow
        bowStack.hurtAndBreak(1, shooter, LivingEntity.getSlotForHand(hand));

        // Handle arrow consumption
        if (!(shooter instanceof Player player)) return;

        boolean isCreative = player.getAbilities().instabuild;
        boolean hasInfinity = EnchantmentHelper.getItemEnchantmentLevel((Holder<Enchantment>) Enchantments.INFINITY, bowStack) > 0;

        if (!hasInfinity && !isCreative) {
            for (ItemStack projectileStack : projectiles) {
                projectileStack.shrink(1);
                if (projectileStack.isEmpty()) {
                    player.getInventory().removeItem(projectileStack);
                }
            }
        }
    }

    public static float getPowerForTime(int charge, int maxDrawDuration) {
        float f = (float) charge / maxDrawDuration;
        f = (f * f + f * 2.0F) / 3.0F;
        return Math.min(f, 1.0F);
    }

    public static float getPowerForTime(int charge) {
        return getPowerForTime(charge, 20);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }
}