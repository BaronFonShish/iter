package com.thirdlife.itermod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.thirdlife.itermod.common.registry.ModItems;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class TankardItemRotationMixin {

    @Inject(
            method = "renderArmWithItem",
            at = @At("HEAD")
    )
    private void rotateTankardItem(AbstractClientPlayer player,
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
        if (stack.getItem() == ModItems.TANKARD.get() && player.isUsingItem()) {
            if (hand == InteractionHand.MAIN_HAND) {
                // Поворот кружки по Z (например 90 градусов)
                poseStack.mulPose(Axis.ZP.rotationDegrees(0f));

                // Поднять предмет вверх (y = -0.3F поднимает выше)
                // x = вправо/влево, y = вверх/вниз, z = вперёд/назад
                poseStack.translate(0.0F, 0.3F, 0.0F);
            }
        }
    }
}
