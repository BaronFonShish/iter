package com.malignant.itermod.client.renderer;

import com.malignant.itermod.common.misc.RenderTypes;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.malignant.itermod.common.entity.misc.JaggedBeam;
import com.malignant.itermod.iterMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class JaggedBeamRenderer extends EntityRenderer<JaggedBeam> {

    private static final ResourceLocation TEXTURE_BEAM = new ResourceLocation(iterMod.MOD_ID, "textures/entity/beam.png");
    private static final ResourceLocation TEXTURE_LIGHTNING = new ResourceLocation(iterMod.MOD_ID, "textures/entity/lightning.png");

    public JaggedBeamRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(JaggedBeam beam, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        List <Vec3> points = beam.getPoints();
        if (points.size() < 2) {
            poseStack.popPose();
            RenderSystem.enableCull();
            return;
        }

        float progress = ((float) beam.tickCount / beam.lifetime);
        float alpha = beam.getAlpha();
        if (beam.fading) {
            alpha *= (1.0f - progress);
        }

        float width = beam.width;
        if (beam.shrinking){
            width *= (1.0f - progress);
        }

        if (beam.flickering){
            if (progress >= 0.5f){
                RandomSource random = beam.level().random;
                float flickerChance = 0.1f + (progress - 0.5f) * 0.9f;
                if (flickerChance >= random.nextFloat()){
                    alpha *= 0.1f;
                }
            }
        }

        int light = 15728880;

        int textureWidth = 3;
        int textureHeight = 16;

        int texturestep = 0;
        if (beam.scrolling) {
            texturestep = (int) (progress * textureHeight);
            texturestep = Math.min(texturestep, textureHeight - 1);
        }

        float width_segment = (float) 1/textureWidth;

        float v0 = (float) texturestep / textureHeight;
        float v1 = v0 + 1f / 16f;

        Vec3 start = Vec3.ZERO;
        Vec3 end = Vec3.ZERO;

        for (int i = 0; i < points.size()-1; i++){
            start = points.get(i).subtract(beam.position());
            end = points.get(i + 1).subtract(beam.position());

            /// glow 2
            renderSegment(start, end, width*3f, alpha*0.25f, light, v0, v1, width_segment * 2, buffer, poseStack, beam, 2);

            /// glow
            renderSegment(start, end, width*2f, alpha*0.5f, light, v0, v1, width_segment * 1, buffer, poseStack, beam, 2);

            /// main beam
            renderSegment(start, end, width, alpha, light, v0, v1, 0, buffer, poseStack, beam, 3);
        }

        poseStack.popPose();
    }

    public void renderSegment(Vec3 start, Vec3 end, float width, float alpha, int light, float v0, float v1, float widthstep, MultiBufferSource buffer, PoseStack poseStack, JaggedBeam beam, int rendertype){

        float hw = width/2;
        Vec3 direction = end.subtract(start);
        direction = direction.normalize();

        if (direction.length() < 0.001f) {
            return;
        }

        Vec3 arbitrary;
        if (Math.abs(direction.y) < 0.995f) {
            arbitrary = new Vec3(0, 1, 0);
        } else {
            arbitrary = new Vec3(0, 0, 1);
        }

        Vec3 side = direction.cross(arbitrary).normalize();
        Vec3 up = direction.cross(side).normalize();


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

        float u0 = widthstep;
        float u1 = widthstep + 0.05f;

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderTypes.Beam(getTextureLocation(beam)));

        if (rendertype != 2) {
            //start
            renderQuad(vertexConsumer, poseStack,
                    bottom_left_start, bottom_right_start, top_right_start, top_left_start,
                    alpha, light, u0, v0, u1, v1, rendertype);

            //end
            renderQuad(vertexConsumer, poseStack,
                    bottom_right_end, bottom_left_end, top_left_end, top_right_end,
                    alpha, light, u0, v0, u1, v1, rendertype);
        }

        //top
        renderQuad(vertexConsumer, poseStack,
                top_left_start, top_right_start, top_right_end, top_left_end,
                alpha, light, u0, v0, u1, v1, rendertype);

        //right
        renderQuad(vertexConsumer, poseStack,
                bottom_right_start, bottom_right_end, top_right_end, top_right_start,
                alpha, light, u0, v0, u1, v1, rendertype);

        //bottom
        renderQuad(vertexConsumer, poseStack,
                bottom_left_end, bottom_right_end, bottom_right_start, bottom_left_start,
                alpha, light, u0, v0, u1, v1, rendertype);

        //left
        renderQuad(vertexConsumer, poseStack,
                bottom_left_end, bottom_left_start, top_left_start, top_left_end,
                alpha, light, u0, v0, u1, v1, rendertype);

    }

    private void renderQuad(VertexConsumer consumer, PoseStack poseStack,
                            Vec3 p1, Vec3 p2, Vec3 p3, Vec3 p4,
                            float alpha, int light, float u0, float v0, float u1, float v1, int rendertype) {

        if (p1.distanceToSqr(p2) < 0.0001f || p2.distanceToSqr(p3) < 0.0001f) {
            return;
        }

        Vec3 edge1_1 = p2.subtract(p1);
        Vec3 edge2_1 = p3.subtract(p1);
        Vec3 faceNormal1 = edge1_1.cross(edge2_1).normalize();

        if (rendertype == 1) {

            vertex(consumer, poseStack, p1, u0, v1, alpha, light, faceNormal1);
            vertex(consumer, poseStack, p2, u1, v1, alpha, light, faceNormal1);
            vertex(consumer, poseStack, p3, u1, v0, alpha, light, faceNormal1);
            vertex(consumer, poseStack, p4, u0, v0, alpha, light, faceNormal1);
        }

        if (rendertype == 2) {
            vertex(consumer, poseStack, p4, u0, v0, alpha, light, faceNormal1);
            vertex(consumer, poseStack, p3, u1, v0, alpha, light, faceNormal1);
            vertex(consumer, poseStack, p2, u1, v1, alpha, light, faceNormal1);
            vertex(consumer, poseStack, p1, u0, v1, alpha, light, faceNormal1);
        }

        if (rendertype == 3) {
            vertex(consumer, poseStack, p1, u0, v1, alpha, light, faceNormal1);
            vertex(consumer, poseStack, p2, u1, v1, alpha, light, faceNormal1);
            vertex(consumer, poseStack, p3, u1, v0, alpha, light, faceNormal1);
            vertex(consumer, poseStack, p4, u0, v0, alpha, light, faceNormal1);

            vertex(consumer, poseStack, p4, u0, v0, alpha, light, faceNormal1);
            vertex(consumer, poseStack, p3, u1, v0, alpha, light, faceNormal1);
            vertex(consumer, poseStack, p2, u1, v1, alpha, light, faceNormal1);
            vertex(consumer, poseStack, p1, u0, v1, alpha, light, faceNormal1);
        }
    }

    private void vertex(VertexConsumer consumer, PoseStack poseStack, Vec3 worldPos,
                        float u, float v, float alpha, int light, Vec3 faceNormal) {

        PoseStack.Pose pose = poseStack.last();
        Matrix4f poseMatrix = pose.pose();
//        Matrix3f normalMatrix = pose.normal();

        float x = (float)(worldPos.x);
        float y = (float)(worldPos.y);
        float z = (float)(worldPos.z);

        consumer.vertex(poseMatrix, x, y, z)
                .color(1.0f, 1.0f, 1.0f, alpha)
                .uv(u, v)
                .endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(JaggedBeam beam) {
        String textureName = beam.getTexture();
        if (textureName == null || textureName.isEmpty()) {
            textureName = "beam";
        }
        return switch (textureName) {
            case "lightning", "wicked" -> TEXTURE_LIGHTNING;
            default -> TEXTURE_BEAM;
        };
    }
}