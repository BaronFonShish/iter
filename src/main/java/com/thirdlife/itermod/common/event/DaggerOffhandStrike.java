package com.thirdlife.itermod.common.event;

import com.google.common.collect.Multimap;
import com.thirdlife.itermod.common.item.DaggerItem;
import com.thirdlife.itermod.iterMod;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = iterMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DaggerOffhandStrike {

    @SubscribeEvent
    public static void onPlayerAttack(LivingHurtEvent event) {

        Entity source = event.getSource().getDirectEntity();
        Entity target = event.getEntity();

        if (!(source instanceof Player player)) {
            return;
        }

        if (!(target instanceof LivingEntity)) {
            return;
        }

        ItemStack offhandItem = player.getOffhandItem();
        if (!(offhandItem.getItem() instanceof DaggerItem) || player.getCooldowns().isOnCooldown(offhandItem.getItem())) {
            return;
        }


        Level level = player.level();
        if (!level.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) level;
            serverLevel.getServer().tell(new TickTask(serverLevel.getServer().getTickCount() + 3, () -> {
                executeOffhandAttack(player, event.getEntity(), offhandItem);
            }));
        }
    }

    private static void executeOffhandAttack(Player player, LivingEntity target, ItemStack dagger) {
        Level level = player.level();
        if (level.isClientSide || !target.isAlive()) {
            return;
        }

        target.invulnerableTime = 0;

        float damage = getDaggerDamage(player, dagger);

        if (dagger.getItem() instanceof DaggerItem daggerItem) {
            int attackSpeed = (int) getDaggerBaseAttackSpeed(dagger);
            int cooldownTicks = (int) Math.max(1, (int) (20.0f / attackSpeed) * 6f);
            player.getCooldowns().addCooldown(dagger.getItem(), cooldownTicks);
        }
        else player.getCooldowns().addCooldown(dagger.getItem(), 15);

        player.swing(InteractionHand.OFF_HAND, true);

        target.hurt(level.damageSources().playerAttack(player), damage);

        dagger.hurtAndBreak(1, player, (p) -> {
            p.broadcastBreakEvent(InteractionHand.OFF_HAND);
        });

    }

    private static float getDaggerDamage(Player player, ItemStack dagger) {

        float baseDamage = getDaggerBaseDamage(dagger);

        if (player.hasEffect(MobEffects.DAMAGE_BOOST)) {
            int amplifier = player.getEffect(MobEffects.DAMAGE_BOOST).getAmplifier();
            baseDamage += (amplifier + 1) * 3.0F;
        }

        if (player.hasEffect(MobEffects.WEAKNESS)) {
            int amplifier = player.getEffect(MobEffects.WEAKNESS).getAmplifier();
            baseDamage -= (amplifier + 1) * 4.0F;
        }

        return Math.max(0, baseDamage);
    }

    private static float getDaggerBaseDamage(ItemStack dagger) {
        Multimap<Attribute, AttributeModifier> attributes = dagger.getAttributeModifiers(EquipmentSlot.MAINHAND);
        for (AttributeModifier modifier : attributes.get(Attributes.ATTACK_DAMAGE)) {
            if (modifier.getOperation() == AttributeModifier.Operation.ADDITION) {
                return (float) modifier.getAmount();
            }
        }
        return 0;
    }

    private static float getDaggerBaseAttackSpeed(ItemStack dagger) {
        Multimap<Attribute, AttributeModifier> attributes = dagger.getAttributeModifiers(EquipmentSlot.MAINHAND);
        for (AttributeModifier modifier : attributes.get(Attributes.ATTACK_SPEED)) {
            if (modifier.getOperation() == AttributeModifier.Operation.ADDITION) {
                return (float) modifier.getAmount();
            }
        }
        return 0;
    }
}
