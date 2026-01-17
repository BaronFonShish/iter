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

public class GhoulModel<T extends Entity> extends EntityModel<T> implements ArmedModel {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("itermod", "ghoul"), "main");
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart arm_right;
	private final ModelPart arm_left;
	private final ModelPart leg_right;
	private final ModelPart leg_left;

	public GhoulModel(ModelPart root) {
		this.body = root.getChild("body");
		this.head = this.body.getChild("head");
		this.arm_right = this.body.getChild("arm_right");
		this.arm_left = this.body.getChild("arm_left");
		this.leg_right = root.getChild("leg_right");
		this.leg_left = root.getChild("leg_left");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(24, 16).addBox(-3.0F, -6.0F, -1.0F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(52, 37).addBox(-3.0F, 0.0F, -4.0F, 6.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(32, 6).addBox(-8.0F, -12.0F, 0.0F, 16.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 41).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition arm_right = body.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(12, 26).addBox(-3.0F, -1.0F, -1.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(34, 26).addBox(-3.0F, 11.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -11.0F, 0.0F));

		PartDefinition arm_left = body.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(12, 26).mirror().addBox(0.0F, -1.0F, -1.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(34, 26).mirror().addBox(1.0F, 11.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, -11.0F, 0.0F));

		PartDefinition leg_right = partdefinition.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(0, 26).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.5F));

		PartDefinition leg_left = partdefinition.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(0, 26).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

    protected ModelPart getArm(HumanoidArm pSide){
        return pSide == HumanoidArm.LEFT ? this.arm_left : this.arm_right;
    }

    public void translateToHand(HumanoidArm Side, PoseStack PoseStack){
        ModelPart arm = this.getArm(Side);
        arm.translateAndRotate(PoseStack);
        PoseStack.scale(0.75f, 0.75f,0.75f);

        float handOffset = Side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
        PoseStack.translate(handOffset * 0.05F, 0F, 0.0F);
    }

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = (netHeadYaw / (180F / (float) Math.PI)) * 0.8f;
        this.head.xRot = (headPitch / (180F / (float) Math.PI));

        this.body.xRot = ((2.5F * Mth.PI / 180) + (10F * Mth.PI / 180) * limbSwingAmount) - (Mth.sin(attackTime * Mth.PI * 2) * 0.5f);
        this.body.yRot = (netHeadYaw / (180F / (float) Math.PI)) * 0.2f;

        this.arm_right.xRot = Mth.cos(limbSwing * 0.65F) * -0.35f * limbSwingAmount - 2F * Mth.sin(attackTime * Mth.PI) + limbSwingAmount;
        this.arm_left.xRot = Mth.cos(limbSwing * 0.65F) * 0.35f * limbSwingAmount - 2F * Mth.sin(attackTime * Mth.PI) + limbSwingAmount;

        this.arm_right.yRot = 0.75F * Mth.sin(attackTime * Mth.PI * 1.5F);
        this.arm_left.yRot = -0.75F * Mth.sin(attackTime * Mth.PI * 1.5F);

        this.leg_right.xRot = Mth.cos(limbSwing * 0.65F) * 1.0F * limbSwingAmount;
        this.leg_left.xRot = Mth.cos(limbSwing * 0.65F) * -1.0F * limbSwingAmount;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leg_right.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leg_left.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}