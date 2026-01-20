package com.thirdlife.itermod.common.event;

import com.thirdlife.itermod.common.item.AbstractIterBow;
import com.thirdlife.itermod.common.registry.ModEnchantments;
import com.thirdlife.itermod.common.registry.ModItems;
import com.thirdlife.itermod.iterMod;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber

public class AutomaticReleaseEvent {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            ItemStack bow = player.getUseItem();
            if ((EnchantmentHelper.getEnchantmentLevel(ModEnchantments.AUTOMATIC.get(), player) != 0)) {

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
}