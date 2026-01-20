package com.thirdlife.itermod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thirdlife.itermod.common.entity.misc.StraightBeam;
import com.thirdlife.itermod.iterMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class StraightBeamRendererCopy extends EntityRenderer<StraightBeam> {
    private static final ResourceLocation BEAM_TEXTURE = new ResourceLocation(iterMod.MOD_ID, "textures/entity/beam.png");

    public StraightBeamRendererCopy(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(StraightBeam beam, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        Vec3 start = beam.position();
        Vec3 end = beam.getEndPos();

        Vec3 direction = end.subtract(start);
        direction = direction.normalize();

        Vec3 arbitrary;
        if (Math.abs(direction.y) < 0.995f) {
            arbitrary = new Vec3(0, 1, 0);
        } else {
            arbitrary = new Vec3(0, 0, 1);
        }

        Vec3 side = direction.cross(arbitrary).normalize();
        Vec3 up = direction.cross(side).normalize();

        float alpha = 1.0f;
        if (beam.fading) {
            alpha = 1.0f - ((float) beam.tickCount / beam.lifetime);
        }

        float width = beam.width;
        if (beam.shrinking){
            width = width * (1.0f - ((float) beam.tickCount / beam.lifetime));
        }

        float hw = width/2;
        int light = 15728880;

        float v0 = (float) beam.tickCount / beam.lifetime * (15.0f / 16.0f);
        float v1 = v0 + (1.0f / 16.0f);

        Vec3 sideOffset = side.scale(hw);
        Vec3 upOffset = up.scale(hw);

        Vec3 top_right_start = start.add(sideOffset).add(upOffset);
        Vec3 top_left_start = start.subtract(sideOffset).add(upOffset);
        Vec3 bottom_left_start = start.subtract(sideOffset).subtract(upOffset);
        Vec3 bottom_right_start = start.add(sideOffset).subtract(upOffset);

        Vec3 top_right_end = end.add(sideOffset).add(upOffset);
        Vec3 top_left_end = end.subtract(sideOffset).add(upOffset);
        Vec3 bottom_left_end = end.subtract(sideOffset).subtract(upOffset);
        Vec3 bottom_right_end = end.add(sideOffset).subtract(upOffset);

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucentEmissive(BEAM_TEXTURE));


        //start
        renderQuad(vertexConsumer, poseStack,
                top_right_start, bottom_right_start, bottom_left_start, top_left_start,
                alpha, light, 0, v0, 1, v1);

        //top
        renderQuad(vertexConsumer, poseStack,
                top_left_end, top_right_end, top_right_start, top_left_start,
                alpha, light, 0, v0, 1, v1);

        //right
        renderQuad(vertexConsumer, poseStack,
                top_right_end, bottom_right_end, bottom_right_start, top_right_start,
                alpha, light, 0, v0, 1, v1);

        //bottom
        renderQuad(vertexConsumer, poseStack,
                bottom_left_end, bottom_right_end, bottom_right_start, bottom_left_start,
                alpha, light, 0, v0, 1, v1);

        //left
        renderQuad(vertexConsumer, poseStack,
                bottom_left_end, top_left_end, top_left_start, bottom_left_start,
                alpha, light, 0, v0, 1, v1);

        //end
        renderQuad(vertexConsumer, poseStack,
                top_right_end, top_left_end, bottom_left_end, bottom_right_end,
                alpha, light, 0, v0, 1, v1);

        poseStack.popPose();
    }


    private void renderQuad(VertexConsumer consumer, PoseStack poseStack,
                            Vec3 p1, Vec3 p2, Vec3 p3, Vec3 p4,
                            float alpha, int light, float u0, float v0, float u1, float v1) {

        Vec3 cameraPos = this.entityRenderDispatcher.camera.getPosition();

        if (p1.distanceToSqr(p2) < 0.0001f || p2.distanceToSqr(p3) < 0.0001f) {
            return;
        }

        //first normal
        Vec3 edge1_1 = p2.subtract(p1);
        Vec3 edge2_1 = p3.subtract(p1);
        Vec3 faceNormal1 = edge1_1.cross(edge2_1).normalize();

        //second normal
        Vec3 edge1_2 = p3.subtract(p1);
        Vec3 edge2_2 = p4.subtract(p1);
        Vec3 faceNormal2 = edge1_2.cross(edge2_2).normalize();


        //top_left_start, bottom_left_start, bottom_right_start, top_right_start,

        // triangle 1
        vertex(consumer, poseStack, p1, cameraPos, u0, v0, alpha, light, faceNormal1);
        vertex(consumer, poseStack, p2, cameraPos, u1, v0, alpha, light, faceNormal1);
        vertex(consumer, poseStack, p3, cameraPos, u1, v1, alpha, light, faceNormal1);

        // triangle 2
        vertex(consumer, poseStack, p1, cameraPos, u0, v0, alpha, light, faceNormal2);
        vertex(consumer, poseStack, p3, cameraPos, u1, v1, alpha, light, faceNormal2);
        vertex(consumer, poseStack, p4, cameraPos, u0, v1, alpha, light, faceNormal2);
    }

    private void vertex(VertexConsumer consumer, PoseStack poseStack, Vec3 worldPos, Vec3 cameraPos,
                        float u, float v, float alpha, int light, Vec3 faceNormal) {

        PoseStack.Pose pose = poseStack.last();
        Matrix4f poseMatrix = pose.pose();
        Matrix3f normalMatrix = pose.normal();

        float x = (float)(worldPos.x - cameraPos.x);
        float y = (float)(worldPos.y - cameraPos.y);
        float z = (float)(worldPos.z - cameraPos.z);

        consumer.vertex(poseMatrix, x, y, z)
                .color(1.0f, 1.0f, 1.0f, alpha)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(normalMatrix, (float) faceNormal.x, (float) faceNormal.y, (float) faceNormal.z)
                .endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(StraightBeam beam) {
        return BEAM_TEXTURE;
    }
}