package com.malignant.itermod.common.misc;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import com.malignant.itermod.iterMod;

public class RenderTypes extends RenderStateShard {

    public RenderTypes(String pName, Runnable pSetupState, Runnable pClearState) {
        super(pName, pSetupState, pClearState);
    }

    public static RenderType Beam(ResourceLocation texture) {
        return RenderType.create(
                iterMod.MOD_ID + ":beam",
                DefaultVertexFormat.POSITION_COLOR_TEX,
                VertexFormat.Mode.QUADS,
                256,
                false,
                true,
                RenderType.CompositeState.builder()
                        .setShaderState(POSITION_COLOR_TEX_SHADER)
                        .setTextureState(new TextureStateShard(texture, false, false))
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setCullState(CULL)
                        .setDepthTestState(LEQUAL_DEPTH_TEST)
                        .setWriteMaskState(COLOR_WRITE)
                        .setOverlayState(OVERLAY)
                        .setLightmapState(NO_LIGHTMAP)
                        .createCompositeState(false)
        );
    }
}
