package com.thirdlife.itermod.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class GoblinWarriorModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("itermod", "goblin_warrior"), "main");
    private final ModelPart goblin;
    private final ModelPart body;
    public final ModelPart rightArm;
    public final ModelPart leftArm;
    private final ModelPart head;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public GoblinWarriorModel(ModelPart root) {
        this.goblin = root.getChild("goblin");
        this.body = this.goblin.getChild("body");
        this.rightArm = this.body.getChild("rightArm");
        this.leftArm = this.body.getChild("leftArm");
        this.head = this.body.getChild("head");
        this.rightLeg = this.goblin.getChild("rightLeg");
        this.leftLeg = this.goblin.getChild("leftLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition goblin = partdefinition.addOrReplaceChild("goblin", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = goblin.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 25).addBox(-2.5F, -6.0F, -1.5F, 5.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 40).addBox(-2.5F, -6.0F, -1.5F, 5.0F, 6.0F, 3.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(25, 9).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 34).addBox(-3.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -4.5F, 0.0F));

        PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(25, 9).addBox(0.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -4.5F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 13).addBox(-3.5F, -6.0F, -2.25F, 7.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.5F, -7.0F, -2.25F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.3F))
                .texOffs(26, 25).addBox(3.5F, -4.0F, -0.25F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(26, 28).addBox(-6.5F, -4.0F, -0.25F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, -0.25F));

        PartDefinition rightLeg = goblin.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(25, 18).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, -4.0F, 0.0F));

        PartDefinition leftLeg = goblin.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(25, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -4.0F, 0.0F));

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

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        goblin.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}