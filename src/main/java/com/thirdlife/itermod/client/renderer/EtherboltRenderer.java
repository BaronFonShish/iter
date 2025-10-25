package com.thirdlife.itermod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.thirdlife.itermod.client.model.EtherboltModel;
import com.thirdlife.itermod.common.entity.misc.EtherboltEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class EtherboltRenderer extends EntityRenderer<EtherboltEntity> {
    private static final ResourceLocation texture = new ResourceLocation("iter", "textures/entity/etherbolt.png");
    private final EtherboltModel model;

    public EtherboltRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new EtherboltModel(context.bakeLayer(EtherboltModel.LAYER_LOCATION));
    }

    @Override
    public void render(EtherboltEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));

        model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EtherboltEntity entity) {
        return texture;
    }
}
