package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.common.variables.MageData;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;

public class ModCapabilities {
    public static final Capability<MageData> MAGE_DATA =
            CapabilityManager.get(new CapabilityToken<>() {});

    public static void register(RegisterCapabilitiesEvent event) {
        event.register(MageData.class);
    }

    public static LazyOptional<MageData> getMagicData(Player player) {
        return player.getCapability(MAGE_DATA);
    }
}