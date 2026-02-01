package com.malignant.itermod.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.malignant.itermod.common.entity.DarkSorcererEntity;
import com.malignant.itermod.iterMod;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;

public class DarkSorcererModel<T extends Entity> extends EntityModel<T> implements ArmedModel {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(iterMod.MOD_ID, "dark_sorcerer"), "main");

    private final ModelPart waist;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart cape;

    public DarkSorcererModel(ModelPart root) {
        this.waist = root.getChild("waist");
        this.body = this.waist.getChild("body");
        this.head = root.getChild("head");
        this.rightArm = root.getChild("rightArm");
        this.leftArm = root.getChild("leftArm");
        this.rightLeg = root.getChild("rightLeg");
        this.leftLeg = root.getChild("leftLeg");
        this.cape = root.getChild("cape");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition waist = partdefinition.addOrReplaceChild("waist", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition Body = waist.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 14.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition Head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(24, 2).addBox(-2.0F, 0.0F, -6.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition RightArm = partdefinition.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(-1.9F, -2.1F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition LeftArm = partdefinition.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 32).mirror().addBox(-1.1F, -2.1F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition RightLeg = partdefinition.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

        PartDefinition LeftLeg = partdefinition.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.0F));

        PartDefinition Cape = partdefinition.addOrReplaceChild("cape", CubeListBuilder.create().texOffs(46, 45).addBox(-4.0F, -1.0F, -0.4F, 8.0F, 18.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 2.5F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
                          float headPitch) {

        this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.head.xRot = headPitch * Mth.DEG_TO_RAD;

        this.rightLeg.xRot = Mth.cos(limbSwing * 0.65F) * 1.0F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.65F) * -1.0F * limbSwingAmount;

        this.rightArm.xRot = Mth.cos(limbSwing * 0.65F + (float) Math.PI) * limbSwingAmount;
        this.leftArm.xRot = Mth.cos(limbSwing * 0.65F) * limbSwingAmount;
        this.rightArm.zRot = 0.0F;
        this.leftArm.zRot = 0.0F;
        this.rightArm.yRot = 0.0F;
        this.leftArm.yRot = 0.0F;

        if (entity instanceof DarkSorcererEntity mob){
            if (mob.isCasting()){
                if (mob.getMainArm() == HumanoidArm.RIGHT){
                    this.rightArm.xRot = (-(float) Math.PI / 2F) + headPitch * Mth.DEG_TO_RAD - 0.75F - Mth.sin(ageInTicks / 2) * 0.5f;
                    this.rightArm.yRot = -0.1F + netHeadYaw * Mth.DEG_TO_RAD + Mth.cos(ageInTicks / 2) * 0.25f;
                    this.rightArm.zRot = 0.0F;
                }
                else {
                    this.leftArm.xRot = (-(float) Math.PI / 2F) + headPitch * Mth.DEG_TO_RAD - 0.75F - Mth.cos(ageInTicks / 2) * 0.5f;
                    this.leftArm.yRot = 0.1F + netHeadYaw * Mth.DEG_TO_RAD + Mth.sin(ageInTicks / 2) * 0.25f;
                    this.leftArm.zRot = 0.0F;
                }
            }
        }

        AnimationUtils.bobArms(this.rightArm, this.leftArm, ageInTicks);
    }

    protected ModelPart getArm(HumanoidArm pSide){
        return pSide == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
    }

    public void translateToHand(HumanoidArm Side, PoseStack PoseStack){
        ModelPart arm = this.getArm(Side);
        arm.translateAndRotate(PoseStack);
        float handOffset = Side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
        PoseStack.translate(handOffset * 0.0625F, 0.0F, 0.0625F);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        waist.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        cape.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}