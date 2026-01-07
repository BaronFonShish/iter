package com.thirdlife.itermod.common.event;

import com.thirdlife.itermod.common.registry.ModAttributes;
import com.thirdlife.itermod.common.variables.IterPlayerDataUtils;
import com.thirdlife.itermod.common.variables.PlayerFlightPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "iter", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerFlight {

    private static boolean wasJumping = false;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Player player = event.player;

            if (!IterPlayerDataUtils.ifCanFly(player) || player.isDeadOrDying()) {
                return;
            }

            if (player.isFallFlying()) return;

            if (!player.level().isClientSide()) {
                AttributeInstance maxFlightTimeAttr = player.getAttribute(ModAttributes.FLIGHT_TIME.get());
                float maxFlightTime = maxFlightTimeAttr != null ? (float) maxFlightTimeAttr.getValue() : 0f;

                if (player.onGround()) {
                    IterPlayerDataUtils.setFlightTime(player, maxFlightTime);
                }

                if (player.isInWater() || player.isInLava() || player.isInFluidType()){
                    float replenishRate = maxFlightTime / 100f;
                    float newTime = Math.min(maxFlightTime, IterPlayerDataUtils.getFlightTime(player) + replenishRate);
                    IterPlayerDataUtils.setFlightTime(player, newTime);
                }
            }

            if (IterPlayerDataUtils.isFlying(player)){
                PlayerFlying(player);
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player == null || minecraft.level == null) return;

            if (!IterPlayerDataUtils.ifCanFly(minecraft.player)) {
                wasJumping = false;
                return;
            }

            boolean onGround = minecraft.player.onGround();
            boolean isJumping = minecraft.options.keyJump.isDown();
            float flightTime = IterPlayerDataUtils.getFlightTime(minecraft.player);

            if (isJumping && flightTime > 0 && !onGround) {
                PlayerFlightPacket.send(true);
            } else if (!isJumping && wasJumping) {
                PlayerFlightPacket.send(false);
            }

            wasJumping = isJumping;
        }
    }

    public static void PlayerFlying(Player player) {
        if (!IterPlayerDataUtils.ifCanFly(player)) return;

        float flightTime = IterPlayerDataUtils.getFlightTime(player);

        if (flightTime > 0) {
            AttributeInstance flightSpeedAttr = player.getAttribute(ModAttributes.FLIGHT_SPEED.get());
            float flightSpeed = flightSpeedAttr != null ? (float) flightSpeedAttr.getValue() : 0f;

            Vec3 currentMotion = player.getDeltaMovement();
            Vec3 lookVec = player.getLookAngle();
            double yAcceleration = ((flightSpeed) - currentMotion.y()) * 0.1f;
            double horizontalAcceleration = 0.08 + flightSpeed * 0.02;
            float forward = player.zza;
            float strafe = player.xxa;

            double newX = 0;
            double newY = yAcceleration;
            double newZ = 0;

            Vec3 NewMovVec = new Vec3(newX, newY, newZ);

            player.push(newX, newY, newZ);

            if (player.getDeltaMovement().y() > 0) {player.resetFallDistance();}
            player.hurtMarked = true;
            float flightCost = 1.0f / 20.0f;
            float newFlightTime = Math.max(0, flightTime - flightCost);
            IterPlayerDataUtils.setFlightTime(player, newFlightTime);
        }
    }
}


//            if (forward != 0)
//                if (forward > 0) {
//                    newX += lookVec.x * horizontalAcceleration;
//                    newZ += lookVec.z * horizontalAcceleration;
//                } else if (forward < 0) {
//                    newX -= lookVec.x * horizontalAcceleration;
//                    newZ -= lookVec.z * horizontalAcceleration;
//                }
//            }
//
//                if (strafe != 0) {
//                    Vec3 strafeVec = lookVec.yRot((float) Math.PI / 2);
//                    newX += strafeVec.x * horizontalAcceleration * strafe;
//                    newZ += strafeVec.z * horizontalAcceleration * strafe;
//                }
//                double horizontalDrag = 0.95;
//
//                newX *= horizontalDrag;
//                newZ *= horizontalDrag;