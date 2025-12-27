package com.thirdlife.itermod;

import com.mojang.logging.LogUtils;
import com.thirdlife.itermod.client.model.ModModelLayers;
import com.thirdlife.itermod.client.renderer.*;
import com.thirdlife.itermod.common.entity.GiantSpiderEntity;
import com.thirdlife.itermod.common.entity.GoblinEntity;
import com.thirdlife.itermod.common.entity.GoblinWarriorEntity;
import com.thirdlife.itermod.common.entity.SpiderlingEntity;
import com.thirdlife.itermod.common.registry.*;
import com.thirdlife.itermod.common.variables.IterPlayerDataPacket;
import com.thirdlife.itermod.world.gui.SpellweaverTablePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;

import static com.ibm.icu.lang.UCharacter.GraphemeClusterBreak.T;


@Mod(iterMod.MOD_ID)
public class iterMod {


    public static final String MOD_ID = "iter";

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("iter", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );


    public iterMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(ModScreens::clientLoad);
        MinecraftForge.EVENT_BUS.register(this);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.REGISTRY.register(modEventBus);
        ModEnchantments.ENCHANTMENTS.register(modEventBus);
        ModEntities.register(modEventBus);
        ModTabs.REGISTRY.register(modEventBus);
        ModFeatures.REGISTRY.register(modEventBus);
        ModAttributes.ATTRIBUTES.register(modEventBus);
        ModParticleTypes.REGISTRY.register(modEventBus);
        modEventBus.addListener(ModCapabilities::register);
        ModMenus.REGISTRY.register(modEventBus);


        int id = 0;
        PACKET_HANDLER.registerMessage(id++, IterPlayerDataPacket.class,
                IterPlayerDataPacket::encode, IterPlayerDataPacket::decode,
                IterPlayerDataPacket::handle);

        PACKET_HANDLER.registerMessage(id++, SpellweaverTablePacket.class,
                SpellweaverTablePacket::encode, SpellweaverTablePacket::decode,
                SpellweaverTablePacket::handle);
        }



    private void commonSetup(final FMLCommonSetupEvent event) {
        ModItemProperties.RegisterItemProperties();

        event.enqueueWork(() -> {
            ModCuriosSlots.enqueueIMC(event);
        });

    }

    @Mod.EventBusSubscriber(modid = iterMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventSubscriber {
        @SubscribeEvent
        public static void onAttributeCreate(EntityAttributeCreationEvent event) {
            event.put(ModEntities.SPIDERLING.get(), SpiderlingEntity.createAttributes().build());
            event.put(ModEntities.GIANT_SPIDER.get(), GiantSpiderEntity.createAttributes().build());
            event.put(ModEntities.GOBLIN_WARRIOR.get(), GoblinWarriorEntity.createAttributes().build());
            event.put(ModEntities.GOBLIN.get(), GoblinEntity.createAttributes().build());
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(modid = iterMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            ModModelLayers.registerLayerDefinitions(event);
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {

            event.registerEntityRenderer(ModEntities.SPIDERLING.get(), SpiderlingRenderer::new);
            event.registerEntityRenderer(ModEntities.GIANT_SPIDER.get(), GiantSpiderRenderer::new);
            event.registerEntityRenderer(ModEntities.ETHERBOLT.get(), EtherboltRenderer::new);
            event.registerEntityRenderer(ModEntities.GOBLIN_WARRIOR.get(), GoblinWarriorRenderer::new);
            event.registerEntityRenderer(ModEntities.GOBLIN.get(), GoblinRenderer::new);
            event.registerEntityRenderer(ModEntities.FROST_SPIKE.get(), FrostSpikeRenderer::new);
        }

        @SubscribeEvent
        public static void onRegisterKeymappings(RegisterKeyMappingsEvent event) {
            event.register(ModKeyBinds.SPELL_SLOT_SELECT);
        }
    }
}
