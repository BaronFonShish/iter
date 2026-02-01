package com.malignant.itermod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.malignant.itermod.client.model.SpiderlingModel;
import com.malignant.itermod.common.entity.SpiderlingEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SpiderlingRenderer extends MobRenderer<SpiderlingEntity, SpiderlingModel<SpiderlingEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("iter", "textures/entity/spiderling.png");

    public SpiderlingRenderer(EntityRendererProvider.Context context) {
        super(context, new SpiderlingModel<>(context.bakeLayer(SpiderlingModel.LAYER_LOCATION)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(SpiderlingEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(SpiderlingEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferIn, int packedLightIn) {

        poseStack.pushPose();

        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
        poseStack.popPose();
    }
}
