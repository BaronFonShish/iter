package com.thirdlife.itermod.common.item;

import com.thirdlife.itermod.common.registry.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

public class MagmanumPickaxe extends PickaxeItem {

    public MagmanumPickaxe() {
        super(new Tier() {
            public int getUses() {
                return 1666;
            }

            public float getSpeed() {
                return 4f;
            }

            public float getAttackDamageBonus() {
                return 3f;
            }

            public int getLevel() {
                return 3;
            }

            public int getEnchantmentValue() {
                return 16;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(ModItems.MAGMANUM.get()));
            }
        }, 2, -2.8f, new Properties().rarity(Rarity.UNCOMMON).fireResistant());
    }

    public boolean hurtEnemy(ItemStack Stack, LivingEntity Target, LivingEntity Attacker) {
        boolean retval = super.hurtEnemy(Stack, Target, Attacker);
        Target.setSecondsOnFire(5);
        Target.level().explode(Attacker, Target.getX(), Target.getY() + Target.getBbHeight()/1.75f, Target.getZ(), 0.25f, false, Level.ExplosionInteraction.NONE);
        return retval;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);
        if (!context.getLevel().isClientSide()) {
            context.getLevel().explode((Entity) null, (double) context.getClickedPos().getX() + 0.5,
                    (double) context.getClickedPos().getY() + 0.5,
                    (double) context.getClickedPos().getZ() + 0.5, 1.5F, false, Level.ExplosionInteraction.TNT);
            context.getPlayer().getCooldowns().addCooldown(this, 100);
        }

        if (context.getPlayer() instanceof ServerPlayer serverPlayer) {
            serverPlayer.swing(context.getHand(), true);
            if (serverPlayer.gameMode.getGameModeForPlayer() != GameType.CREATIVE);{
                context.getItemInHand().hurtAndBreak(4, serverPlayer, (playerEntity) -> {
                    playerEntity.broadcastBreakEvent(context.getHand());
                });
            }
        }
        return InteractionResult.SUCCESS;
    }


}