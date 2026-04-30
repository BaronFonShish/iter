package com.malignant.iter.common.registry;

import com.malignant.iter.IterMod;
import com.malignant.iter.common.variables.IterPlayerData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ModCapabilities {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, IterMod.MOD_ID);

    public static final Supplier<AttachmentType<IterPlayerData>> ITER_PLAYER_DATA =
            ATTACHMENT_TYPES.register(
                    "iter_player_data",
                    () -> AttachmentType.serializable(IterPlayerData::new).build()
            );

    @Nullable
    public static IterPlayerData getMageData(Player player) {
        return player.getData(ITER_PLAYER_DATA);
    }

    @Nullable
    public static IItemHandler getSpellbookInventory(ItemStack stack) {
        return stack.getCapability(Capabilities.ItemHandler.ITEM);
    }
}
