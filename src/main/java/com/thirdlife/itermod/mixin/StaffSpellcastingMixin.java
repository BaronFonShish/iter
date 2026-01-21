package com.thirdlife.itermod.mixin;

import com.thirdlife.itermod.common.item.magic.defaults.SpellFocus;
import com.thirdlife.itermod.common.registry.ModItems;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class StaffSpellcastingMixin<T extends LivingEntity> extends EntityModel<T> {

    @Shadow
    @Final
    public ModelPart leftArm;

    @Shadow
    @Final
    public ModelPart rightArm;

    @Inject(method = "setupAnim", at = @At("TAIL"))
    private void applyStaffPose(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                                  float netHeadYaw, float headPitch, CallbackInfo ci) {

        if (!(entity instanceof AbstractClientPlayer player)) return;

        ItemStack stack = player.getUseItem();
        if (stack.getItem() instanceof SpellFocus && player.isUsingItem()) {
            HumanoidModel<?> model = (HumanoidModel<?>) (Object) this;
            float usetime = player.getTicksUsingItem() + ageInTicks - player.tickCount;

            InteractionHand usedHand = player.getUsedItemHand();
            boolean isMainHand = usedHand == InteractionHand.MAIN_HAND;
            HumanoidArm staffArm = isMainHand ? player.getMainArm() : player.getMainArm().getOpposite();

            if (staffArm == HumanoidArm.RIGHT) {
                model.rightArm.xRot = (-(float) Math.PI / 2F) + model.head.xRot - 0.75F - Mth.sin(usetime / 2) * 0.5f;
                model.rightArm.yRot = -0.1F + model.head.yRot + Mth.cos(usetime / 2) * 0.25f;
                model.rightArm.zRot = 0.0F;
            } else {
                model.leftArm.xRot = (-(float) Math.PI / 2F) + model.head.xRot - 0.75F - Mth.cos(usetime / 2) * 0.5f;
                model.leftArm.yRot = 0.1F + model.head.yRot + Mth.sin(usetime / 2) * 0.25f;
                model.leftArm.zRot = 0.0F;
            }
        }
    }
}
