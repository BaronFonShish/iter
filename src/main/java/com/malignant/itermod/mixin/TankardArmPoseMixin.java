package com.malignant.itermod.mixin;

import com.malignant.itermod.common.registry.ModItems;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class TankardArmPoseMixin<T extends LivingEntity> extends EntityModel<T> {

    @Inject(method = "setupAnim", at = @At("TAIL"))
    private void applyTankardPose(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                                  float netHeadYaw, float headPitch, CallbackInfo ci) {

        if (!(entity instanceof AbstractClientPlayer player)) return;

        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() == ModItems.TANKARD.get() && player.isUsingItem()) {
            HumanoidModel<?> model = (HumanoidModel<?>) (Object) this;

            model.rightArm.xRot = (float) Math.toRadians(-110);
            model.rightArm.yRot = 0.2F;
            model.rightArm.zRot = 0.0F;
        }
    }
}
