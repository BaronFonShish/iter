package com.thirdlife.itermod;

import com.mojang.logging.LogUtils;
import com.thirdlife.itermod.client.model.SpiderlingModel;
import com.thirdlife.itermod.common.entity.SpiderlingEntity;
import com.thirdlife.itermod.common.registry.*;
import com.thirdlife.itermod.client.renderer.SpiderlingRenderer;
import com.thirdlife.itermod.common.variables.EtherBurnoutPacket;
import com.thirdlife.itermod.common.variables.SpellSlotPacket;
import com.thirdlife.itermod.common.variables.SpellSlotPacketServer;
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
        ModEnchantments.ENCHANTMENTS.register(modEventBus);
        ModEntities.register(modEventBus);
        ModFeatures.REGISTRY.register(modEventBus);
        ModAttributes.ATTRIBUTES.register(modEventBus);
        modEventBus.addListener(ModCapabilities::register);
        ModMenus.REGISTRY.register(modEventBus);

        int id = 0;
        PACKET_HANDLER.registerMessage(id++, EtherBurnoutPacket.class,
                EtherBurnoutPacket::toBytes, EtherBurnoutPacket::new, EtherBurnoutPacket::handle);

        PACKET_HANDLER.registerMessage(id++, SpellSlotPacket.class,
                SpellSlotPacket::toBytes, SpellSlotPacket::new, SpellSlotPacket::handle);

        PACKET_HANDLER.registerMessage(id++, SpellSlotPacketServer.class,
                SpellSlotPacketServer::toBytes, SpellSlotPacketServer::new, SpellSlotPacketServer::handle);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModItemProperties.RegisterItemProperties();
    }

    @Mod.EventBusSubscriber(modid = iterMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventSubscriber {
        @SubscribeEvent
        public static void onAttributeCreate(EntityAttributeCreationEvent event) {
            event.put(ModEntities.SPIDERLING.get(), SpiderlingEntity.createAttributes().build());
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(modid = iterMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {

            event.registerLayerDefinition(SpiderlingModel.LAYER_LOCATION, SpiderlingModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {

            event.registerEntityRenderer(ModEntities.SPIDERLING.get(), context ->
                    new SpiderlingRenderer(context)
            );
        }

        @SubscribeEvent
        public static void onRegisterKeymappings(RegisterKeyMappingsEvent event) {
            event.register(ModKeyBinds.SPELL_SLOT_SELECT);
        }
    }
}
