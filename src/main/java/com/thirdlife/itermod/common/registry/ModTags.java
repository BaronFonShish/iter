package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.iterMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> ROTROOT_GROWABLE = tag("rotroot_growable");
        public static final TagKey<Block> ETHERBLOOM_SOIL = tag("etherbloom_soil");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(iterMod.MOD_ID, name));
        }
    }
    public static class Items {
        public static final TagKey<Item> MAGICAL_ITEM = tag("magical_item");

//        public static final TagKey<Item> SPELL_TIER_PRIMITIVE = tag("spell_tier_primitive");
//        public static final TagKey<Item> SPELL_TIER_NOVICE = tag("spell_tier_novice");
//        public static final TagKey<Item> SPELL_TIER_ADVANCED = tag("spell_tier_advanced");
//        public static final TagKey<Item> SPELL_TIER_EXPERT = tag("spell_tier_expert");
//        public static final TagKey<Item> SPELL_TIER_FABLED = tag("spell_tier_fabled");


        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(iterMod.MOD_ID, name));
        }
    }
}