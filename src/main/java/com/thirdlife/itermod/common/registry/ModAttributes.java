package com.thirdlife.itermod.common.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


@Mod.EventBusSubscriber(modid = "iter", bus = Mod.EventBusSubscriber.Bus.MOD)

public class ModAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, "iter");

    public static final RegistryObject<Attribute> SPELL_POWER = ATTRIBUTES.register("spell_power",
            () -> new RangedAttribute("attribute.iter.spell_power", 1.0, 0.0, 128.0)
                    .setSyncable(true));

    public static final RegistryObject<Attribute> CASTING_SPEED = ATTRIBUTES.register("casting_speed",
            () -> new RangedAttribute("attribute.iter.casting_speed", 1.0, 0.1, 8.0)
                    .setSyncable(true));

    public static final RegistryObject<Attribute> ETHER_EFFICIENCY = ATTRIBUTES.register("ether_efficiency",
            () -> new RangedAttribute("attribute.iter.ether_efficiency", 1.0, 0.0, 2.0)
                    .setSyncable(true));

    public static final RegistryObject<Attribute> ETHER_BURNOUT_DISSIPATION = ATTRIBUTES.register("ether_burnout_dissipation",
            () -> new RangedAttribute("attribute.iter.ether_burnout_dissipation", 0.2, 0.0, 1024.0)
                    .setSyncable(true));

    public static final RegistryObject<Attribute> ETHER_BURNOUT_THRESHOLD = ATTRIBUTES.register("ether_burnout_threshold",
            () -> new RangedAttribute("attribute.iter.ether_burnout_threshold", 50.0, 0.0, 250000.0)
                    .setSyncable(true));

    public static final RegistryObject<Attribute> REGENERATION = ATTRIBUTES.register("regeneration",
            () -> new RangedAttribute("attribute.regeneration", 0.0, 0.0, 128.0)
                    .setSyncable(true));

    public static final RegistryObject<Attribute> FLIGHT_TIME = ATTRIBUTES.register("flight_time",
            () -> new RangedAttribute("attribute.iter.flight_time", 0.0, 0.0, 250000.0)
                    .setSyncable(true));

    public static final RegistryObject<Attribute> FLIGHT_SPEED = ATTRIBUTES.register("flight_speed",
            () -> new RangedAttribute("attribute.iter.flight_speed", 1, 0.0, 100)
                    .setSyncable(true));



    @SubscribeEvent
    public static void addAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, SPELL_POWER.get());
        event.add(EntityType.PLAYER, CASTING_SPEED.get());
        event.add(EntityType.PLAYER, ETHER_EFFICIENCY.get());

        event.add(EntityType.PLAYER, ETHER_BURNOUT_DISSIPATION.get());
        event.add(EntityType.PLAYER, ETHER_BURNOUT_THRESHOLD.get());

        event.add(EntityType.PLAYER, REGENERATION.get());

        event.add(EntityType.PLAYER, FLIGHT_TIME.get());
        event.add(EntityType.PLAYER, FLIGHT_SPEED.get());

    }

    @Mod.EventBusSubscriber
    private class Utils {
        @SubscribeEvent
        public static void persistAttributes(PlayerEvent.Clone event) {
            Player oldP = event.getOriginal();
            Player newP = (Player) event.getEntity();
            newP.getAttribute(SPELL_POWER.get()).setBaseValue(oldP.getAttribute(SPELL_POWER.get()).getBaseValue());
            newP.getAttribute(CASTING_SPEED.get()).setBaseValue(oldP.getAttribute(CASTING_SPEED.get()).getBaseValue());
            newP.getAttribute(ETHER_EFFICIENCY.get()).setBaseValue(oldP.getAttribute(ETHER_EFFICIENCY.get()).getBaseValue());

            newP.getAttribute(ETHER_BURNOUT_DISSIPATION.get()).setBaseValue(oldP.getAttribute(ETHER_BURNOUT_DISSIPATION.get()).getBaseValue());
            newP.getAttribute(ETHER_BURNOUT_THRESHOLD.get()).setBaseValue(oldP.getAttribute(ETHER_BURNOUT_THRESHOLD.get()).getBaseValue());

            newP.getAttribute(REGENERATION.get()).setBaseValue(oldP.getAttribute(REGENERATION.get()).getBaseValue());

            newP.getAttribute(FLIGHT_TIME.get()).setBaseValue(oldP.getAttribute(FLIGHT_TIME.get()).getBaseValue());
            newP.getAttribute(FLIGHT_SPEED.get()).setBaseValue(oldP.getAttribute(FLIGHT_SPEED.get()).getBaseValue());
        }
    }
}