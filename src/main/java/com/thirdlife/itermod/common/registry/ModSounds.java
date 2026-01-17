package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.iterMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, iterMod.MOD_ID);

    public static final RegistryObject<SoundEvent> ETHERBOLT_IMPACT = registerSoundEvent("etherbolt_impact");
    public static final RegistryObject<SoundEvent> CAST_ARCANE = registerSoundEvent("cast_arcane");
    public static final RegistryObject<SoundEvent> GOBLIN_AMBIENT = registerSoundEvent("goblin_ambient");
    public static final RegistryObject<SoundEvent> GOBLIN_HURT = registerSoundEvent("goblin_hurt");


    private static RegistryObject<SoundEvent> registerSoundEvent(String name){
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(iterMod.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
