package com.thirdlife.itermod.common;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

public class IterModConfig {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final CommonConfig COMMON;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
    }


    public static class CommonConfig {
//        public final ForgeConfigSpec.Range;
//        public final ForgeConfigSpec.DoubleValue;
//        public final ForgeConfigSpec.IntValue;
        public final ForgeConfigSpec.BooleanValue fociDurability;
        public final ForgeConfigSpec.BooleanValue giantSpiders;
        public final ForgeConfigSpec.BooleanValue ghouls;
        public final ForgeConfigSpec.BooleanValue abyssQuartz;
        public final ForgeConfigSpec.BooleanValue spiderEggs;
        public final ForgeConfigSpec.BooleanValue ancientVases;
        public final ForgeConfigSpec.BooleanValue rotroots;
        public final ForgeConfigSpec.BooleanValue etherBlooms;
        public final ForgeConfigSpec.BooleanValue abyssquartzGrowth;

        public CommonConfig(ForgeConfigSpec.Builder builder) {

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
                    .define("fociDurability", true);

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
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, COMMON_SPEC, "iter.toml");
    }
}