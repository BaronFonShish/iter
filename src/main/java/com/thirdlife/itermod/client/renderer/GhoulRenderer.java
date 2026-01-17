package com.thirdlife.itermod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.thirdlife.itermod.client.model.GhoulModel;
import com.thirdlife.itermod.common.entity.GhoulEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class GhoulRenderer extends MobRenderer<GhoulEntity, GhoulModel<GhoulEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("iter", "textures/entity/ghoul.png");

    public GhoulRenderer(EntityRendererProvider.Context context) {
        super(context, new GhoulModel<>(context.bakeLayer(GhoulModel.LAYER_LOCATION)), 0.4f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(GhoulEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(GhoulEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferIn, int packedLightIn) {
        poseStack.pushPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
        poseStack.popPose();
    }
}
