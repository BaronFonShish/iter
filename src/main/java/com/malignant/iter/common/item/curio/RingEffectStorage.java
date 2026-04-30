package com.malignant.iter.common.item.curio;

import com.malignant.iter.common.event.RingEffectManager;
import com.malignant.iter.common.registry.ModAttributes;
import com.malignant.iter.common.registry.ModItems;
import net.minecraft.world.entity.ai.attributes.Attributes;
import top.theillusivec4.curios.api.SlotContext;

public class RingEffectStorage {

    public static void amethyst(SlotContext slotContext) {
        RingEffectManager.tieredRingStuff("iter_ring_spellpower", ModAttributes.SPELL_POWER.get(),
                ModItems.IRON_RING_AMETHYST.get(), ModItems.GOLDEN_RING_AMETHYST.get(), ModItems.NETHERITE_RING_AMETHYST.get(),
                0.05f, 0.075f, 0.1f, slotContext.entity(), 2);
    }

    public static void nostelon(SlotContext slotContext) {
        RingEffectManager.tieredRingStuff("iter_ring_burnout_dissipation", ModAttributes.ETHER_BURNOUT_DISSIPATION.get(),
                ModItems.IRON_RING_NOSTELON.get(), ModItems.GOLDEN_RING_NOSTELON.get(), ModItems.NETHERITE_RING_NOSTELON.get(),
                0.05f, 0.075f, 0.1f, slotContext.entity(), 1);
    }

    public static void abyssquartz(SlotContext slotContext) {
        RingEffectManager.tieredRingStuff("iter_ring_burnout_threshold", ModAttributes.ETHER_BURNOUT_THRESHOLD.get(),
                ModItems.IRON_RING_ABYSSQUARTZ.get(), ModItems.GOLDEN_RING_ABYSSQUARTZ.get(), ModItems.NETHERITE_RING_ABYSSQUARTZ.get(),
                20f, 35f, 50f, slotContext.entity(), 1);
    }

    public static void diamond(SlotContext slotContext) {
        RingEffectManager.tieredRingStuff("iter_ring_defense", Attributes.ARMOR.value(),
                ModItems.IRON_RING_DIAMOND.get(), ModItems.GOLDEN_RING_DIAMOND.get(), ModItems.NETHERITE_RING_DIAMOND.get(),
                2, 3, 4, slotContext.entity(), 1);
    }

    public static void emerald(SlotContext slotContext) {
        RingEffectManager.tieredRingStuff("iter_ring_mining", Attributes.MINING_EFFICIENCY.value(),
                ModItems.IRON_RING_EMERALD.get(), ModItems.GOLDEN_RING_EMERALD.get(), ModItems.NETHERITE_RING_EMERALD.get(),
                0.05f, 0.075f, 0.1f, slotContext.entity(), 2);
    }

    public static void magmanum(SlotContext slotContext) {
        RingEffectManager.tieredRingStuff("iter_ring_melee_damage", Attributes.ATTACK_DAMAGE.value(),
                ModItems.IRON_RING_MAGMANUM.get(), ModItems.GOLDEN_RING_MAGMANUM.get(), ModItems.NETHERITE_RING_MAGMANUM.get(),
                0.05f, 0.075f, 0.1f, slotContext.entity(), 2);
    }

    public static void sanguarnet(SlotContext slotContext) {
        RingEffectManager.tieredRingStuff("iter_ring_regeneration", ModAttributes.REGENERATION.get(),
                ModItems.IRON_RING_SANGUARNET.get(), ModItems.GOLDEN_RING_SANGUARNET.get(), ModItems.NETHERITE_RING_SANGUARNET.get(),
                0.1f, 0.15f, 0.2f, slotContext.entity(), 1);
    }
}
