package com.thirdlife.itermod.common.item.magic.defaults;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.thirdlife.itermod.common.event.SpellBookUtils;
import com.thirdlife.itermod.common.registry.ModAttributes;
import com.thirdlife.itermod.common.registry.ModCapabilities;
import com.thirdlife.itermod.common.variables.EtherBurnoutPacket;
import com.thirdlife.itermod.iterMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public abstract class SpellFocus extends Item {

    private final int castingSpeed;
    private final int spellpower;
    private final int etherCost;

    public SpellFocus(SpellFocusProperties properties, int spellpower, int castingSpeed, int etherCost) {
        super(properties.toItemProperties());
        this.spellpower = spellpower;
        this.castingSpeed = castingSpeed;
        this.etherCost = etherCost;

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

    String spellpowerstring = "iter_foci_spellpower";
    String etherefficinecystring = "iter_foci_ether_efficiency";
    String casttimerstring = "iter_foci_cast_time";

    final UUID SpellPowerUUID = UUID.nameUUIDFromBytes((spellpowerstring.getBytes()));
    final UUID CastingSpeedUUID = UUID.nameUUIDFromBytes((casttimerstring.getBytes()));
    final UUID EtherEfficiencyUUID = UUID.nameUUIDFromBytes((etherefficinecystring.getBytes()));

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers (@NotNull final EquipmentSlot slot, final ItemStack itemStack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        if (slot == EquipmentSlot.MAINHAND)
        {
            builder.put(ModAttributes.SPELL_POWER.get(), new AttributeModifier(SpellPowerUUID, "Foci modifier", this.spellpower, AttributeModifier.Operation.ADDITION));
            builder.put(ModAttributes.CASTING_SPEED.get(), new AttributeModifier(CastingSpeedUUID, "Foci modifier", this.castingSpeed, AttributeModifier.Operation.MULTIPLY_BASE));
            builder.put(ModAttributes.SPELL_POWER.get(), new AttributeModifier(EtherEfficiencyUUID, "Foci modifier", this.etherCost, AttributeModifier.Operation.MULTIPLY_BASE));
            Map<Enchantment, Integer> itemEnchants = itemStack.getAllEnchantments();
        }

        return builder.build();
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        ItemStack spellstack = SpellBookUtils.getSpell(player);
        if (spellstack.isEmpty() || !(spellstack.getItem() instanceof SpellItem)) {
            return InteractionResultHolder.fail(stack);
        }

        if (player.getCooldowns().isOnCooldown(spellstack.getItem())) {
            player.stopUsingItem();
            return InteractionResultHolder.fail(stack);
        }

        if (spellstack.getItem() instanceof SpellItem spell) {
            float castTime = spell.getCastTime(player);


            if (castTime <= 1) {
                if (!level.isClientSide()) {
                    float ether = spell.getManaCost(player);
                    float cooldown = spell.getCooldown(player);
                    float spellpower = spell.getSpellPower(player);

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
                float castTime = spell.getCastTime(player);


                if ((!level.isClientSide()) && ((useTime >= castTime))) {
                    float ether = spell.getManaCost(player);
                    float cooldown = spell.getCooldown(player);
                    float spellpower = spell.getSpellPower(player);

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

            player.getCapability(ModCapabilities.MAGE_DATA).ifPresent(mageData -> {
                mageData.addEtherBurnout(ether);
                if (player instanceof ServerPlayer serverPlayer) {
                    iterMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> serverPlayer),
                            new EtherBurnoutPacket(mageData.getEtherBurnout()));
                }
            });

            player.getCooldowns().addCooldown(spell, cooldown);
            player.getCooldowns().addCooldown(this, cooldown);
        }
    }
}
