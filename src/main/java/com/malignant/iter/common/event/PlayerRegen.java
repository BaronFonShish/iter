package com.malignant.iter.common.event;

import com.malignant.iter.common.registry.ModAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = "iter")
public class PlayerRegen {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) {
            return;
        }

        if (player.isDeadOrDying() || !player.isAlive()) {
            return;
        }

        AttributeInstance regenerationAttr = player.getAttribute(ModAttributes.REGENERATION);
        if (regenerationAttr == null) return;

        float regenAmount = (float) regenerationAttr.getValue();
        if (regenAmount == 0) return;

        if (player.level().getGameTime() % 10 == 0) {
            regenAmount /= 2f;
            player.heal(regenAmount);
        }
    }
}