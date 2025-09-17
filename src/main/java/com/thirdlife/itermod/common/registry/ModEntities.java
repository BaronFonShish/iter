package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.entity.mob.Spiderling;
import com.thirdlife.itermod.iterMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, iterMod.MOD_ID);

    public static final RegistryObject<EntityType<Spiderling>> SPIDERLING = ENTITIES.register("spiderling", EntityType.Builder.<Spiderling>of(Spiderling::new, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
            .sized(0.6f, 0.5f));


    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}