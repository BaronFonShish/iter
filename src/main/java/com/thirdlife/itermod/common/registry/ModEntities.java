package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.common.entity.*;
import com.thirdlife.itermod.common.entity.misc.*;
import com.thirdlife.itermod.iterMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobType;
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

    public static final RegistryObject<EntityType<GhoulEntity>> GHOUL =
            ENTITY_TYPES.register("ghoul",
                    () -> EntityType.Builder.<GhoulEntity>of(GhoulEntity::new, MobCategory.MONSTER)
                            .setTrackingRange(64)
                            .sized(0.5f, 2f)
                            .build("ghoul"));

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

/// Снаряды

    public static final RegistryObject<EntityType<EtherboltEntity>> ETHERBOLT =
            ENTITY_TYPES.register("etherbolt",
                    () -> EntityType.Builder.<EtherboltEntity>of(EtherboltEntity::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64).setUpdateInterval(1)
                            .sized(0.25f, 0.25f)
                            .build("etherbolt"));

    public static final RegistryObject<EntityType<StraightBeam>> STRAIGHT_BEAM =
            ENTITY_TYPES.register("straight_beam",
                    () -> EntityType.Builder.<StraightBeam>of(StraightBeam::new, MobCategory.MISC)
                            .sized(1F, 1F)
                            .clientTrackingRange(256)
                            .updateInterval(1)
                            .build("straight_beam"));

    public static final RegistryObject<EntityType<JaggedBeam>> JAGGED_BEAM =
            ENTITY_TYPES.register("jagged_beam",
                    () -> EntityType.Builder.<JaggedBeam>of(JaggedBeam::new, MobCategory.MISC)
                            .sized(1F, 1F)
                            .clientTrackingRange(256)
                            .updateInterval(1)
                            .build("jagged_beam"));

    public static final RegistryObject<EntityType<FrostSpikeEntity>> FROST_SPIKE =
            ENTITY_TYPES.register("frost_spike",
                    () -> EntityType.Builder.<FrostSpikeEntity>of(FrostSpikeEntity::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64).setUpdateInterval(1)
                            .sized(0.25f, 0.25f)
                            .build("frost_spike"));

    public static final RegistryObject<EntityType<FlameboltEntity>> FLAMEBOLT =
            ENTITY_TYPES.register("flamebolt",
                    () -> EntityType.Builder.<FlameboltEntity>of(FlameboltEntity::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64).setUpdateInterval(1)
                            .sized(0.25f, 0.25f)
                            .build("flamebolt"));

    public static final RegistryObject<EntityType<HellblazeArrowEntity>> HELLBLAZE_ARROW =
            ENTITY_TYPES.register("hellblaze_arrow",
                    () -> EntityType.Builder.<HellblazeArrowEntity>of(HellblazeArrowEntity::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64).setUpdateInterval(1)
                            .sized(0.25f, 0.25f)
                            .build("hellblaze_arrow"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
