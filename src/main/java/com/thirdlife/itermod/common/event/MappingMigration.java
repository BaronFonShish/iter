package com.thirdlife.itermod.common.event;


import com.thirdlife.itermod.iterMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

@Mod.EventBusSubscriber(modid = iterMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)

public class MappingMigration {

    @SubscribeEvent
    public static void onMissingMappings(MissingMappingsEvent event) {
        /// BLOCKS
        for (MissingMappingsEvent.Mapping<Block> mapping : event.getMappings(ForgeRegistries.Keys.BLOCKS, "iter_rpg")) {
            ResourceLocation oldId = mapping.getKey();

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
