package com.malignant.iter;

import com.malignant.iter.client.model.ModModelLayers;
import com.malignant.iter.client.renderer.*;
import com.malignant.iter.common.IterModConfig;
import com.malignant.iter.common.entity.*;
import com.malignant.iter.common.payload.*;
import com.malignant.iter.common.registry.*;
import com.mojang.logging.LogUtils;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;

@Mod(IterMod.MOD_ID)
public class IterMod {
    public static final String MOD_ID = "iter";
    public static final ResourceLocation PICTOGRAM_FONT = ResourceLocation.parse(MOD_ID + ":font/iter_pictograms.json");
    public static final Logger LOGGER = LogUtils.getLogger();
    public static RegistryAccess registryAccess;

    public IterMod(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, IterModConfig.COMMON_SPEC);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::registerPayloads);
        modEventBus.addListener(ModScreens::registerScreens);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.REGISTRY.register(modEventBus);
        ModEntities.register(modEventBus);
        ModSounds.register(modEventBus);
        ModTabs.REGISTRY.register(modEventBus);
        ModFeatures.REGISTRY.register(modEventBus);
        ModProcessors.PROCESSORS.register(modEventBus);
        ModRuleTests.RULE_TEST_TYPES.register(modEventBus);
        ModStructures.STRUCTURE_TYPES.register(modEventBus);
        ModAttributes.ATTRIBUTES.register(modEventBus);
        ModParticleTypes.REGISTRY.register(modEventBus);
        ModMenus.REGISTRY.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModCuriosSlots.enqueueIMC(event);
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            event.enqueueWork(ModItemProperties::registerItemProperties);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class ModEventSubscriber {

        @SubscribeEvent
        public static void onEntityAttributeCreate(EntityAttributeCreationEvent event) {
            event.put(ModEntities.SPIDERLING.get(), SpiderlingEntity.createAttributes().build());
            event.put(ModEntities.GIANT_SPIDER.get(), GiantSpiderEntity.createAttributes().build());
            event.put(ModEntities.GHOUL.get(), GhoulEntity.createAttributes().build());
            event.put(ModEntities.DARK_SORCERER.get(), DarkSorcererEntity.createAttributes().build());
            event.put(ModEntities.BEREFT.get(), BereftEntity.createAttributes().build());
            event.put(ModEntities.GOBLIN_WARRIOR.get(), GoblinWarriorEntity.createAttributes().build());
            event.put(ModEntities.GOBLIN.get(), GoblinEntity.createAttributes().build());
        }

        @SubscribeEvent
        public static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        }
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            ModModelLayers.registerLayerDefinitions(event);
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntities.SPIDERLING.get(), SpiderlingRenderer::new);
            event.registerEntityRenderer(ModEntities.GIANT_SPIDER.get(), GiantSpiderRenderer::new);
            event.registerEntityRenderer(ModEntities.GOBLIN_WARRIOR.get(), GoblinWarriorRenderer::new);
            event.registerEntityRenderer(ModEntities.GOBLIN.get(), GoblinRenderer::new);
            event.registerEntityRenderer(ModEntities.GHOUL.get(), GhoulRenderer::new);
            event.registerEntityRenderer(ModEntities.DARK_SORCERER.get(), DarkSorcererRenderer::new);
            event.registerEntityRenderer(ModEntities.BEREFT.get(), BereftRenderer::new);

            event.registerEntityRenderer(ModEntities.ETHERBOLT.get(), EtherboltRenderer::new);
            event.registerEntityRenderer(ModEntities.FLAMEBOLT.get(), FlameboltRenderer::new);
            event.registerEntityRenderer(ModEntities.FROST_SPIKE.get(), FrostSpikeRenderer::new);
            event.registerEntityRenderer(ModEntities.FIREBALL.get(), FireBallRenderer::new);
            event.registerEntityRenderer(ModEntities.HELLBLAZE_ARROW.get(), HellblazeArrowRenderer::new);
            event.registerEntityRenderer(ModEntities.STRAIGHT_BEAM.get(), StraightBeamRenderer::new);
            event.registerEntityRenderer(ModEntities.JAGGED_BEAM.get(), JaggedBeamRenderer::new);
        }

        @SubscribeEvent
        public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
            event.register(ModKeyBinds.SPELL_SLOT_SELECT);
        }
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(MOD_ID);

        registrar.playBidirectional(
                BurnoutPayload.TYPE,
                BurnoutPayload.STREAM_CODEC,
                ModPayloadHandler::handleBurnout
        );

        registrar.playBidirectional(
                SpellSlotPayload.TYPE,
                SpellSlotPayload.STREAM_CODEC,
                ModPayloadHandler::handleSpellSlot
        );

        registrar.playBidirectional(
                SpellBookPayload.TYPE,
                SpellBookPayload.STREAM_CODEC,
                ModPayloadHandler::handleSpellBook
        );

        registrar.playBidirectional(
                SpellLuckPayload.TYPE,
                SpellLuckPayload.STREAM_CODEC,
                ModPayloadHandler::handleSpellLuck
        );

        registrar.playBidirectional(
                SpellweaverSwitchPayload.TYPE,
                SpellweaverSwitchPayload.STREAM_CODEC,
                ModPayloadHandler::handleSpellweaverSwitch
        );

        registrar.playBidirectional(
                FlightTimePayload.TYPE,
                FlightTimePayload.STREAM_CODEC,
                ModPayloadHandler::handleFlightTime
        );

        registrar.playBidirectional(
                FlyingPayload.TYPE,
                FlyingPayload.STREAM_CODEC,
                ModPayloadHandler::handleFlying
        );

        registrar.playBidirectional(
                FullSyncPayload.TYPE,
                FullSyncPayload.STREAM_CODEC,
                ModPayloadHandler::handleFullSync
        );

//        registrar.playBidirectional(
//                GnawerGuiButtonPayload.TYPE,
//                GnawerGuiButtonPayload.STREAM_CODEC,
//                ModPayloadHandler::handleGnawerGuiButton
//        );
//
//        registrar.playBidirectional(
//                SpellweaverTablePayload.TYPE,
//                SpellweaverTablePayload.STREAM_CODEC,
//                ModPayloadHandler::handleSpellweaverTable
//        );
//
//        registrar.playBidirectional(
//                VoidMawPayload.TYPE,
//                VoidMawPayload.STREAM_CODEC,
//                ModPayloadHandler::handleVoidMaw
//        );
    }
}