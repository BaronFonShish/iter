package com.thirdlife.itermod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.thirdlife.itermod.common.item.magic.defaults.SpellFocus;
import com.thirdlife.itermod.common.registry.ModItems;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class StaffSpellcastingInHandMixin {

    @Inject(
            method = "renderArmWithItem",
            at = @At("HEAD")
    )
    private void rotateStaffItem(AbstractClientPlayer player,
                                   float partialTicks,
                                   float pitch,
                                   InteractionHand hand,
                                   float swingProgress,
                                   ItemStack stack,
                                   float equipProgress,
                                   PoseStack poseStack,
                                   MultiBufferSource bufferSource,
                                   int packedLight,
                                   CallbackInfo ci) {
        if (stack.getItem() instanceof SpellFocus && player.isUsingItem()) {
            float usetime = player.getTicksUsingItem() + partialTicks;

            InteractionHand usedHand = player.getUsedItemHand();
            boolean isMainHand = usedHand == InteractionHand.MAIN_HAND;
            HumanoidArm staffArm = isMainHand ? player.getMainArm() : player.getMainArm().getOpposite();

            if (staffArm == HumanoidArm.RIGHT) {
                poseStack.translate(0.5F, -0.35F, -0.75F);
                poseStack.mulPose(Axis.XP.rotationDegrees((float) Math.sin(usetime/2) * 5F));
                poseStack.mulPose(Axis.ZP.rotationDegrees((float) Math.cos(usetime/2) * 5F));
            } else {
                poseStack.translate(-0.5F, -0.35F, -0.75F);
                poseStack.mulPose(Axis.XP.rotationDegrees((float) Math.sin(usetime/2) * 5F));
                poseStack.mulPose(Axis.ZP.rotationDegrees((float) -Math.cos(usetime/2) * 5F));
            }
        }
    }
}
