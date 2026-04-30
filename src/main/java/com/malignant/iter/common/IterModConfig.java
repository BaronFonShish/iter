package com.malignant.iter.common;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.ModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

public class IterModConfig {
    public static final ModConfigSpec COMMON_SPEC;
    public static final CommonConfig COMMON;

    static {
        final Pair<CommonConfig, ModConfigSpec> commonSpecPair = new ModConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
    }


    public static class CommonConfig {
//        public final ModConfigSpec.Range;
//        public final ModConfigSpec.DoubleValue;
//        public final ModConfigSpec.IntValue;
        public final ModConfigSpec.BooleanValue fociDurability;
        public final ModConfigSpec.BooleanValue giantSpiders;
        public final ModConfigSpec.BooleanValue ghouls;
        public final ModConfigSpec.BooleanValue abyssQuartz;
        public final ModConfigSpec.BooleanValue spiderEggs;
        public final ModConfigSpec.BooleanValue ancientVases;
        public final ModConfigSpec.BooleanValue rotroots;
        public final ModConfigSpec.BooleanValue etherBlooms;
        public final ModConfigSpec.BooleanValue abyssquartzGrowth;

        public CommonConfig(ModConfigSpec.Builder builder) {

            builder.comment("Common configuration for Iter").push("common");

            ///
            builder.comment("World generation").push("world_generation");

            ancientVases = builder.comment("Enable ancient vases generation")
                    .define("ancientVases", true);

            abyssQuartz = builder.comment("Enable abyssquartz clusters generation")
                    .define("abyssQuartz", true);

            spiderEggs = builder.comment("Enable spider eggs generation")
                    .define("spiderEggs", true);

            etherBlooms = builder.comment("Enable etherblooms generation")
                    .define("etherBlooms", true);

            rotroots = builder.comment("Enable rotroots generation")
                    .define("rotroots", true);

            builder.pop();

            ///
            builder.comment("Functions").push("functions");

            fociDurability = builder.comment("Enable foci item damage from spellcasting")
                    .define("fociDurability", false);

            abyssquartzGrowth = builder.comment("Enable abyssquartz blocks growing new clusters")
                    .define("abyssquartzGrowth", true);

            builder.pop();

            ///
            builder.comment("Mobs").push("mobs");

            giantSpiders = builder.comment("Enable Giant Spiders spawning")
                    .define("giantSpiders", true);

            ghouls = builder.comment("Enable Ghouls spawning")
                    .define("ghouls", true);

            builder.pop();
/// final
            builder.pop();
        }
    }

    public static void register() {
        ModLoadingContext.get().getActiveContainer().registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, COMMON_SPEC, "iter.toml");
    }
}