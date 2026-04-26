package com.malignant.itermod.common.misc;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ClientPlayerGetter {

    @OnlyIn(Dist.CLIENT)
    public static Player getClientPlayer() {
        return net.minecraft.client.Minecraft.getInstance().player;
    }
}
