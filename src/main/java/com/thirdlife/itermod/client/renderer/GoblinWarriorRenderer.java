package com.thirdlife.itermod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.thirdlife.itermod.client.model.GoblinWarriorModel;
import com.thirdlife.itermod.common.entity.GoblinWarriorEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.HumanoidArm;

public class GoblinWarriorRenderer extends MobRenderer<GoblinWarriorEntity, GoblinWarriorModel<GoblinWarriorEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("iter", "textures/entity/goblin_warrior.png");

    public GoblinWarriorRenderer(EntityRendererProvider.Context context) {
        super(context, new GoblinWarriorModel<>(context.bakeLayer(GoblinWarriorModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(GoblinWarriorEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void setupRotations(GoblinWarriorEntity entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);
    }

    // Add method to render held items
    protected void renderHandWithItem(GoblinWarriorEntity entity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (!itemStack.isEmpty()) {
            poseStack.pushPose();

            if (arm == HumanoidArm.RIGHT) {
                this.model.rightArm.translateAndRotate(poseStack);
            } else {
                this.model.leftArm.translateAndRotate(poseStack);
            }

            // Adjust position and rotation for the item
            poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

            boolean isRightHand = arm == HumanoidArm.RIGHT;
            poseStack.translate(
                    (isRightHand ? -1.0F : 1.0F) / 16.0F,
                    0.125F,
                    -0.625F
            );

            Minecraft.getInstance().getItemRenderer().renderStatic(
                    entity,
                    itemStack,
                    displayContext,
                    isRightHand,
                    poseStack,
                    buffer,
                    entity.level(),
                    packedLight,
                    packedLight,
                    0
            );

            poseStack.popPose();
        }
    }

    @Override
    public void render(GoblinWarriorEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        this.model.attackTime = this.getAttackAnim(entity, partialTicks);

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);

        poseStack.pushPose();

        ItemStack mainHandItem = entity.getMainHandItem();
        if (!mainHandItem.isEmpty()) {
            this.renderHandWithItem(entity, mainHandItem, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, buffer, packedLight);
        }

        ItemStack offHandItem = entity.getOffhandItem();
        if (!offHandItem.isEmpty()) {
            this.renderHandWithItem(entity, offHandItem, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, poseStack, buffer, packedLight);
        }
        poseStack.popPose();
    }
}