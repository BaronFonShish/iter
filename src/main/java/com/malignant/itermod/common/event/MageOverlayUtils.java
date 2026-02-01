package com.malignant.itermod.common.event;

import com.malignant.itermod.common.item.magic.defaults.SpellItem;
import com.malignant.itermod.common.variables.IterPlayerDataUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class MageOverlayUtils {

    public static String rahh(Player player){
        ItemStack spellbook = SpellBookUtils.findSpellbook(player);
        String slotdisplay = "X";
        if (spellbook.isEmpty()){
            return slotdisplay;
        } else {
            int spellnumber = IterPlayerDataUtils.getSpellSlot(player);
            slotdisplay = Integer.toString(spellnumber);
            return  slotdisplay;
        }
    }

    public static String Grrr(Player player){
        ItemStack spellitem = SpellBookUtils.getSpell(player);
        if (spellitem != null && spellitem.getItem() instanceof SpellItem spell){
            String spellname = spell.getSpellDisplayName();
            if (player.getCooldowns().isOnCooldown(spell)){
                spellname = spellname + " [" + String.format("%.1f", (player.getCooldowns().getCooldownPercent(spell, 0f))*spell.getCooldown(player, spellitem)/20) + "]";
            }
            return spellname;
        }
        return "Empty";
    }
}
