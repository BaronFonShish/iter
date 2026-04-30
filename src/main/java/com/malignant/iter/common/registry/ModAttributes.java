package com.malignant.iter.common.registry;

import com.malignant.iter.IterMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.fml.common.EventBusSubscriber;



@EventBusSubscriber(modid = "iter")

public class ModAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, IterMod.MOD_ID);

    public static final DeferredHolder<Attribute, Attribute> SPELL_POWER = ATTRIBUTES.register("spell_power",
            () -> new RangedAttribute("attribute.iter.spell_power", 1.0, 0.0, 128.0)
                    .setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> CASTING_SPEED = ATTRIBUTES.register("casting_speed",
            () -> new RangedAttribute("attribute.iter.casting_speed", 1.0, 0.1, 8.0)
                    .setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> ETHER_EFFICIENCY = ATTRIBUTES.register("ether_efficiency",
            () -> new RangedAttribute("attribute.iter.ether_efficiency", 1.0, 0.0, 2.0)
                    .setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> ETHER_BURNOUT_DISSIPATION = ATTRIBUTES.register("ether_burnout_dissipation",
            () -> new RangedAttribute("attribute.iter.ether_burnout_dissipation", 0.2, 0.0, 1024.0)
                    .setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> ETHER_BURNOUT_THRESHOLD = ATTRIBUTES.register("ether_burnout_threshold",
            () -> new RangedAttribute("attribute.iter.ether_burnout_threshold", 50.0, 0.0, 250000.0)
                    .setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> REGENERATION = ATTRIBUTES.register("regeneration",
            () -> new RangedAttribute("attribute.regeneration", 0.0, 0.0, 128.0)
                    .setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> RANGED_DAMAGE = ATTRIBUTES.register("ranged_damage",
            () -> new RangedAttribute("attribute.ranged_damage", 1.0, 0.0, 128.0)
                    .setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> ARCANE_POWER = ATTRIBUTES.register("arcane_power",
            () -> new RangedAttribute("attribute.arcane_power", 1.0, 0.0, 128.0)
                    .setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> OCCULT_POWER = ATTRIBUTES.register("occult_power",
            () -> new RangedAttribute("attribute.occult_power", 1.0, 0.0, 128.0)
                    .setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> PRIMAL_POWER = ATTRIBUTES.register("primal_power",
            () -> new RangedAttribute("attribute.primal_power", 1.0, 0.0, 128.0)
                    .setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> FLIGHT_TIME = ATTRIBUTES.register("flight_time",
            () -> new RangedAttribute("attribute.iter.flight_time", 0.0, 0.0, 250000.0)
                    .setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> FLIGHT_SPEED = ATTRIBUTES.register("flight_speed",
            () -> new RangedAttribute("attribute.iter.flight_speed", 1, 0.0, 100)
                    .setSyncable(true));



    @SubscribeEvent
    public static void addAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, SPELL_POWER);
        event.add(EntityType.PLAYER, CASTING_SPEED);
        event.add(EntityType.PLAYER, ETHER_EFFICIENCY);

        event.add(EntityType.PLAYER, ETHER_BURNOUT_DISSIPATION);
        event.add(EntityType.PLAYER, ETHER_BURNOUT_THRESHOLD);

        event.add(EntityType.PLAYER, ARCANE_POWER);
        event.add(EntityType.PLAYER, OCCULT_POWER);
        event.add(EntityType.PLAYER, PRIMAL_POWER);

        event.add(EntityType.PLAYER, REGENERATION);
        event.add(EntityType.PLAYER, RANGED_DAMAGE);

        event.add(EntityType.PLAYER, FLIGHT_TIME);
        event.add(EntityType.PLAYER, FLIGHT_SPEED);

    }

    @EventBusSubscriber
    private class Utils {
        @SubscribeEvent
        public static void persistAttributes(PlayerEvent.Clone event) {
            Player oldP = event.getOriginal();
            Player newP = (Player) event.getEntity();
            newP.getAttribute(SPELL_POWER).setBaseValue(oldP.getAttribute(SPELL_POWER).getBaseValue());
            newP.getAttribute(CASTING_SPEED).setBaseValue(oldP.getAttribute(CASTING_SPEED).getBaseValue());
            newP.getAttribute(ETHER_EFFICIENCY).setBaseValue(oldP.getAttribute(ETHER_EFFICIENCY).getBaseValue());

            newP.getAttribute(ETHER_BURNOUT_DISSIPATION).setBaseValue(oldP.getAttribute(ETHER_BURNOUT_DISSIPATION).getBaseValue());
            newP.getAttribute(ETHER_BURNOUT_THRESHOLD).setBaseValue(oldP.getAttribute(ETHER_BURNOUT_THRESHOLD).getBaseValue());

            newP.getAttribute(ARCANE_POWER).setBaseValue(oldP.getAttribute(ARCANE_POWER).getBaseValue());
            newP.getAttribute(OCCULT_POWER).setBaseValue(oldP.getAttribute(OCCULT_POWER).getBaseValue());
            newP.getAttribute(PRIMAL_POWER).setBaseValue(oldP.getAttribute(PRIMAL_POWER).getBaseValue());

            newP.getAttribute(REGENERATION).setBaseValue(oldP.getAttribute(REGENERATION).getBaseValue());
            newP.getAttribute(RANGED_DAMAGE).setBaseValue(oldP.getAttribute(RANGED_DAMAGE).getBaseValue());

            newP.getAttribute(FLIGHT_TIME).setBaseValue(oldP.getAttribute(FLIGHT_TIME).getBaseValue());
            newP.getAttribute(FLIGHT_SPEED).setBaseValue(oldP.getAttribute(FLIGHT_SPEED).getBaseValue());
        }
    }
}