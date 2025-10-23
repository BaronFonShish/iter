package com.thirdlife.itermod.common.item.magic;

import com.thirdlife.itermod.common.registry.ModCapabilities;
import com.thirdlife.itermod.common.variables.EtherBurnoutPacket;
import com.thirdlife.itermod.common.variables.MageData;
import com.thirdlife.itermod.common.variables.MageDataProvider;
import com.thirdlife.itermod.common.variables.MageUtils;
import com.thirdlife.itermod.iterMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

public class BoneStaff extends Item {
    public BoneStaff() {
        super(new Item.Properties().durability(244).rarity(Rarity.COMMON));
    }

    @Override
    public int getEnchantmentValue() {
        return 16;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000; // Maximum use duration
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {

            player.getCapability(ModCapabilities.MAGE_DATA).ifPresent(mageData ->{
                mageData.addEtherBurnout(5);
                iterMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
                        new EtherBurnoutPacket(mageData.getEtherBurnout()));});
        }

        return InteractionResultHolder.success(stack);
    }

//    @Override
//    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingTicks) {
//        if (entity instanceof Player player) {
//            PlayerMagicData.get(player).ifPresent(data -> {
//                if (data.isCasting()) {
//                    int ticksCast = data.getCastTime() - remainingTicks;
//                    ItemStack spellStack = data.getCastingSpell();
//
//                    if (spellStack.getItem() instanceof SpellItem spell) {
//                        spell.onCastingTick(level, player, stack, spellStack, ticksCast);
//
//                        // Check if casting is complete
//                        if (ticksCast >= data.getTotalCastTime()) {
//                            completeCast(player, stack, spellStack, spell);
//                            player.stopUsingItem();
//                        }
//                    }
//                }
//            });
//        }
//    }

    private void completeCast(Player player, ItemStack wand, ItemStack spellStack, SpellItem spell) {
        spell.castSpell(player.level(), player, wand, spellStack);

        // Apply cooldown
        player.getCooldowns().addCooldown(this, spell.getCooldown(wand));

    }


}
