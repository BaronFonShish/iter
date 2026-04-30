package com.malignant.iter.common.registry;

import com.malignant.iter.IterMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;


public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, IterMod.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> ETHERBOLT_IMPACT = registerSoundEvent("etherbolt_impact");
    public static final DeferredHolder<SoundEvent, SoundEvent> START_CASTING = registerSoundEvent("start_casting");
    public static final DeferredHolder<SoundEvent, SoundEvent> CAST_ARCANE = registerSoundEvent("cast_arcane");
    public static final DeferredHolder<SoundEvent, SoundEvent> CAST_OCCULT = registerSoundEvent("cast_occult");
    public static final DeferredHolder<SoundEvent, SoundEvent> GOBLIN_AMBIENT = registerSoundEvent("goblin_ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> GOBLIN_HURT = registerSoundEvent("goblin_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> GOBLIN_DEATH = registerSoundEvent("goblin_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> BEREFT_AMBIENT = registerSoundEvent("bereft_ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> BEREFT_HURT = registerSoundEvent("bereft_hurt");


    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name){
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
