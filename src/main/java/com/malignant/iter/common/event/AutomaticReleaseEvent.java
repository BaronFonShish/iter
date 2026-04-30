package com.malignant.iter.common.event;

import com.malignant.iter.common.item.AbstractIterBow;
import com.malignant.iter.common.registry.ModEnchantments;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber

public class AutomaticReleaseEvent {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
            Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }
            ItemStack bow = player.getUseItem();
            if ((EnchantmentHelper.getEnchantmentLevel(ModEnchantments.getHolder(ModEnchantments.AUTOMATIC), player) != 0)) {

                int usetime = 20;
                if ((bow.getItem() instanceof AbstractIterBow iterBowItem)){
                    usetime = (iterBowItem.MAX_DRAW_DURATION + 1);
                } else if ((bow.getItem() instanceof BowItem bowItem)){
                    usetime = (bowItem.MAX_DRAW_DURATION + 1);
                }
                if (player.getTicksUsingItem() >= usetime && !player.isShiftKeyDown()){
                    player.releaseUsingItem();
                }
            }
        }
}