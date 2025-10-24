package com.thirdlife.itermod.common.item.magic.defaults;

import com.thirdlife.itermod.common.event.SpellBookUtils;
import com.thirdlife.itermod.common.registry.ModCapabilities;
import com.thirdlife.itermod.common.variables.EtherBurnoutPacket;
import com.thirdlife.itermod.iterMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

public abstract class SpellFocus extends Item {

    public SpellFocus(SpellFocusProperties properties) {
        super(properties.toItemProperties());
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

        public Item.Properties toItemProperties() {
            return new Item.Properties()
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

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        ItemStack spellstack = SpellBookUtils.getSpell(player);
        if (spellstack.isEmpty() || !(spellstack.getItem() instanceof SpellItem)) {
            return InteractionResultHolder.fail(stack);
        }

        if (player.getCooldowns().isOnCooldown(spellstack.getItem())){
            player.stopUsingItem();
            return InteractionResultHolder.fail(stack);
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
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

                if (useTime == 10){

                }

                if ((!level.isClientSide()) && ((useTime >= castTime)||(castTime<=1))) {
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
