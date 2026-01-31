package com.thirdlife.itermod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.thirdlife.itermod.client.model.FlameboltModel;
import com.thirdlife.itermod.common.entity.misc.FlameboltEntity;
import com.thirdlife.itermod.iterMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class FlameboltRenderer extends EntityRenderer<FlameboltEntity> {
    private static final ResourceLocation texture = new ResourceLocation(iterMod.MOD_ID, "textures/entity/flamebolt.png");
    private final FlameboltModel model;

    public FlameboltRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new FlameboltModel(context.bakeLayer(FlameboltModel.LAYER_LOCATION));
    }

    @Override
    public void render(FlameboltEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
        poseStack.pushPose();
        poseStack.scale(1.15f, 1.15f, 1.15f);
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90));
        poseStack.mulPose(Axis.ZP.rotationDegrees(90 + Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
        model.setupAnim(entityIn, 0, 0, entityIn.tickCount + partialTicks, 0, 0);
        model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        poseStack.popPose();
        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(FlameboltEntity entity) {
        return texture;
    }
}
