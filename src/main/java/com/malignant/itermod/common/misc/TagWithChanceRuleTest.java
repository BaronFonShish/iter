package com.malignant.itermod.common.misc;

import com.malignant.itermod.common.registry.ModRuleTests;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

public class TagWithChanceRuleTest extends RuleTest {
    public static final Codec<TagWithChanceRuleTest> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    TagKey.codec(BuiltInRegistries.BLOCK.key()).fieldOf("tag").forGetter(t -> t.tag),
                    Codec.FLOAT.fieldOf("probability").forGetter(t -> t.probability)
            ).apply(instance, TagWithChanceRuleTest::new)
    );

    private final TagKey<Block> tag;
    private final float probability;

    public TagWithChanceRuleTest(TagKey<Block> tag, float probability) {
        this.tag = tag;
        this.probability = probability;
    }

    @Override
    public boolean test(BlockState state, RandomSource random) {
        return state.is(tag) && random.nextFloat() < probability;
    }

    @Override
    protected RuleTestType<?> getType() {
        return ModRuleTests.TAG_WITH_CHANCE.get();
    }
}