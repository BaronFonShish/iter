package com.malignant.iter.client.model;

import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class ModModelLayers {
    public static void registerLayerDefinitions(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SpiderlingModel.LAYER_LOCATION, SpiderlingModel::createBodyLayer);
        event.registerLayerDefinition(GiantSpiderModel.LAYER_LOCATION, GiantSpiderModel::createBodyLayer);
        event.registerLayerDefinition(GhoulModel.LAYER_LOCATION, GhoulModel::createBodyLayer);
        event.registerLayerDefinition(DarkSorcererModel.LAYER_LOCATION, DarkSorcererModel::createBodyLayer);
        event.registerLayerDefinition(BereftModel.LAYER_LOCATION, BereftModel::createBodyLayer);
        event.registerLayerDefinition(GoblinWarriorModel.LAYER_LOCATION, GoblinWarriorModel::createBodyLayer);
        event.registerLayerDefinition(GoblinModel.LAYER_LOCATION, GoblinModel::createBodyLayer);

        event.registerLayerDefinition(EtherboltModel.LAYER_LOCATION, EtherboltModel::createBodyLayer);
        event.registerLayerDefinition(FlameboltModel.LAYER_LOCATION, FlameboltModel::createBodyLayer);
        event.registerLayerDefinition(FrostSpikeModel.LAYER_LOCATION, FrostSpikeModel::createBodyLayer);
        event.registerLayerDefinition(FireballModel.LAYER_LOCATION, FireballModel::createBodyLayer);
        event.registerLayerDefinition(GenericArrowModel.LAYER_LOCATION, GenericArrowModel::createBodyLayer);
    }
}