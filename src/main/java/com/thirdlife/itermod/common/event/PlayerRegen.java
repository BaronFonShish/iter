package com.thirdlife.itermod.common.event;


import com.thirdlife.itermod.common.registry.ModAttributes;
import com.thirdlife.itermod.common.registry.ModCapabilities;
import com.thirdlife.itermod.common.variables.MageDataProvider;
import com.thirdlife.itermod.common.variables.MageUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "iter", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerRegen {


    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide()) {
            Player player = event.player;

            if (player.isSpectator() || player.isDeadOrDying() || !player.isAlive()) {
                return;
            }

            AttributeInstance regenerationAttr = player.getAttribute(ModAttributes.REGENERATION.get());
            float regenAmount = (float) regenerationAttr.getValue();
            if (regenAmount == 0) return;

            if (player.level().getGameTime() % 10 == 0) {
                regenAmount /= 2f;
                player.heal(regenAmount);
            }
        }
    }
}
