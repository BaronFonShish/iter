package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.iterMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> ROTROOT_GROWABLE = tag("rotroot_growable");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(iterMod.MOD_ID, name));
        }
    }
}