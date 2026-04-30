package com.malignant.iter.common.misc;

import com.malignant.iter.IterMod;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class RenderTypes extends RenderStateShard {

    public RenderTypes(String pName, Runnable pSetupState, Runnable pClearState) {
        super(pName, pSetupState, pClearState);
    }

    public static RenderType Beam(ResourceLocation texture) {
        return RenderType.create(
                IterMod.MOD_ID + ":beam",
                DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
                VertexFormat.Mode.QUADS,
                256,
                false,
                true,
                RenderType.CompositeState.builder()
                        .setShaderState(POSITION_COLOR_TEX_LIGHTMAP_SHADER)
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
