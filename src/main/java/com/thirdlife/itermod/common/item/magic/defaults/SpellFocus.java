package com.thirdlife.itermod.common.item.magic.defaults;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.thirdlife.itermod.common.event.SpellBookUtils;
import com.thirdlife.itermod.common.registry.ModAttributes;
import com.thirdlife.itermod.common.registry.ModCapabilities;
import com.thirdlife.itermod.common.registry.ModEnchantments;
import com.thirdlife.itermod.common.variables.IterPlayerDataUtils;
import com.thirdlife.itermod.iterMod;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class SpellFocus extends Item {

    private final float castingSpeed;
    private final float spellpower;
    private final float etherCost;
    private final int tier;

    public SpellFocus(SpellFocusProperties properties, int tier, float spellpower, float castingSpeed, float etherCost) {
        super(properties.toItemProperties());
        this.spellpower = spellpower;
        this.castingSpeed = castingSpeed;
        this.etherCost = etherCost;
        this.tier = tier;
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
            return new Properties()
                    .durability(durability)
                    .rarity(rarity);
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
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    public int getTier(){
        return this.tier;
    }

    private static final String spellpowerstring = "iter_foci_spellpower";
    private static final String etherefficinecystring = "iter_foci_ether_efficiency";
    private static final String casttimestring = "iter_foci_cast_time";

    private static final UUID SpellPowerUUID = UUID.nameUUIDFromBytes((spellpowerstring.getBytes()));
    private static final UUID CastingSpeedUUID = UUID.nameUUIDFromBytes((casttimestring.getBytes()));
    private static final UUID EtherEfficiencyUUID = UUID.nameUUIDFromBytes((etherefficinecystring.getBytes()));

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers (@NotNull final EquipmentSlot slot, final ItemStack itemStack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();

        int attunement_level = (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.ATTUNEMENT.get(), itemStack));
        int dexterity_level = (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.DEXTERITY.get(), itemStack));
        int rigour_level = (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.RIGOUR.get(), itemStack));

        if (slot == EquipmentSlot.MAINHAND)
        {
            builder.put(ModAttributes.SPELL_POWER.get(), new AttributeModifier(SpellPowerUUID, "Foci modifier",
                    ((this.spellpower + 1) * (1 + attunement_level * 0.02) -1),
                    AttributeModifier.Operation.ADDITION));
            builder.put(ModAttributes.CASTING_SPEED.get(), new AttributeModifier(CastingSpeedUUID, "Foci modifier",
                    ((this.castingSpeed + 0.5) * (1 + dexterity_level * 0.025) -0.5),
                    AttributeModifier.Operation.MULTIPLY_TOTAL));
            builder.put(ModAttributes.ETHER_EFFICIENCY.get(), new AttributeModifier(EtherEfficiencyUUID, "Foci modifier",
                    ((this.etherCost + 0.5) * (1 + rigour_level * 0.015) -0.5),
                    AttributeModifier.Operation.MULTIPLY_TOTAL));
            Map<Enchantment, Integer> itemEnchants = itemStack.getAllEnchantments();
        }

        return builder.build();
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("iterpg.spell.tier", Component.translatable("iterpg.spell.tier." + this.getTier())));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        ItemStack spellstack = SpellBookUtils.getSpell(player);
        if (spellstack.isEmpty() || !(spellstack.getItem() instanceof SpellItem)) {
            return InteractionResultHolder.fail(stack);
        }

        if ((spellstack.getItem() instanceof SpellItem spellItem)&&(stack.getItem() instanceof SpellFocus focus)){
            int spellTier = spellItem.getTier();
            int focusTier = focus.getTier();
            if (focusTier < spellTier){
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

            if (player.getCooldowns().isOnCooldown(spellstack.getItem())){
                entity.stopUsingItem();
                return;
            }

            if (spellstack.getItem() instanceof SpellItem spell){
                int useTime = player.getTicksUsingItem();
                float castTime = spell.getCastTime(player, spellstack);


                if ((!level.isClientSide()) && ((useTime >= castTime))) {
                    float ether = spell.getManaCost(player, spellstack);
                    float cooldown = spell.getCooldown(player, spellstack);
                    float spellpower = spell.getSpellPower(player, spellstack);

                    this.completeCast(player, (int)cooldown, spellpower, ether, spell, stack, spellstack);
                    entity.stopUsingItem();
                }
            }
        }
    }

    private void completeCast(Player player, int cooldown, float spellpower, float ether, SpellItem spell, ItemStack wand, ItemStack spellstack) {
        if (!player.level().isClientSide()) {
            spell.castSpell(player.level(), player, wand, spellstack, spellpower);

            wand.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(p.getUsedItemHand()));
            player.swing(InteractionHand.MAIN_HAND, true);
            IterPlayerDataUtils.addBurnout(player, ether);

            player.getCooldowns().addCooldown(spell, cooldown);
            float wandCooldown = (float) Math.min(cooldown * 0.5f, Math.log(cooldown+20));
            player.getCooldowns().addCooldown(this, (int)wandCooldown);
        }
    }
}
