package com.malignant.iter.common.item.magic.defaults;

import com.malignant.iter.common.IterModConfig;
import com.malignant.iter.common.event.SpellBookUtils;
import com.malignant.iter.common.registry.ModAttributes;
import com.malignant.iter.common.registry.ModEnchantments;
import com.malignant.iter.common.variables.IterPlayerDataUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class SpellFocus extends Item {

    private final float castingSpeed;
    private final float spellpower;
    private final float etherCost;
    private final int tier;
    private final float arcaneMod;
    private final float occultMod;
    private final float primalMod;

    public SpellFocus(SpellFocusProperties properties, int tier, float spellpower, float castingSpeed, float etherCost, float arcaneMod, float occultMod, float primalMod) {
        super(properties.toItemProperties());
        this.spellpower = spellpower;
        this.castingSpeed = castingSpeed;
        this.etherCost = etherCost;
        this.tier = tier;
        this.arcaneMod = arcaneMod;
        this.occultMod = occultMod;
        this.primalMod = primalMod;
    }

    public static class SpellFocusProperties {
        private int durability = 250;
        private Rarity rarity = Rarity.COMMON;
        private int enchantability = 16;

        public SpellFocusProperties durability(int durability) {
            this.durability = durability;
            return this;
        }

        public SpellFocusProperties rarity(Rarity rarity) {
            this.rarity = rarity;
            return this;
        }

        public SpellFocusProperties enchantability(int enchantability) {
            this.enchantability = enchantability;
            return this;
        }

        public Properties toItemProperties() {
            Properties props = new Properties()
                    .durability(durability)
                    .rarity(rarity);
            return props;
        }

        public int getEnchantability() {
            return enchantability;
        }
    }

    @Override
    public int getEnchantmentValue() {
        return 16;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.CUSTOM;
    }

    public int getTier() {
        return this.tier;
    }

    private static final String spellpowerstring = "iter_foci_spellpower";
    private static final String etherefficinecystring = "iter_foci_ether_efficiency";
    private static final String casttimestring = "iter_foci_cast_time";

    private static final String arcanepowerstring = "iter_foci_arcane_modifier";
    private static final String occultpowerstring = "iter_foci_occult_modifier";
    private static final String primalpowerstring = "iter_foci_primal_modifier";

    private static final UUID SpellPowerUUID = UUID.nameUUIDFromBytes(spellpowerstring.getBytes());
    private static final UUID CastingSpeedUUID = UUID.nameUUIDFromBytes(casttimestring.getBytes());
    private static final UUID EtherEfficiencyUUID = UUID.nameUUIDFromBytes(etherefficinecystring.getBytes());

    private static final UUID ArcanePowerUUID = UUID.nameUUIDFromBytes(arcanepowerstring.getBytes());
    private static final UUID OccultPowerUUID = UUID.nameUUIDFromBytes(occultpowerstring.getBytes());
    private static final UUID PrimalPowerUUID = UUID.nameUUIDFromBytes(primalpowerstring.getBytes());

    @Override
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers() {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

        // Static base attributes (applied when in mainhand)
        // Note: These are base values without enchantment modifiers
        // Enchantment-based modifiers need to be handled dynamically elsewhere
        // or you need to recalculate them when enchantments change

        builder.add(ModAttributes.SPELL_POWER,
                new AttributeModifier(ResourceLocation.parse("iter:spell_power_focus"), (this.spellpower + 1) - 1, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND);

        builder.add(ModAttributes.CASTING_SPEED,
                new AttributeModifier(ResourceLocation.parse("iter:casting_speed_focus"), (this.castingSpeed + 0.5) - 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                EquipmentSlotGroup.MAINHAND);

        builder.add(ModAttributes.ETHER_EFFICIENCY,
                new AttributeModifier(ResourceLocation.parse("iter:ether_efficiency_focus"), (this.etherCost + 0.5) - 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                EquipmentSlotGroup.MAINHAND);

        builder.add(ModAttributes.ARCANE_POWER,
                new AttributeModifier(ResourceLocation.parse("iter:arcane_power_focus"), this.arcaneMod, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                EquipmentSlotGroup.MAINHAND);

        builder.add(ModAttributes.OCCULT_POWER,
                new AttributeModifier(ResourceLocation.parse("iter:occult_power_focus"), this.occultMod, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                EquipmentSlotGroup.MAINHAND);

        builder.add(ModAttributes.PRIMAL_POWER,
                new AttributeModifier(ResourceLocation.parse("iter:primal_power_focus"), this.primalMod, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                EquipmentSlotGroup.MAINHAND);

        return builder.build();
    }

    /**
     * For dynamic enchantment-based modifiers, you'll need to handle them separately.
     * Since attributes are now static on items, you have a few options:
     *
     * 1. Recalculate and update the item's attribute modifiers when enchantments change
     * 2. Use a different system (like an event handler that applies effects based on held item)
     * 3. Handle enchantment bonuses directly in spell casting logic
     *
     * Here's a helper method to get enchantment scaling for when you cast spells:
     */
    public float getSpellPowerWithEnchantments(ItemStack stack) {
        int attunementLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.getHolder(ModEnchantments.FLAYING), stack);
        return (this.spellpower + 1) * (1 + attunementLevel * 0.02f) - 1;
    }

    public float getCastingSpeedWithEnchantments(ItemStack stack) {
        int dexterityLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.getHolder(ModEnchantments.DEXTERITY), stack);
        return (this.castingSpeed + 0.5f) * (1 + dexterityLevel * 0.025f) - 0.5f;
    }

    public float getEtherCostWithEnchantments(ItemStack stack) {
        int rigourLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.getHolder(ModEnchantments.RIGOUR), stack);
        return (this.etherCost + 0.5f) * (1 + rigourLevel * 0.015f) - 0.5f;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, TooltipContext context, java.util.List<net.minecraft.network.chat.Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        list.add(net.minecraft.network.chat.Component.translatable("iterpg.spell.tier",
                net.minecraft.network.chat.Component.translatable("iterpg.spell.tier." + this.getTier())));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        ItemStack spellstack = SpellBookUtils.getSpell(player);
        if (spellstack.isEmpty() || !(spellstack.getItem() instanceof SpellItem)) {
            return InteractionResultHolder.fail(stack);
        }

        if ((spellstack.getItem() instanceof SpellItem spellItem) && (stack.getItem() instanceof SpellFocus focus)) {
            int spellTier = spellItem.getTier();
            int focusTier = focus.getTier();
            if (focusTier < spellTier) {
                return InteractionResultHolder.fail(stack);
            }
        }

        if (player.getCooldowns().isOnCooldown(spellstack.getItem())) {
            player.stopUsingItem();
            return InteractionResultHolder.fail(stack);
        }

        if (spellstack.getItem() instanceof SpellItem spell) {
            float castTime = spell.getCastTime(player, spellstack);

            if (castTime <= 1) {
                if (!level.isClientSide()) {
                    float ether = spell.getManaCost(player, spellstack);
                    float cooldown = spell.getCooldown(player, spellstack);
                    float spellpower = spell.getSpellPower(player, spellstack);

                    this.completeCast(player, (int) cooldown, spellpower, ether, spell, stack, spellstack);
                }
                return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
            } else {
                player.startUsingItem(hand);
                return InteractionResultHolder.consume(stack);
            }
        }

        return InteractionResultHolder.fail(stack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingTicks) {
        if (entity instanceof Player player) {
            ItemStack spellstack = SpellBookUtils.getSpell(player);

            if (spellstack.isEmpty() || !(spellstack.getItem() instanceof SpellItem)) {
                entity.stopUsingItem();
                return;
            }

            if (player.getCooldowns().isOnCooldown(spellstack.getItem())) {
                entity.stopUsingItem();
                return;
            }

            if (spellstack.getItem() instanceof SpellItem spell) {
                int useTime = this.getUseDuration(stack, entity) - remainingTicks;
                float castTime = spell.getCastTime(player, spellstack);

                if ((!level.isClientSide()) && (useTime >= castTime)) {
                    float ether = spell.getManaCost(player, spellstack);
                    float cooldown = spell.getCooldown(player, spellstack);
                    float spellpower = spell.getSpellPower(player, spellstack);

                    this.completeCast(player, (int) cooldown, spellpower, ether, spell, stack, spellstack);
                    entity.stopUsingItem();
                }
            }
        }
    }

    private void completeCast(Player player, int cooldown, float spellpower, float ether, SpellItem spell, ItemStack wand, ItemStack spellstack) {
        if (!player.level().isClientSide()) {
            spell.castSpell(player.level(), player, wand, spellstack, spellpower);

            player.swing(player.getUsedItemHand(), true);
            if (IterModConfig.COMMON.fociDurability.get()) {
                wand.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
            }
            IterPlayerDataUtils.addBurnout(player, ether);

            player.getCooldowns().addCooldown(spell, cooldown);
            float wandCooldown = (float) Math.min(cooldown * 0.5f, Math.log(cooldown + 20));
            player.getCooldowns().addCooldown(this, (int) wandCooldown);
        }
    }
}