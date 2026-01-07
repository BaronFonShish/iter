package com.thirdlife.itermod.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class GenericArrowModel<T extends Entity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("itermod", "generic_arrow"), "main");
    private final ModelPart arrow;

    public GenericArrowModel(ModelPart root) {
        this.arrow = root.getChild("arrow");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition arrow = partdefinition.addOrReplaceChild("arrow", CubeListBuilder.create().texOffs(0, -1)
                        .addBox(0.0F, -12.3333F, -3.5F, 0.0F, 19.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 10F, 0.0F));

        PartDefinition tail_r1 = arrow.addOrReplaceChild("tail_r1",
                CubeListBuilder.create().texOffs(-7, 25).addBox(-4.0F, 0.0F, -4.0F, 7.0F, 0.0F, 7.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5F, 2.6667F, 0.5F, 0.0F, -1.5708F, 0.0F));

        PartDefinition plane_r1 = arrow.addOrReplaceChild("plane_r1",
                CubeListBuilder.create().texOffs(14, -1).addBox(0.0F, -9.5F, -3.5F, 0.0F, 19.0F, 7.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -2.8333F, 0.0F, 0.0F, -1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        arrow.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
                          float headPitch) {
    }
}