package com.thirdlife.itermod.common.datagen;

import com.thirdlife.itermod.common.block.RotrootBlock;
import com.thirdlife.itermod.common.registry.ModBlocks;
import com.thirdlife.itermod.common.registry.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }


    @Override
    protected void generate() {
        LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.ROTROOT.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RotrootBlock.AGE, 3));
        this.add(ModBlocks.ROTROOT.get(), this.createCropDrops(ModBlocks.ROTROOT.get(), ModItems.ROTROOT_SEEDS.get(), ModItems.ROTROOT.get(), lootitemcondition$builder));
    }
}

