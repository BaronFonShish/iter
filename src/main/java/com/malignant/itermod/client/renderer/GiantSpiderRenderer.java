package com.malignant.itermod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.malignant.itermod.client.model.GiantSpiderModel;
import com.malignant.itermod.common.entity.GiantSpiderEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class GiantSpiderRenderer extends MobRenderer<GiantSpiderEntity, GiantSpiderModel<GiantSpiderEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("iter", "textures/entity/giant_spider.png");
    private static final ResourceLocation TEXTURE_EMISSIVE = new ResourceLocation("iter", "textures/entity/giant_spider_emissive.png");

    public GiantSpiderRenderer(EntityRendererProvider.Context context) {
        super(context, new GiantSpiderModel<>(context.bakeLayer(GiantSpiderModel.LAYER_LOCATION)), 1.2f);
        this.addLayer(new RenderLayer<>(this) {

            @Override
            public void render(PoseStack poseStack, MultiBufferSource bufferSource, int light, GiantSpiderEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
                VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.eyes(TEXTURE_EMISSIVE));
                this.getParentModel().renderToBuffer(poseStack, vertexConsumer, light, LivingEntityRenderer.getOverlayCoords(entity, 0), 1, 1, 1, 1);
            }
        });
    }

    @Override
    public ResourceLocation getTextureLocation(GiantSpiderEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(GiantSpiderEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(1.1f, 1.1f, 1.1f);
    }

    @Override
    public void render(GiantSpiderEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferIn, int packedLightIn) {

        poseStack.pushPose();

        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
        poseStack.popPose();
    }
}
