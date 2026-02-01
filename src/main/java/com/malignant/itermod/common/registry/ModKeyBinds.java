package com.malignant.itermod.common.registry;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class ModKeyBinds {
    public static final KeyMapping SPELL_SLOT_SELECT = new KeyMapping(
            "key.iter.spell_slot_modifier",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_LALT,
            "key.categories.iter"
    );

    public static void register() {

    }
}