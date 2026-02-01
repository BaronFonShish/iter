package com.malignant.itermod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.malignant.itermod.client.model.GoblinModel;
import com.malignant.itermod.common.entity.GoblinEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;


public class GoblinRenderer extends MobRenderer<GoblinEntity, GoblinModel<GoblinEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("iter", "textures/entity/goblin.png");

    public GoblinRenderer(EntityRendererProvider.Context context) {
        super(context, new GoblinModel<>(context.bakeLayer(GoblinModel.LAYER_LOCATION)), 0.3f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(GoblinEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(GoblinEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferIn, int packedLightIn) {
        poseStack.pushPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
        poseStack.popPose();
    }
}