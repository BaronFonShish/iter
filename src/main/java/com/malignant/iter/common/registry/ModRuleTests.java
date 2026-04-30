package com.malignant.iter.common.registry;

import com.malignant.iter.common.misc.TagWithChanceRuleTest;
import com.malignant.iter.IterMod;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRuleTests {
    public static final DeferredRegister<RuleTestType<?>> RULE_TEST_TYPES =
            DeferredRegister.create(Registries.RULE_TEST, IterMod.MOD_ID);

    public static final DeferredHolder<RuleTestType<?>, RuleTestType<TagWithChanceRuleTest>> TAG_WITH_CHANCE =
            RULE_TEST_TYPES.register("tag_with_chance",
                    () -> () -> (TagWithChanceRuleTest.CODEC));
}