package com.thirdlife.itermod.common.registry;

//import com.thirdlife.itermod.common.entity.FlailHeadEntity;
import com.thirdlife.itermod.common.entity.GoblinWarriorEntity;
import com.thirdlife.itermod.common.entity.misc.EtherboltEntity;
import com.thirdlife.itermod.iterMod;
import com.thirdlife.itermod.common.entity.SpiderlingEntity;
import com.thirdlife.itermod.world.gui.SpellBookGuiMenu;
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

    public static final RegistryObject<EntityType<EtherboltEntity>> ETHERBOLT =
            ENTITY_TYPES.register("etherbolt",
                    () -> EntityType.Builder.<EtherboltEntity>of(EtherboltEntity::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64).setUpdateInterval(1)
                    .sized(0.25f, 0.25f)
                            .build("etherbolt"));

    public static final RegistryObject<EntityType<GoblinWarriorEntity>> GOBLIN_WARRIOR =
            ENTITY_TYPES.register("goblin_warrior",
                    () -> EntityType.Builder.<GoblinWarriorEntity>of(GoblinWarriorEntity::new, MobCategory.MONSTER)
                            .setTrackingRange(64)
                            .sized(0.5f, 1f)
                            .build("goblin_warrior"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
