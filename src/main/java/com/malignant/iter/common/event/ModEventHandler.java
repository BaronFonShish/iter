package com.malignant.iter.common.event;

import com.malignant.iter.IterMod;
import com.malignant.iter.common.item.SpearItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

@EventBusSubscriber(modid = IterMod.MOD_ID)
public class ModEventHandler {

    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        Player player = event.getEntity();
        ItemStack mainHand = player.getMainHandItem();

        if (mainHand.getItem() instanceof SpearItem) {
            if (player.isSprinting()) {
                event.setCriticalHit(true);
            } else {
                event.setCriticalHit(false);
            }
        }
    }
}