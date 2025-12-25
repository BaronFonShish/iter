package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.common.variables.IterPlayerData;
import com.thirdlife.itermod.common.variables.SpellbookCapability;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;

public class ModCapabilities {
    public static final Capability<IterPlayerData> ITER_PLAYER_DATA =
            CapabilityManager.get(new CapabilityToken<>() {});

    public static final Capability<SpellbookCapability> SPELLBOOK_CAPABILITY =
            CapabilityManager.get(new CapabilityToken<>() {});

    public static void register(RegisterCapabilitiesEvent event) {
        event.register(IterPlayerData.class);
        event.register(SpellbookCapability.class);
    }


    public static LazyOptional<IterPlayerData> getMageData(Player player) {
        return player.getCapability(ITER_PLAYER_DATA);
    }
}