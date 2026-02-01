package com.malignant.itermod.common.item.magic.spells;

import com.malignant.itermod.common.item.magic.defaults.SpellItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpellLesserHeal extends SpellItem {

    public SpellLesserHeal() {
        super(new Properties(), "arcane","body","life",1,40, 10, 100);
    }

    @Override
    public void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower) {

        float healAmount = 5f * spellpower;
        int duration = (int)(100 + 20 * spellpower);

        player.heal(healAmount);
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, duration, 0));

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

}
