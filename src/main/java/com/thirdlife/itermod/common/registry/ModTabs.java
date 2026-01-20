package com.thirdlife.itermod.common.registry;

import com.thirdlife.itermod.iterMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, iterMod.MOD_ID);
    public static final RegistryObject<CreativeModeTab> ITER_MAGIC = REGISTRY.register("iter_magic",
            () -> CreativeModeTab.builder().title(Component.translatable("item_group.iter.iter_magic")).icon(() -> new ItemStack(ModItems.SPELL_ETHERBOLT.get())).displayItems((parameters, tabData) -> {
                        tabData.accept(ModBlocks.SPELLWEAVER_TABLE.get().asItem());
                        tabData.accept(ModItems.SPELL_BOOK.get());

                        tabData.accept(ModItems.BONE_STAFF.get());
                        tabData.accept(ModItems.AMETHYST_WAND.get());
                        tabData.accept(ModItems.ANCIENT_STAFF.get());
                        tabData.accept(ModItems.NOSTELON_STAFF.get());
                        tabData.accept(ModItems.DIAMOND_STAFF.get());
                        tabData.accept(ModItems.MAGMANUM_STAFF.get());

                        ///Спеллы

                        tabData.accept(ModItems.SPELL_ETHERBOLT.get());
                        tabData.accept(ModItems.SPELL_LESSER_HEAL.get());
                        tabData.accept(ModItems.SPELL_MEND.get());
                        tabData.accept(ModItems.SPELL_LEAP.get());
                        tabData.accept(ModItems.SPELL_RECALL.get());
                        tabData.accept(ModItems.SPELL_ARCANE_BEAM.get());

                        tabData.accept(ModItems.SPELL_IGNITE.get());
                        tabData.accept(ModItems.SPELL_SPLASHES.get());
                        tabData.accept(ModItems.SPELL_FROST_SPIKE.get());
                        tabData.accept(ModItems.SPELL_DISCHARGE.get());
                        tabData.accept(ModItems.SPELL_BLAZE_BARRAGE.get());
                        tabData.accept(ModItems.SPELL_LEGACY_FIREBALL.get());


                        tabData.accept(ModItems.SPELL_WITCH_LASH.get());
                    })
                    .build());
}