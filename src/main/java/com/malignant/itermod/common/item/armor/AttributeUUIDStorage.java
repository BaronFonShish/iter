package com.malignant.itermod.common.item.armor;

import java.util.UUID;

public class AttributeUUIDStorage {
    public static UUID provide(String name) {
        return UUID.nameUUIDFromBytes((name.getBytes()));
    }
}
