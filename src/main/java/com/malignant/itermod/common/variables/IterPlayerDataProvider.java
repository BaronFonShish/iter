package com.malignant.itermod.common.variables;

import com.malignant.itermod.common.registry.ModCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IterPlayerDataProvider implements ICapabilitySerializable<CompoundTag> {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation("iter", "player_data");

    private final IterPlayerData data = new IterPlayerData();
    private final LazyOptional<IterPlayerData> optional = LazyOptional.of(() -> data);

    public static void attach(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(IDENTIFIER, new IterPlayerDataProvider());
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return ModCapabilities.ITER_PLAYER_DATA.orEmpty(cap, optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        data.saveNBT(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        data.loadNBT(nbt);
    }
}