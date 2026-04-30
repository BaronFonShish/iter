package com.malignant.iter.common.item;

import com.malignant.iter.common.registry.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;

public class MagmanumSword extends SwordItem {

    private static final Tier MAGMANUM_TIER = new Tier() {
        @Override
        public int getUses() {
            return 1666;
        }

        @Override
        public float getSpeed() {
            return 4f;
        }

        @Override
        public float getAttackDamageBonus() {
            return 3f;
        }

        @Override
        public TagKey<Block> getIncorrectBlocksForDrops() {
            return null;
        }

        @Override
        public int getEnchantmentValue() {
            return 16;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(ModItems.MAGMANUM.get());
        }

    };

    public MagmanumSword() {
        super(MAGMANUM_TIER, new Properties()
                .attributes(SwordItem.createAttributes(MAGMANUM_TIER, 3, -2.4f))
                .rarity(Rarity.UNCOMMON)
                .fireResistant());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean retval = super.hurtEnemy(stack, target, attacker);
        target.setRemainingFireTicks(200); // 10 seconds
        target.level().explode(attacker, target.getX(), target.getY() + target.getBbHeight() / 1.75f,
                target.getZ(), 0.75f, true, Level.ExplosionInteraction.NONE);
        return retval;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        player.getCooldowns().addCooldown(this, 50);

        if (!level.isClientSide) {
            Vec3 lookAngle = player.getLookAngle();

            SmallFireball fireball = new SmallFireball(
                    level,
                    player.getX(),
                    player.getEyeY(),
                    player.getZ(),
                    player.getLookAngle()
            );

            fireball.setDeltaMovement(lookAngle.x * 1.5, lookAngle.y * 1.5, lookAngle.z * 1.5);
            level.addFreshEntity(fireball);
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.FIRECHARGE_USE,
                SoundSource.PLAYERS, 0.75f, 1f);

        if (player instanceof ServerPlayer serverPlayer) {
            player.swing(hand, true);
            if (serverPlayer.gameMode.getGameModeForPlayer() != GameType.CREATIVE) {
                itemstack.hurtAndBreak(4, player, LivingEntity.getSlotForHand(hand));
            }
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}