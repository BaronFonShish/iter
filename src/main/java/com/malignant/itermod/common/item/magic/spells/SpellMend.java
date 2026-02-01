package com.malignant.itermod.common.item.magic.spells;

import com.malignant.itermod.common.item.magic.defaults.SpellItem;
import com.malignant.itermod.common.registry.ModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpellMend extends SpellItem {

    public SpellMend() {
        super(new Properties(), "arcane", "form","ether", 2, 50, 75, 1800);
    }

    @Override
    public void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower) {

        if (level.isClientSide) return;
        int repairPoints = (int) (25 * spellpower);
        if (repairPoints == 0) return;


        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                ModSounds.CAST_ARCANE.get(), SoundSource.PLAYERS, 0.8F, 1.0F);

        level.playSound(null, player.blockPosition(),
                SoundEvents.ENCHANTMENT_TABLE_USE,
                SoundSource.PLAYERS, 0.8f, 1.2f);

        while (repairPoints > 0) {

            boolean repaired = false;
            for (int i = 0; i < 2; i++) {
                EquipmentSlot slot = (i==0) ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
                ItemStack item = player.getItemBySlot(slot);
                if (repairPoints <= 0) break;
                if (!item.isEmpty() && item.isDamaged()) {
                    repairPoints--;

                    repairItemSlot(player, item, slot);
                    repaired = true;
                }
            }
                for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}){
                    ItemStack item = player.getItemBySlot(slot);
                    if (repairPoints <= 0) break;
                    if (!item.isEmpty() && item.isDamaged())  {
                      repairPoints--;

                      repairItemSlot(player, item, slot);
                      repaired = true;
                }
            }

            for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
                ItemStack item = player.getInventory().getItem(slot);
                if (repairPoints <= 0) break;
                if (!item.isEmpty() && item.isDamaged()) {
                    repairPoints--;

                    repairItem(player, item, slot);
                    repaired = true;
                }
            }
            if ((!repaired)||(repairPoints <= 0)) break;
            player.getInventory().setChanged();
        }
    }

    public void repairItem(Player player, ItemStack item, int slot){
        int newDamage = item.getDamageValue() - 1;
        item.setDamageValue(Math.max(newDamage, 0));
        player.getInventory().setItem(slot, item);

    }

    public void repairItemSlot(Player player, ItemStack item, EquipmentSlot slot){
        int newDamage = item.getDamageValue() - 1;
        item.setDamageValue(Math.max(newDamage, 0));
        player.setItemSlot(slot, item);
    }

}
