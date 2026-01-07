package com.thirdlife.itermod.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.thirdlife.itermod.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class MagmanumSword extends SwordItem {

    public MagmanumSword() {
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
                return 1;
            }

            public int getEnchantmentValue() {
                return 16;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(ModItems.MAGMANUM.get()));
            }
        }, 3, -2.4f, new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant());
    }

    public boolean hurtEnemy(ItemStack Stack, LivingEntity Target, LivingEntity Attacker) {
        boolean retval = super.hurtEnemy(Stack, Target, Attacker);
        Target.setSecondsOnFire(10);
        Target.level().explode(Attacker, Target.getX(), Target.getY() + Target.getBbHeight()/1.75f, Target.getZ(), 0.75f, true, Level.ExplosionInteraction.NONE);
        return retval;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        player.getCooldowns().addCooldown(this, 50);

        if (!level.isClientSide) {
            SmallFireball fireball = new SmallFireball(level, player, player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z);
            fireball.setPos(player.getX(), player.getEyeY(), player.getZ());
            fireball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);

            level.addFreshEntity(fireball);
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.FIRECHARGE_USE,
                SoundSource.PLAYERS, 0.75f, 1f);
        if (player instanceof ServerPlayer serverPlayer) {
            player.swing(hand, true);
           if (serverPlayer.gameMode.getGameModeForPlayer() != GameType.CREATIVE);{
                itemstack.hurtAndBreak(4, player, (playerEntity) -> {
                    playerEntity.broadcastBreakEvent(hand);
                });
            }
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

}