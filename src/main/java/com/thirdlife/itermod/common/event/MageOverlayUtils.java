package com.thirdlife.itermod.common.event;

import com.thirdlife.itermod.common.variables.MageUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class MageOverlayUtils {

    public static String rahh(Player player){
        ItemStack spellbook = SpellBookUtils.findSpellbook(player);
        String slotdisplay = "X";
        if (spellbook.isEmpty()){
            return slotdisplay;
        } else {
            int spellnumber = MageUtils.getSpellSlot(player);
            slotdisplay = Integer.toString(spellnumber);
            return  slotdisplay;
        }
    }
}
