package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.common.entity.GiantSpiderEntity;
import com.thirdlife.itermod.common.entity.GoblinEntity;
import com.thirdlife.itermod.common.entity.GoblinWarriorEntity;
import com.thirdlife.itermod.common.entity.misc.EtherboltEntity;
import com.thirdlife.itermod.common.entity.misc.FrostSpikeEntity;
import com.thirdlife.itermod.iterMod;
import com.thirdlife.itermod.common.entity.SpiderlingEntity;
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

    public static final RegistryObject<EntityType<GiantSpiderEntity>> GIANT_SPIDER =
            ENTITY_TYPES.register("giant_spider",
                    () -> EntityType.Builder.of(GiantSpiderEntity::new, MobCategory.MONSTER)
                            .sized(1.5f, 1.4f)
                            .build("giant_spider"));

    public static final RegistryObject<EntityType<EtherboltEntity>> ETHERBOLT =
            ENTITY_TYPES.register("etherbolt",
                    () -> EntityType.Builder.<EtherboltEntity>of(EtherboltEntity::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64).setUpdateInterval(1)
                    .sized(0.25f, 0.25f)
                            .build("etherbolt"));

    public static final RegistryObject<EntityType<FrostSpikeEntity>> FROST_SPIKE =
            ENTITY_TYPES.register("frost_spike",
                    () -> EntityType.Builder.<FrostSpikeEntity>of(FrostSpikeEntity::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64).setUpdateInterval(1)
                            .sized(0.25f, 0.25f)
                            .build("frost_spike"));

    public static final RegistryObject<EntityType<GoblinWarriorEntity>> GOBLIN_WARRIOR =
            ENTITY_TYPES.register("goblin_warrior",
                    () -> EntityType.Builder.<GoblinWarriorEntity>of(GoblinWarriorEntity::new, MobCategory.MONSTER)
                            .setTrackingRange(64)
                            .sized(0.5f, 1f)
                            .build("goblin_warrior"));

    public static final RegistryObject<EntityType<GoblinEntity>> GOBLIN =
            ENTITY_TYPES.register("goblin",
                    () -> EntityType.Builder.<GoblinEntity>of(GoblinEntity::new, MobCategory.MONSTER)
                            .setTrackingRange(64)
                            .sized(0.5f, 0.9f)
                            .build("goblin"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
