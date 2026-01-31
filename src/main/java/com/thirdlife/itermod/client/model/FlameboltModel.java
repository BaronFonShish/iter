package com.thirdlife.itermod.client.model;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thirdlife.itermod.iterMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class FlameboltModel<T extends Entity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(iterMod.MOD_ID, "flamebolt"), "main");
    private final ModelPart projectile;
    private final ModelPart base;
    private final ModelPart trail;

    public FlameboltModel(ModelPart root) {
        this.projectile = root.getChild("projectile");
        this.base = this.projectile.getChild("base");
        this.trail = this.projectile.getChild("trail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition projectile = partdefinition.addOrReplaceChild("projectile", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

        PartDefinition base = projectile.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.5F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition trail = projectile.addOrReplaceChild("trail", CubeListBuilder.create().texOffs(0, 12).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.5F, 0.0F));

        PartDefinition trail_r1 = trail.addOrReplaceChild("trail_r1", CubeListBuilder.create().texOffs(0, 12).addBox(-3.0F, -2.5F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.projectile.yRot = ageInTicks / 5f;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        projectile.render(poseStack, vertexConsumer, 15728880, packedOverlay, red, green, blue, alpha);
    }
}
