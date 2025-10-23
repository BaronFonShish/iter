package com.thirdlife.itermod.common.event;


import com.thirdlife.itermod.common.item.SpearItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "iter", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEventHandler {

    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        Player player = event.getEntity();
        ItemStack mainHand = player.getMainHandItem();

        if (mainHand.getItem() instanceof SpearItem) {
            if (player.isSprinting()) {
                event.setResult(Event.Result.ALLOW);
            } else {
                event.setResult(Event.Result.DENY);
            }
        }
    }
}