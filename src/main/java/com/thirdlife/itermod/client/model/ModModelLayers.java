package com.thirdlife.itermod.client.model;

import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)

public class ModModelLayers {
    public static void registerLayerDefinitions(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SpiderlingModel.LAYER_LOCATION, SpiderlingModel::createBodyLayer);
        event.registerLayerDefinition(GiantSpiderModel.LAYER_LOCATION, GiantSpiderModel::createBodyLayer);
        event.registerLayerDefinition(GhoulModel.LAYER_LOCATION, GhoulModel::createBodyLayer);
        event.registerLayerDefinition(GoblinWarriorModel.LAYER_LOCATION, GoblinWarriorModel::createBodyLayer);
        event.registerLayerDefinition(GoblinModel.LAYER_LOCATION, GoblinModel::createBodyLayer);

        event.registerLayerDefinition(EtherboltModel.LAYER_LOCATION, EtherboltModel::createBodyLayer);
        event.registerLayerDefinition(FlameboltModel.LAYER_LOCATION, FlameboltModel::createBodyLayer);
        event.registerLayerDefinition(FrostSpikeModel.LAYER_LOCATION, FrostSpikeModel::createBodyLayer);
        event.registerLayerDefinition(GenericArrowModel.LAYER_LOCATION, GenericArrowModel::createBodyLayer);
    }
}