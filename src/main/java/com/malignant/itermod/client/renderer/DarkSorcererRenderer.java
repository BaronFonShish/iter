package com.malignant.itermod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.malignant.itermod.client.model.DarkSorcererModel;
import com.malignant.itermod.common.entity.DarkSorcererEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class DarkSorcererRenderer extends MobRenderer<DarkSorcererEntity, DarkSorcererModel<DarkSorcererEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("iter", "textures/entity/dark_sorcerer.png");

    public DarkSorcererRenderer(EntityRendererProvider.Context context) {
        super(context, new DarkSorcererModel<>(context.bakeLayer(DarkSorcererModel.LAYER_LOCATION)), 0.4f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(DarkSorcererEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(DarkSorcererEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferIn, int packedLightIn) {
        poseStack.pushPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
        poseStack.popPose();
    }
}
