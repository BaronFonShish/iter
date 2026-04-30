package com.malignant.iter.common.registry;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)

public class ModCuriosSlots {
    @SubscribeEvent
    public static void enqueueIMC(final FMLCommonSetupEvent event) {
        //InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BACK.getMessageBuilder().size(1).build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.RING.getMessageBuilder().size(4).build());
        //InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CHARM.getMessageBuilder().size(3).build());
    }

}
