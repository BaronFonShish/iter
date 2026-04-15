package com.malignant.itermod.client.renderer;

import com.malignant.itermod.client.model.BereftModel;
import com.malignant.itermod.common.entity.BereftEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;

public class BereftArmorLayer extends RenderLayer<BereftEntity, BereftModel<BereftEntity>> {

    private final HumanoidModel<BereftEntity> innerModel;
    private final HumanoidModel<BereftEntity> outerModel;

    public BereftArmorLayer(LivingEntityRenderer<BereftEntity, BereftModel<BereftEntity>> renderer,
                            HumanoidModel<BereftEntity> innerModel,
                            HumanoidModel<BereftEntity> outerModel) {
        super(renderer);
        this.innerModel = innerModel;
        this.outerModel = outerModel;

        this.innerModel.young = false;
        this.outerModel.young = false;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                       BereftEntity entity, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        BereftModel<BereftEntity> parentModel = getParentModel();

        copyModelAngles(parentModel, innerModel);
        copyModelAngles(parentModel, outerModel);

        poseStack.pushPose();

        float rootZRot = parentModel.getRootZRot();
        if (rootZRot != 0) {
            poseStack.mulPose(com.mojang.math.Axis.ZP.rotation(rootZRot));
        }

        float rootYOffset = parentModel.getRootYOffset();
        if (rootYOffset != 0) {
            poseStack.translate(0.0F, rootYOffset, 0.0F);
        }

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                ItemStack stack = entity.getItemBySlot(slot);
                if (stack.getItem() instanceof ArmorItem armorItem) {
                    HumanoidModel<BereftEntity> model = getArmorModel(armorItem);

                    setAllPartsVisibility(model, false);
                    setSlotVisibility(model, slot);

                    if (slot == EquipmentSlot.HEAD) {
                        poseStack.pushPose();
                        poseStack.scale(1.0F, 1.125F, 1.0F);
                        poseStack.translate(0.0F, -0.0625F, 0.0F);
                    }

                    ResourceLocation texture = getArmorTexture(stack, slot);
                    VertexConsumer vertexConsumer;

                    if (armorItem.getMaterial() == net.minecraft.world.item.ArmorMaterials.LEATHER) {
                        int color = ((DyeableLeatherItem) armorItem).getColor(stack);
                        float r = ((color >> 16) & 0xFF) / 255.0F;
                        float g = ((color >> 8) & 0xFF) / 255.0F;
                        float b = (color & 0xFF) / 255.0F;

                        vertexConsumer = buffer.getBuffer(RenderType.armorCutoutNoCull(texture));
                        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY,
                                r, g, b, 1.0F);
                    } else {
                        vertexConsumer = ItemRenderer.getArmorFoilBuffer(
                                buffer, RenderType.armorCutoutNoCull(texture),
                                false, stack.hasFoil()
                        );
                        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY,
                                1.0F, 1.0F, 1.0F, 1.0F);
                    }

                    if (slot == EquipmentSlot.HEAD) {
                        poseStack.popPose();
                    }
                }
            }
        }

        poseStack.popPose();
    }

    private void setAllPartsVisibility(HumanoidModel<BereftEntity> model, boolean visible) {
        model.head.visible = visible;
        model.hat.visible = visible;
        model.body.visible = visible;
        model.rightArm.visible = visible;
        model.leftArm.visible = visible;
        model.rightLeg.visible = visible;
        model.leftLeg.visible = visible;
    }

    private void setSlotVisibility(HumanoidModel<BereftEntity> model, EquipmentSlot slot) {
        switch (slot) {
            case HEAD -> {
                model.head.visible = true;
                model.hat.visible = true;
            }
            case CHEST -> {
                model.body.visible = true;
                model.rightArm.visible = true;
                model.leftArm.visible = true;
            }
            case LEGS -> {
                model.body.visible = true;
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
            }
            case FEET -> {
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
            }
            default -> {}
        }
    }

    private HumanoidModel<BereftEntity> getArmorModel(ArmorItem armorItem) {
        return armorItem.getType() == ArmorItem.Type.LEGGINGS ? innerModel : outerModel;
    }

    private void copyModelAngles(BereftModel<BereftEntity> source, HumanoidModel<BereftEntity> target) {
        target.head.xRot = source.getHead().xRot;
        target.head.yRot = source.getHead().yRot;
        target.head.zRot = source.getHead().zRot;

        target.body.xRot = source.getBody().xRot;
        target.body.yRot = source.getBody().yRot;
        target.body.zRot = source.getBody().zRot;

        target.leftArm.xRot = source.getLeftArm().xRot;
        target.leftArm.yRot = source.getLeftArm().yRot;
        target.leftArm.zRot = source.getLeftArm().zRot;

        target.rightArm.xRot = source.getRightArm().xRot;
        target.rightArm.yRot = source.getRightArm().yRot;
        target.rightArm.zRot = source.getRightArm().zRot;

        target.leftLeg.xRot = source.getLeftLeg().xRot;
        target.leftLeg.yRot = source.getLeftLeg().yRot;
        target.leftLeg.zRot = source.getLeftLeg().zRot;

        target.rightLeg.xRot = source.getRightLeg().xRot;
        target.rightLeg.yRot = source.getRightLeg().yRot;
        target.rightLeg.zRot = source.getRightLeg().zRot;
    }

    private ResourceLocation getArmorTexture(ItemStack stack, EquipmentSlot slot) {
        ArmorItem item = (ArmorItem) stack.getItem();
        String texture = item.getMaterial().getName();

        if (item.getMaterial() == net.minecraft.world.item.ArmorMaterials.LEATHER) {
            String domain = "minecraft";
            String path = "textures/models/armor/leather_layer_" +
                    (slot == EquipmentSlot.LEGS ? 2 : 1) + ".png";
            return new ResourceLocation(domain, path);
        }

        String domain = "minecraft";
        String path = "textures/models/armor/" + texture + "_layer_" +
                (slot == EquipmentSlot.LEGS ? 2 : 1) + ".png";
        return new ResourceLocation(domain, path);
    }
}