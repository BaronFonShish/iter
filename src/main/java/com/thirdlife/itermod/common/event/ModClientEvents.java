package com.thirdlife.itermod.common.event;

import com.thirdlife.itermod.common.registry.ModItems;
import com.thirdlife.itermod.iterMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = iterMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)

public class ModClientEvents {
    @SubscribeEvent
    public static void onComputerFovModifierEvent(ComputeFovModifierEvent event){
        if (event.getPlayer().isUsingItem()){
            Item bow = event.getPlayer().getUseItem().getItem();
            if (bow == ModItems.RECURVE_BOW.get()){
                float fovModifier = 1f;
                int ticksUsingItem = event.getPlayer().getTicksUsingItem();
                float deltaTicks = (float) ticksUsingItem/20f;

                if (deltaTicks > 1f) deltaTicks = 1f; else deltaTicks *= deltaTicks;

                fovModifier *= 1 - (deltaTicks * 0.15f);
                event.setNewFovModifier(fovModifier);
            }
        }
    }
}
