package com.thirdlife.itermod.common.event;

import com.thirdlife.itermod.common.item.magic.defaults.SpellItem;
import com.thirdlife.itermod.common.variables.IterPlayerDataUtils;
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
        if (spellitem.getItem() instanceof SpellItem spell){
            return spell.getSpellDisplayName();
        }
        return "Empty";
    }
}
