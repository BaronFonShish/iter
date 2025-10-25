package com.thirdlife.itermod.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thirdlife.itermod.client.animations.SpiderlingAnimationDefinitions;
import com.thirdlife.itermod.common.entity.SpiderlingEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.builders.*;


public class SpiderlingModel<T extends SpiderlingEntity> extends HierarchicalModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation("itermod", "spiderling"), "main");

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart left_chelicerae;
    private final ModelPart right_chelicerae;
    private final ModelPart belly;
    private final ModelPart left_leg;
    private final ModelPart right_leg;
    private final ModelPart left_leg4;
    private final ModelPart right_leg4;
    private final ModelPart left_leg2;
    private final ModelPart right_leg2;
    private final ModelPart left_leg3;
    private final ModelPart right_leg3;

    public SpiderlingModel(ModelPart root) {

        this.root = root;
        this.body = this.root.getChild("body");
        this.left_chelicerae = this.body.getChild("left_chelicerae");
        this.right_chelicerae = this.body.getChild("right_chelicerae");
        this.belly = this.body.getChild("belly");
        this.left_leg = this.root.getChild("left_leg");
        this.right_leg = this.root.getChild("right_leg");
        this.left_leg4 = this.root.getChild("left_leg4");
        this.right_leg4 = this.root.getChild("right_leg4");
        this.left_leg2 = this.root.getChild("left_leg2");
        this.right_leg2 = this.root.getChild("right_leg2");
        this.left_leg3 = this.root.getChild("left_leg3");
        this.right_leg3 = this.root.getChild("right_leg3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(0, 13).addBox(-3.0F, -3.0F, -3.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(19, 13).addBox(-3.0F, -3.0F, -3.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.5F, 21.0F, -1.0F));

        PartDefinition left_chelicerae = body.addOrReplaceChild("left_chelicerae", CubeListBuilder.create()
                        .texOffs(15, 21).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.5F, -1.0F, -3.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition right_chelicerae = body.addOrReplaceChild("right_chelicerae", CubeListBuilder.create()
                        .texOffs(15, 21).mirror().addBox(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)).mirror(false),
                PartPose.offsetAndRotation(-1.5F, -1.0F, -3.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition belly = body.addOrReplaceChild("belly", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3.5F, -3.0F, 0.0F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5F, -2.0F, 0.75F, -0.2182F, 0.0F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create()
                        .texOffs(0, 21).addBox(0.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.5F, 21.0F, -2.25F, 0.0F, 0.0F, 0.5236F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create()
                        .texOffs(0, 21).mirror().addBox(-6.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false),
                PartPose.offsetAndRotation(-0.5F, 21.0F, -2.25F, 0.0F, 0.0F, -0.5236F));

        PartDefinition left_leg4 = partdefinition.addOrReplaceChild("left_leg4", CubeListBuilder.create()
                        .texOffs(0, 21).addBox(0.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.5F, 21.0F, -2.75F, 0.2878F, 0.4643F, 0.6F));

        PartDefinition right_leg4 = partdefinition.addOrReplaceChild("right_leg4", CubeListBuilder.create()
                        .texOffs(0, 21).mirror().addBox(-6.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false),
                PartPose.offsetAndRotation(-0.5F, 21.0F, -2.75F, 0.2878F, -0.4643F, -0.6F));

        PartDefinition left_leg2 = partdefinition.addOrReplaceChild("left_leg2", CubeListBuilder.create()
                        .texOffs(0, 21).addBox(0.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.5F, 21.0F, -1.5F, -0.2355F, -0.3829F, 0.5695F));

        PartDefinition right_leg2 = partdefinition.addOrReplaceChild("right_leg2", CubeListBuilder.create()
                        .texOffs(0, 21).mirror().addBox(-6.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false),
                PartPose.offsetAndRotation(-0.5F, 21.0F, -1.5F, -0.2355F, 0.3829F, -0.5695F));

        PartDefinition left_leg3 = partdefinition.addOrReplaceChild("left_leg3", CubeListBuilder.create()
                        .texOffs(0, 21).addBox(0.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.25F, 21.0F, -1.0F, -0.7614F, -0.877F, 0.9035F));

        PartDefinition right_leg3 = partdefinition.addOrReplaceChild("right_leg3", CubeListBuilder.create()
                        .texOffs(0, 21).mirror().addBox(-6.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false),
                PartPose.offsetAndRotation(0.25F, 21.0F, -1.0F, -0.7614F, 0.877F, -0.9035F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        applyHeadRotation(netHeadYaw, headPitch);

        this.animateWalk(SpiderlingAnimationDefinitions.SPIDERLING_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(entity.idleAnimationState, SpiderlingAnimationDefinitions.SPIDERLING_IDLE, ageInTicks, 1f);

        this.animate(entity.attackAnimationState, SpiderlingAnimationDefinitions.SPIDERLING_ATTACK, ageInTicks, 1f);
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