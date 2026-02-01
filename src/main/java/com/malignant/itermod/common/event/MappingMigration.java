package com.malignant.itermod.common.event;


import com.malignant.itermod.iterMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = iterMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)

public class MappingMigration {

    private static final Map<ResourceLocation, ResourceLocation> ITEM_RENAME_MAP = new HashMap<>();
    private static final Map<ResourceLocation, ResourceLocation> BLOCK_RENAME_MAP = new HashMap<>();

    static {

        ITEM_RENAME_MAP.put(new ResourceLocation("iter_rpg", "ametrine_ingot"),
                new ResourceLocation("iter", "nostelon"));

        BLOCK_RENAME_MAP.put(new ResourceLocation("iter_rpg", "arcanist_table"),
                new ResourceLocation("iter", "spellweaver_table"));
    }

    @SubscribeEvent
    public static void onMissingMappings(MissingMappingsEvent event) {
        /// BLOCKS
        for (MissingMappingsEvent.Mapping<Block> mapping : event.getMappings(ForgeRegistries.Keys.BLOCKS, "iter_rpg")) {
            ResourceLocation oldId = mapping.getKey();

            if (BLOCK_RENAME_MAP.containsKey(oldId)) {
                ResourceLocation newId = BLOCK_RENAME_MAP.get(oldId);
                Block newBlock = ForgeRegistries.BLOCKS.getValue(newId);
                if (newBlock != null) {
                    mapping.remap(newBlock);
                    continue;
                }
            }

            if (oldId.getNamespace().equals("iter_rpg")) {
                ResourceLocation newId = new ResourceLocation("iter", oldId.getPath());

                Block newBlock = ForgeRegistries.BLOCKS.getValue(newId);
                if (newBlock != null) {
                    mapping.remap(newBlock);
                }
            }
        }
        /// ITEMS
        for (MissingMappingsEvent.Mapping<Item> mapping : event.getMappings(ForgeRegistries.Keys.ITEMS, "iter_rpg")) {
            ResourceLocation oldId = mapping.getKey();

            if (ITEM_RENAME_MAP.containsKey(oldId)) {
                ResourceLocation newId = ITEM_RENAME_MAP.get(oldId);
                Item newItem = ForgeRegistries.ITEMS.getValue(newId);
                if (newItem != null) {
                    mapping.remap(newItem);
                    continue;
                }
            }

            if (oldId.getNamespace().equals("iter_rpg")) {
                ResourceLocation newId = new ResourceLocation("iter", oldId.getPath());

                Item newItem = ForgeRegistries.ITEMS.getValue(newId);
                if (newItem != null) {
                    mapping.remap(newItem);
                }
            }
        }
    }
}
