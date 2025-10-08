package com.thirdlife.itermod;

import com.mojang.logging.LogUtils;
import com.thirdlife.itermod.client.model.SpiderlingModel;
import com.thirdlife.itermod.common.entity.SpiderlingEntity;
import com.thirdlife.itermod.common.registry.ModBlocks;
import com.thirdlife.itermod.common.registry.ModEntities;
import com.thirdlife.itermod.common.registry.ModItems;
import com.thirdlife.itermod.common.registry.ModFeatures;
import com.thirdlife.itermod.client.renderer.SpiderlingRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;



@Mod(iterMod.MOD_ID)
public class iterMod {


    public static final String MOD_ID = "iter";

    private static final Logger LOGGER = LogUtils.getLogger();


    public iterMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModFeatures.REGISTRY.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

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

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = iterMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            // Spiderling
            event.registerLayerDefinition(SpiderlingModel.LAYER_LOCATION, SpiderlingModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            // Spiderling
            event.registerEntityRenderer(ModEntities.SPIDERLING.get(), context ->
                    new SpiderlingRenderer(context)
            );
        }
    }

}
