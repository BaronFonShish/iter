package com.malignant.itermod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.malignant.itermod.client.model.GoblinWarriorModel;
import com.malignant.itermod.common.entity.GoblinWarriorEntity;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;

import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;


public class GoblinWarriorRenderer extends MobRenderer<GoblinWarriorEntity, GoblinWarriorModel<GoblinWarriorEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("iter", "textures/entity/goblin_warrior.png");

    public GoblinWarriorRenderer(EntityRendererProvider.Context context) {
        super(context, new GoblinWarriorModel<>(context.bakeLayer(GoblinWarriorModel.LAYER_LOCATION)), 0.3f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(GoblinWarriorEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(GoblinWarriorEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferIn, int packedLightIn) {
        poseStack.pushPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
        poseStack.popPose();
    }
}