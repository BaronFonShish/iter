package com.thirdlife.itermod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.thirdlife.itermod.client.model.EtherboltModel;
import com.thirdlife.itermod.client.model.FrostSpikeModel;
import com.thirdlife.itermod.common.entity.misc.EtherboltEntity;
import com.thirdlife.itermod.common.entity.misc.FrostSpikeEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class FrostSpikeRenderer extends EntityRenderer<FrostSpikeEntity> {
    private static final ResourceLocation texture = new ResourceLocation("iter", "textures/entity/frost_spike.png");
    private final FrostSpikeModel model;

    public FrostSpikeRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new FrostSpikeModel(context.bakeLayer(FrostSpikeModel.LAYER_LOCATION));
    }

    @Override
    public void render(FrostSpikeEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90));
        poseStack.mulPose(Axis.ZP.rotationDegrees(90 + Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
        model.setupAnim(entityIn, 0, 0, entityIn.tickCount + partialTicks, 0, 0);
        model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        poseStack.popPose();
        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(FrostSpikeEntity entity) {
        return texture;
    }
}
