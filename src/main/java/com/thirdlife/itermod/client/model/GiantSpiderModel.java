package com.thirdlife.itermod.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thirdlife.itermod.client.animations.SpiderlingAnimationDefinitions;
import com.thirdlife.itermod.common.entity.GiantSpiderEntity;
import com.thirdlife.itermod.common.entity.SpiderlingEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;


public class GiantSpiderModel<T extends GiantSpiderEntity> extends HierarchicalModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation("itermod", "giant_spider"), "main");

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart belly;
    private final ModelPart left_chelicerae;
    private final ModelPart right_chelicerae;
    private final ModelPart hair;
    private final ModelPart hair2;
    private final ModelPart hair3;
    private final ModelPart hair4;
    private final ModelPart hair5;
    private final ModelPart hair6;
    private final ModelPart left_leg_1;
    private final ModelPart left_leg_2;
    private final ModelPart left_leg_3;
    private final ModelPart left_leg_4;
    private final ModelPart right_leg_1;
    private final ModelPart right_leg_2;
    private final ModelPart right_leg_3;
    private final ModelPart right_leg_4;

    public GiantSpiderModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.belly = this.body.getChild("belly");
        this.left_chelicerae = this.body.getChild("left_chelicerae");
        this.right_chelicerae = this.body.getChild("right_chelicerae");
        this.hair = this.body.getChild("hair");
        this.hair2 = this.body.getChild("hair2");
        this.hair3 = this.body.getChild("hair3");
        this.hair4 = this.body.getChild("hair4");
        this.hair5 = this.body.getChild("hair5");
        this.hair6 = this.body.getChild("hair6");
        this.left_leg_1 = this.root.getChild("left_leg_1");
        this.left_leg_2 = this.root.getChild("left_leg_2");
        this.left_leg_3 = this.root.getChild("left_leg_3");
        this.left_leg_4 = this.root.getChild("left_leg_4");
        this.right_leg_1 = this.root.getChild("right_leg_1");
        this.right_leg_2 = this.root.getChild("right_leg_2");
        this.right_leg_3 = this.root.getChild("right_leg_3");
        this.right_leg_4 = this.root.getChild("right_leg_4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 13.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 28).addBox(-6.0F, -9.0F, -9.0F, 11.0F, 9.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(48, 28).addBox(-6.0F, -9.0F, -9.0F, 11.0F, 9.0F, 13.0F, new CubeDeformation(0.25F)), PartPose.offset(0.5F, 0.0F, 0.0F));

        PartDefinition belly = body.addOrReplaceChild("belly", CubeListBuilder.create().texOffs(0, 0).addBox(-7.5F, -5.5F, -0.5F, 15.0F, 11.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -6.5F, 3.5F, -0.1745F, 0.0F, 0.0F));

        PartDefinition left_chelicerae = body.addOrReplaceChild("left_chelicerae", CubeListBuilder.create().texOffs(44, 50).addBox(-0.5F, 0.0F, -1.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 55).addBox(-0.5F, 5.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.5F, -4.0F, -10.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition right_chelicerae = body.addOrReplaceChild("right_chelicerae", CubeListBuilder.create().texOffs(44, 50).mirror().addBox(-3.5F, 0.0F, -1.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 55).mirror().addBox(-3.5F, 5.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -4.0F, -10.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition hair = body.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(1, 32).mirror().addBox(0.0F, -6.0F, -1.0F, 0.0F, 6.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(2.0F, -8.25F, -3.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition hair2 = body.addOrReplaceChild("hair2", CubeListBuilder.create().texOffs(1, 32).addBox(0.0F, -6.0F, -1.0F, 0.0F, 6.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-3.0F, -8.25F, -3.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition hair3 = body.addOrReplaceChild("hair3", CubeListBuilder.create().texOffs(6, 33).addBox(0.0F, -5.0F, -1.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-3.0F, -7.25F, 0.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition hair4 = body.addOrReplaceChild("hair4", CubeListBuilder.create().texOffs(6, 33).mirror().addBox(0.0F, -5.0F, -1.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(2.0F, -7.25F, 0.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition hair5 = body.addOrReplaceChild("hair5", CubeListBuilder.create().texOffs(6, 32).mirror().addBox(0.0F, -6.0F, -1.0F, 0.0F, 6.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(2.0F, -8.25F, -6.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition hair6 = body.addOrReplaceChild("hair6", CubeListBuilder.create().texOffs(6, 32).addBox(0.0F, -6.0F, -1.0F, 0.0F, 6.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-3.0F, -8.25F, -6.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition left_leg_1 = root.addOrReplaceChild("left_leg_1", CubeListBuilder.create(), PartPose.offsetAndRotation(1.5F, -1.0F, -2.5F, 0.3491F, 0.4363F, 0.6981F));

        PartDefinition leg_r1 = left_leg_1.addOrReplaceChild("leg_r1", CubeListBuilder.create().texOffs(0, 50).addBox(-1.0F, -2.0F, -2.0F, 19.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 1.0F, 0.5F, 0.0F, 0.0F, 0.0436F));

        PartDefinition left_leg_2 = root.addOrReplaceChild("left_leg_2", CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, -1.0F, -0.5F, 0.0873F, 0.0873F, 0.6109F));

        PartDefinition leg_r2 = left_leg_2.addOrReplaceChild("leg_r2", CubeListBuilder.create().texOffs(0, 50).addBox(-1.0F, -2.0F, -2.0F, 19.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 1.0F, 0.5F, 0.0F, 0.0F, 0.0436F));

        PartDefinition left_leg_3 = root.addOrReplaceChild("left_leg_3", CubeListBuilder.create().texOffs(0, 50).addBox(-0.5F, -1.0F, -1.5F, 19.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -1.0F, 1.5F, -0.0524F, -0.2094F, 0.6981F));

        PartDefinition left_leg_4 = root.addOrReplaceChild("left_leg_4", CubeListBuilder.create().texOffs(0, 50).addBox(-0.5F, -1.0F, -1.5F, 19.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -1.0F, 3.5F, -0.6109F, -0.5236F, 0.8727F));

        PartDefinition right_leg_1 = root.addOrReplaceChild("right_leg_1", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.5F, -1.0F, -2.5F, 0.3491F, -0.4363F, -0.6981F));

        PartDefinition leg_r3 = right_leg_1.addOrReplaceChild("leg_r3", CubeListBuilder.create().texOffs(0, 50).mirror().addBox(-18.0F, -2.0F, -2.0F, 19.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 1.0F, 0.5F, 0.0F, 0.0F, -0.0436F));

        PartDefinition right_leg_2 = root.addOrReplaceChild("right_leg_2", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5F, -1.0F, -0.5F, 0.0873F, -0.0873F, -0.6109F));

        PartDefinition leg_r4 = right_leg_2.addOrReplaceChild("leg_r4", CubeListBuilder.create().texOffs(0, 50).mirror().addBox(-18.0F, -2.0F, -2.0F, 19.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 1.0F, 0.5F, 0.0F, 0.0F, -0.0436F));

        PartDefinition right_leg_3 = root.addOrReplaceChild("right_leg_3", CubeListBuilder.create().texOffs(0, 50).mirror().addBox(-18.5F, -1.0F, -1.5F, 19.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, -1.0F, 1.5F, -0.0524F, 0.2094F, -0.6981F));

        PartDefinition right_leg_4 = root.addOrReplaceChild("right_leg_4", CubeListBuilder.create().texOffs(0, 50).mirror().addBox(-18.5F, -1.0F, -1.5F, 19.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -1.0F, 3.5F, -0.6109F, 0.5236F, -0.8727F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        applyHeadRotation(netHeadYaw, headPitch);

        this.right_leg_1.yRot = (-35 * Mth.PI / 180) +Mth.cos(limbSwing) * 0.5F * limbSwingAmount;
        this.right_leg_1.zRot = (-40 * Mth.PI / 180) + Math.max(0, -Mth.sin(limbSwing) * -0.75F * limbSwingAmount);

        this.right_leg_2.yRot = (-10 * Mth.PI / 180) -Mth.cos(limbSwing) * 0.5F * limbSwingAmount;
        this.right_leg_2.zRot = (-35 * Mth.PI / 180) +Math.max(0, Mth.sin(limbSwing) * -0.75F * limbSwingAmount);

        this.right_leg_3.yRot = (10 * Mth.PI / 180) +Mth.cos(limbSwing) * 0.5F * limbSwingAmount;
        this.right_leg_3.zRot = (-40 * Mth.PI / 180) + Math.max(0, -Mth.sin(limbSwing) * -0.75F * limbSwingAmount);

        this.right_leg_4.yRot = (35 * Mth.PI / 180) -Mth.cos(limbSwing) * 0.5F * limbSwingAmount;
        this.right_leg_4.zRot = (-50 * Mth.PI / 180) +Math.max(0, Mth.sin(limbSwing) * -0.75F * limbSwingAmount);

        this.left_leg_1.yRot = (35 * Mth.PI / 180) +Mth.cos(limbSwing) * 0.5F * limbSwingAmount;
        this.left_leg_1.zRot = (40 * Mth.PI / 180) -Math.max(0, Mth.sin(limbSwing) * -0.75F * limbSwingAmount);

        this.left_leg_2.yRot = (10 * Mth.PI / 180) -Mth.cos(limbSwing) * 0.5F * limbSwingAmount;
        this.left_leg_2.zRot = (35 * Mth.PI / 180) -Math.max(0, -Mth.sin(limbSwing) * -0.75F * limbSwingAmount);

        this.left_leg_3.yRot = (-10 * Mth.PI / 180) +Mth.cos(limbSwing) * 0.5F * limbSwingAmount;
        this.left_leg_3.zRot = (40 * Mth.PI / 180) -Math.max(0, Mth.sin(limbSwing) * -0.75F * limbSwingAmount);

        this.left_leg_4.yRot = (-35 * Mth.PI / 180) -Mth.cos(limbSwing) * 0.5F * limbSwingAmount;
        this.left_leg_4.zRot = (50 * Mth.PI / 180) -Math.max(0, -Mth.sin(limbSwing) * -0.75F * limbSwingAmount);

        this.right_leg_1.xRot = (20 * Mth.PI / 180);
        this.right_leg_2.xRot = (5 * Mth.PI / 180);
        this.right_leg_3.xRot = (-5 * Mth.PI / 180);
        this.right_leg_4.xRot = (-35 * Mth.PI / 180);

        this.left_leg_1.xRot = (20 * Mth.PI / 180);
        this.left_leg_2.xRot = (5 * Mth.PI / 180);
        this.left_leg_3.xRot = (-5 * Mth.PI / 180);
        this.left_leg_4.xRot = (-35 * Mth.PI / 180);

    }

    private void applyHeadRotation(float netHeadYaw, float headPitch) {
        netHeadYaw = Mth.clamp(netHeadYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45f);

        this.body.yRot = netHeadYaw * ((float) Math.PI / 180f);
        this.body.xRot = headPitch * ((float) Math.PI / 180f);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight,
                               int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}