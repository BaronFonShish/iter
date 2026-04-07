package com.malignant.itermod.client.renderer;

import com.malignant.itermod.client.model.BereftModel;
import com.malignant.itermod.client.model.DarkSorcererModel;
import com.malignant.itermod.common.entity.BereftEntity;
import com.malignant.itermod.common.entity.DarkSorcererEntity;
import com.malignant.itermod.iterMod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class BereftRenderer extends MobRenderer<BereftEntity, BereftModel<BereftEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(iterMod.MOD_ID, "textures/entity/bereft.png");

    public BereftRenderer(EntityRendererProvider.Context context) {
        super(context, new BereftModel<>(context.bakeLayer(BereftModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(BereftEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(BereftEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferIn, int packedLightIn) {
        poseStack.pushPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
        poseStack.popPose();
    }
}