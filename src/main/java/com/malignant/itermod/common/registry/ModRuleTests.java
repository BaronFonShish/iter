package com.malignant.itermod.common.registry;

import com.malignant.itermod.common.misc.TagWithChanceRuleTest;
import com.malignant.itermod.iterMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModRuleTests {
    public static final DeferredRegister<RuleTestType<?>> RULE_TEST_TYPES =
            DeferredRegister.create(BuiltInRegistries.RULE_TEST.key(), iterMod.MOD_ID);

    public static final RegistryObject<RuleTestType<TagWithChanceRuleTest>> TAG_WITH_CHANCE =
            RULE_TEST_TYPES.register("tag_with_chance",
                    () -> () -> TagWithChanceRuleTest.CODEC);
}