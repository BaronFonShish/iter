package com.malignant.iter.client.model;

import com.malignant.iter.IterMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class FireballModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(IterMod.MOD_ID, "fireball"), "main");
    private final ModelPart fireball;
    private final ModelPart core;
    private final ModelPart trail;

    public FireballModel(ModelPart root) {
        this.fireball = root.getChild("fireball");
        this.core = this.fireball.getChild("core");
        this.trail = this.fireball.getChild("trail");
    }
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition fireball = partdefinition.addOrReplaceChild("fireball", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition core = fireball.addOrReplaceChild("core", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(-0.001F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition trail = fireball.addOrReplaceChild("trail", CubeListBuilder.create().texOffs(9, 20).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(26, 2).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(26, 2).mirror().addBox(-5.0F, 5.0F, -5.0F, 10.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition overlay4_r1 = trail.addOrReplaceChild("overlay4_r1", CubeListBuilder.create().texOffs(26, 2).mirror().addBox(-5.0F, 0.0F, -8.0F, 10.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 0.0F, 3.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition overlay3_r1 = trail.addOrReplaceChild("overlay3_r1", CubeListBuilder.create().texOffs(26, 2).addBox(-5.0F, 0.0F, -8.0F, 10.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.0F, 3.0F, 0.0F, 0.0F, -1.5708F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.fireball.yRot = ageInTicks / 5f;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        fireball.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }
}
