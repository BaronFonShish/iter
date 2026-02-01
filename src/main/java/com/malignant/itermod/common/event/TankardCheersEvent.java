package com.malignant.itermod.common.event;

import com.malignant.itermod.common.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TankardCheersEvent {

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        ItemStack stack = event.getItemStack();

        if (stack.getItem() == ModItems.TANKARD.get()) {
            if (event.getHand() == InteractionHand.MAIN_HAND) {
                // Выполняем только на стороне сервера
                if (!event.getLevel().isClientSide) {
                    String playerName = event.getEntity().getName().getString();

                    // Сообщение в общий чат
                    event.getEntity().getServer().getPlayerList().broadcastSystemMessage(
                            Component.literal(playerName + " cheers!"), false
                    );
                }
            }
        }
    }
}
