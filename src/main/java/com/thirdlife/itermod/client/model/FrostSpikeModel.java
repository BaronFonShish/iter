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

public class FrostSpikeModel<T extends Entity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("itermod", "frost_spike"), "main");
    private final ModelPart projectile;

    public FrostSpikeModel(ModelPart root) {
        this.projectile = root.getChild("projectile");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition projectile = partdefinition.addOrReplaceChild("projectile",
                CubeListBuilder.create().texOffs(0, 25).addBox(-1.5F, 7.0F, -1.5F, 3.0F, 0F, 3.0F,
                        new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition blade_r1 = projectile
                .addOrReplaceChild("blade_r1",
                        CubeListBuilder.create().texOffs(0, -6).addBox(0.0F, -8.0F, -3.0F, 0.0F, 16.0F, 6.0F,
                                new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.0F, -2.3562F, 0.0F));

        PartDefinition blade_r2 = projectile
                .addOrReplaceChild("blade_r2",
                        CubeListBuilder.create().texOffs(0, -6).addBox(0.0F, -8.0F, -3.0F, 0.0F, 16.0F, 6.0F,
                                new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
                          float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        projectile.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}