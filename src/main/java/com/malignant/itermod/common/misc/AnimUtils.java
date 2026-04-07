package com.malignant.itermod.common.misc;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CrossbowItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static net.minecraft.client.model.AnimationUtils.bobArms;

@OnlyIn(Dist.CLIENT)
public class AnimUtils {

    public void translateToHandOffset(HumanoidArm side, PoseStack poseStack, ModelPart arm) {
        float storedXRot = arm.xRot;
        float storedYRot = arm.yRot;
        float storedZRot = arm.zRot;

        arm.xRot = 0;
        arm.yRot = 0;
        arm.zRot = 0;

        arm.translateAndRotate(poseStack);

        float xOffset = side == HumanoidArm.RIGHT ? -0.25F : 0.25F;
        float yOffset = -0.35F;
        float zOffset = 0.1F;

        poseStack.translate(xOffset, yOffset, zOffset);

        poseStack.mulPose(com.mojang.math.Axis.XP.rotation(storedXRot));
        poseStack.mulPose(com.mojang.math.Axis.YP.rotation(storedYRot));
        poseStack.mulPose(com.mojang.math.Axis.ZP.rotation(storedZRot));

        arm.xRot = storedXRot;
        arm.yRot = storedYRot;
        arm.zRot = storedZRot;
    }

    public static void LegSit(Entity entity, ModelPart rightLeg, ModelPart leftLeg, ModelPart rightArm, ModelPart leftArm, ModelPart root) {
        if (entity.isPassenger()) {
            rightArm.xRot += Mth.DEG_TO_RAD * -25f;
            leftArm.xRot += Mth.DEG_TO_RAD * -25f;
            rightLeg.xRot += Mth.DEG_TO_RAD * -75f;
            rightLeg.yRot += Mth.DEG_TO_RAD * 20f;
            leftLeg.xRot += Mth.DEG_TO_RAD * -75f;
            leftLeg.yRot += Mth.DEG_TO_RAD * -20f;
            root.y += 4f;
        }
    }

    public static void MeleeWeaponHold(Entity entity, ModelPart rightArm, ModelPart leftArm, float limbSwing, float limbSwingAmount, float attackTime, float ageInTicks) {
        if (entity instanceof Monster mob) {
            if (mob.isAggressive()) {

                if (mob.getMainArm() == HumanoidArm.RIGHT) {
                    rightArm.xRot = -1.75F + (Mth.cos(limbSwing * 0.65F + (float) Math.PI) * limbSwingAmount * 0.15f);
                    rightArm.yRot = 0.15F;
                } else {
                    leftArm.xRot = -1.75F + (Mth.cos(limbSwing * 0.65F) * limbSwingAmount * 0.15f);
                    leftArm.yRot = 0.15F;
                }
            }
            if (attackTime > 0) {
                if (mob.getMainArm() == HumanoidArm.RIGHT) {
                    AnimationUtils.swingWeaponDown(rightArm, leftArm, mob, attackTime, ageInTicks);
                } else {
                    AnimationUtils.swingWeaponDown(leftArm, rightArm, mob, attackTime, ageInTicks);
                }
            }
        }
    }

    public static void ZombieArmHold(Entity entity, ModelPart rightArm, ModelPart leftArm, float attackTime, float ageInTicks, boolean aggro) {
        float f = Mth.sin(attackTime * (float)Math.PI);
        float f1 = Mth.sin((1.0F - (1.0F - attackTime) * (1.0F - attackTime)) * (float)Math.PI);
        rightArm.zRot = 0.0F;
        leftArm.zRot = 0.0F;
        rightArm.yRot = -(0.1F - f * 0.6F);
        leftArm.yRot = 0.1F - f * 0.6F;
        float f2 = -(float)Math.PI / (aggro ? 1.5F : 2.25F);
        rightArm.xRot = f2;
        leftArm.xRot = f2;
        rightArm.xRot += f * 1.2F - f1 * 0.4F;
        leftArm.xRot += f * 1.2F - f1 * 0.4F;
    }

    public static void BowHold(){}
}