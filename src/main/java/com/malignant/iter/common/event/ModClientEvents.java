package com.malignant.iter.common.event;

import com.malignant.iter.IterMod;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.Objects;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = IterMod.MOD_ID, value = Dist.CLIENT)

public class ModClientEvents {
    @SubscribeEvent
    public static void onComputerFovModifierEvent(ComputeFovModifierEvent event){
        if (event.getPlayer().isUsingItem()){
            Item bow = event.getPlayer().getUseItem().getItem();

            if (Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(bow)).getNamespace().equals(IterMod.MOD_ID) && bow instanceof BowItem){
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