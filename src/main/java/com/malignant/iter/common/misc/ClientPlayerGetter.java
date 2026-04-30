package com.malignant.iter.common.misc;

import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ClientPlayerGetter {

    @OnlyIn(Dist.CLIENT)
    public static Player getClientPlayer() {
        return net.minecraft.client.Minecraft.getInstance().player;
    }
}
