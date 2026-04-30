package com.malignant.iter.common.item;

import com.malignant.iter.common.registry.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

public class MagmanumPickaxe extends PickaxeItem {

    // Custom tier similar to iron but with better durability
    private static final Tier MAGMANUM_TIER = new Tier() {
        @Override
        public int getUses() {
            return 1666; // Between iron (250) and diamond (1561)
        }

        @Override
        public float getSpeed() {
            return 4.0f; // Same as iron
        }

        @Override
        public float getAttackDamageBonus() {
            return 3.0f; // Same as iron
        }

        @Override
        public TagKey<Block> getIncorrectBlocksForDrops() {
            return null;
        }


        @Override
        public int getEnchantmentValue() {
            return 16; // Better than iron (14)
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(ModItems.MAGMANUM.get());
        }
    };

    public MagmanumPickaxe() {
        super(MAGMANUM_TIER, new Properties()
                .attributes(PickaxeItem.createAttributes(MAGMANUM_TIER, 2, -2.8f))
                .rarity(Rarity.UNCOMMON)
                .fireResistant());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean retval = super.hurtEnemy(stack, target, attacker);
        target.setRemainingFireTicks(100); // 5 seconds (20 ticks per second)
        target.level().explode(attacker, target.getX(), target.getY() + target.getBbHeight() / 1.75f,
                target.getZ(), 0.25f, false, Level.ExplosionInteraction.NONE);
        return retval;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);

        if (!context.getLevel().isClientSide()) {
            context.getLevel().explode(null,
                    (double) context.getClickedPos().getX() + 0.5,
                    (double) context.getClickedPos().getY() + 0.5,
                    (double) context.getClickedPos().getZ() + 0.5,
                    1.5F, false, Level.ExplosionInteraction.TNT);
            context.getPlayer().getCooldowns().addCooldown(this, 100);
        }

        if (context.getPlayer() instanceof ServerPlayer serverPlayer) {
            serverPlayer.swing(context.getHand(), true);
            if (serverPlayer.gameMode.getGameModeForPlayer() != GameType.CREATIVE) {
                context.getItemInHand().hurtAndBreak(4, serverPlayer,
                        LivingEntity.getSlotForHand(context.getHand()));
            }
        }
        return InteractionResult.SUCCESS;
    }
}