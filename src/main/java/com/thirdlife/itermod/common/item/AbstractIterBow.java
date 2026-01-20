package com.thirdlife.itermod.common.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.*;

public abstract class AbstractIterBow extends BowItem {

    public AbstractIterBow(Properties properties) {
        super(properties);
    }

    public abstract double getMultiplicativePower();
    public abstract double getAdditivePower();
    public abstract int getMaxDrawDuration();
    public abstract float getVelocityMultiplier();
    public final int MAX_DRAW_DURATION = getMaxDrawDuration();

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (entity instanceof Player player) {
            boolean isCreative = player.getAbilities().instabuild;
            boolean hasInfinity = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
            ItemStack projectileStack = player.getProjectile(stack);

            int charge = this.getUseDuration(stack) - timeLeft;
            charge = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, level, player, charge, !projectileStack.isEmpty() || isCreative || hasInfinity);

            if (charge < 0) return;

            if (!projectileStack.isEmpty() || isCreative || hasInfinity) {
                if (projectileStack.isEmpty()) {
                    projectileStack = new ItemStack(Items.ARROW);
                }

                float power = getPowerForTime(charge, this.getMaxDrawDuration());
                if (!(power < 0.1D)) {
                    ArrowItem arrowItem = (ArrowItem)(projectileStack.getItem() instanceof ArrowItem ? projectileStack.getItem() : Items.ARROW);
                    boolean hasInfiniteArrow = isCreative || (arrowItem.isInfinite(projectileStack, stack, player));

                    if (!level.isClientSide) {
                        AbstractArrow arrow = arrowItem.createArrow(level, projectileStack, player);
                        arrow = this.customArrow(arrow);

                        arrow.shootFromRotation(player, player.getXRot(), player.getYRot(),
                                0.0F, power * this.getVelocityMultiplier(), 1.0F);

                        if (power == 1.0F) {
                            arrow.setCritArrow(true);
                        }

                        double baseDamage = arrow.getBaseDamage();
                        int powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);

                        double powerEnchantBonus = (powerLevel > 0) ? (powerLevel * 0.5 + 0.5) : 0;

                        double finalDamage = (baseDamage + powerEnchantBonus) * this.getMultiplicativePower()
                                + this.getAdditivePower();

                        arrow.setBaseDamage(finalDamage);

                        int punchLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
                        if (punchLevel > 0) {
                            arrow.setKnockback(punchLevel);
                        }

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
                            arrow.setSecondsOnFire(100);
                        }

                        if (hasInfiniteArrow || (isCreative && (projectileStack.is(Items.SPECTRAL_ARROW) || projectileStack.is(Items.TIPPED_ARROW)))) {
                            arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }

                        stack.hurtAndBreak(1, player, (entityParam) -> {
                            entityParam.broadcastBreakEvent(player.getUsedItemHand());
                        });

                        level.addFreshEntity(arrow);
                    }

                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS,
                            1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F);

                    if (!hasInfiniteArrow && !isCreative) {
                        projectileStack.shrink(1);
                        if (projectileStack.isEmpty()) {
                            player.getInventory().removeItem(projectileStack);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    public static float getPowerForTime(int charge, int maxDrawDuration) {
        float f = (float)charge / maxDrawDuration;
        f = (f * f + f * 2.0F) / 3.0F;
        return Math.min(f, 1.0F);
    }

    public static float getPowerForTime(int charge) {
        return getPowerForTime(charge, 20);
    }
}
