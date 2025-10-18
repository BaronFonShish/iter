package com.thirdlife.itermod.common.registry;

//import com.thirdlife.itermod.common.entity.FlailHeadEntity;
import com.thirdlife.itermod.iterMod;
import com.thirdlife.itermod.common.entity.SpiderlingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {


    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, iterMod.MOD_ID);


    public static final RegistryObject<EntityType<SpiderlingEntity>> SPIDERLING =
            ENTITY_TYPES.register("spiderling",
                    () -> EntityType.Builder.of(SpiderlingEntity::new, MobCategory.MONSTER)
                            .sized(0.6f, 0.5f)
                            .build("spiderling"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
