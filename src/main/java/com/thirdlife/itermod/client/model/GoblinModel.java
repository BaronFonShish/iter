package com.thirdlife.itermod.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

public class GoblinModel<T extends Entity> extends EntityModel<T> implements ArmedModel {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("itermod", "goblin"), "main");
    private final ModelPart goblin;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart head;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public GoblinModel(ModelPart root) {
        this.goblin = root;
        this.body = root.getChild("body");
        this.head = this.body.getChild("head");
        this.rightLeg = root.getChild("rightLeg");
        this.leftLeg = root.getChild("leftLeg");
        this.rightArm = root.getChild("rightArm");
        this.leftArm = root.getChild("leftArm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 12).addBox(-2.5F, -5.0F, -1.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 21).addBox(-2.0F, -4.0F, 1.5F, 4.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(15, 21).addBox(-3.0F, -6.0F, 3.0F, 6.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(17, 12).addBox(-2.5F, -5.0F, -1.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 20.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -6.0F, -2.5F, 7.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(25, 8).addBox(3.5F, -4.0F, -0.5F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(28, 21).addBox(-6.5F, -4.0F, -0.5F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition rightLeg = partdefinition.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(9, 33).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 20.0F, 0.0F));

        PartDefinition leftLeg = partdefinition.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(18, 33).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 20.0F, 0.0F));

        PartDefinition rightArm = partdefinition.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(25, 0).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(15, 24).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offset(-2.5F, 16.5F, 0.0F));

        PartDefinition leftArm = partdefinition.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(24, 25).addBox(0.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 30).addBox(0.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offset(2.5F, 16.5F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
                          float headPitch) {
        this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
        this.head.xRot = headPitch / (180F / (float) Math.PI);
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.65F) * 1.0F * limbSwingAmount;
        this.rightArm.xRot = Mth.cos(limbSwing * 0.65F + (float) Math.PI) * limbSwingAmount + -2F * Mth.sin(attackTime * Mth.PI);
        this.rightArm.yRot = 0.75F * Mth.sin(attackTime * Mth.PI * 1.5F);
        this.leftArm.xRot = Mth.cos(limbSwing * 0.65F) * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.65F) * -1.0F * limbSwingAmount;
        this.leftArm.zRot = (Mth.sin(ageInTicks/16)/-20) - 0.1F;
        this.rightArm.zRot = (Mth.sin(ageInTicks/16)/20) + 0.1F;
    }

    protected ModelPart getArm(HumanoidArm pSide){
        return pSide == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
    }

    public void translateToHand(HumanoidArm Side, PoseStack PoseStack){
        ModelPart arm = this.getArm(Side);
        arm.translateAndRotate(PoseStack);
        PoseStack.scale(0.75f, 0.75f,0.75f);

        float handOffset = Side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
        PoseStack.translate(handOffset * 0.05F, -0.1F, 0.0F);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        goblin.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}