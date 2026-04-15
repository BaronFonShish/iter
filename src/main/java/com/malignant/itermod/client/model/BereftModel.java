package com.malignant.itermod.client.model;

import com.malignant.itermod.common.misc.AnimUtils;
import com.malignant.itermod.common.registry.ModTags;
import com.malignant.itermod.iterMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;

public class BereftModel<T extends LivingEntity> extends EntityModel<T> implements ArmedModel {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(iterMod.MOD_ID, "bereft"), "main");
	private final ModelPart wholemob;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public BereftModel(ModelPart root) {
		this.wholemob = root.getChild("wholemob");
		this.head = this.wholemob.getChild("head");
		this.body = this.wholemob.getChild("body");
		this.leftArm = this.wholemob.getChild("leftArm");
		this.rightArm = this.wholemob.getChild("rightArm");
		this.leftLeg = this.wholemob.getChild("leftLeg");
		this.rightLeg = this.wholemob.getChild("rightLeg");
	}

    public ModelPart getHead() { return head; }
    public ModelPart getBody() { return body; }
    public ModelPart getLeftArm() { return leftArm; }
    public ModelPart getRightArm() { return rightArm; }
    public ModelPart getLeftLeg() { return leftLeg; }
    public ModelPart getRightLeg() { return rightLeg; }
    public float getRootZRot() {return wholemob.zRot;}
    public float getRootYOffset() {return wholemob.y;}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition wholemob = partdefinition.addOrReplaceChild("wholemob", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = wholemob.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-2.0F, -12.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = wholemob.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 17).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leftArm = wholemob.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(48, 0).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition rightArm = wholemob.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(48, 0).mirror().addBox(-2.0F, -2.0F, -2.0F, 3.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition leftLeg = wholemob.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(32, 1).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offset(2F, 12.0F, 0.0F));

		PartDefinition rightLeg = wholemob.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(32, 1).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offset(-2F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        this.wholemob.y = 0;
        this.rightArm.zRot = 0;
        this.leftArm.zRot = 0;
        this.rightLeg.zRot = 0;
        this.leftLeg.zRot = 0;
        this.rightLeg.yRot = 0;
        this.leftLeg.yRot = 0;
        this.rightArm.yRot = 0;
        this.leftArm.yRot = 0;

        this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.head.xRot = headPitch * Mth.DEG_TO_RAD;

        this.rightLeg.xRot = Mth.cos(limbSwing * 0.65F) * 1.0F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.65F) * -1.0F * limbSwingAmount;

        this.wholemob.zRot = Mth.cos(limbSwing * 0.65f) * 0.075f * limbSwingAmount;

        this.rightArm.xRot = Mth.cos(limbSwing * 0.65F + (float) Math.PI) * limbSwingAmount;
        this.leftArm.xRot = Mth.cos(limbSwing * 0.65F) * limbSwingAmount;

        if (entity instanceof Monster mob) {
            boolean aggro = mob.isAggressive();

            ItemStack mainHand = mob.getMainHandItem();

            boolean isUsingBow = mob.isUsingItem() && mainHand.getItem() instanceof BowItem;

            if (mainHand.getItem() instanceof BowItem) {
                AnimUtils.BowHold(mob, rightArm, leftArm,
                        ageInTicks, isUsingBow, netHeadYaw, headPitch);
            } else if (!mainHand.isEmpty()) {
                AnimUtils.MeleeWeaponHold(mob, rightArm, leftArm, limbSwing, limbSwingAmount, attackTime, ageInTicks);
            } else {
                AnimUtils.ZombieArmHold(mob, rightArm, leftArm, attackTime, ageInTicks, aggro);
            }
        }

        AnimUtils.LegSit(entity,rightLeg, leftLeg, rightArm, leftArm, wholemob);
        AnimationUtils.bobArms(this.rightArm, this.leftArm, ageInTicks);
	}

    protected ModelPart getArm(HumanoidArm pSide){
        return pSide == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
    }

    public void translateToHand(HumanoidArm Side, PoseStack PoseStack){
        ModelPart arm = this.getArm(Side);
        arm.translateAndRotate(PoseStack);

        float handOffset = Side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
        PoseStack.translate(handOffset * 0.025F, 0.0F, 0.0F);
    }

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		wholemob.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

}