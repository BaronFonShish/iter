package com.thirdlife.itermod.common.item.magic.spells;

import com.thirdlife.itermod.common.item.magic.defaults.SpellItem;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class SpellLesserHeal extends SpellItem {

    public SpellLesserHeal() {
        super(new Properties(), 40, 10, 40);
    }

    @Override
    public void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower) {

        int duration = (int)(100 * spellpower);

        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, duration, 0));

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);

        for (int i = 0; i < 8; i++) {
            level.addParticle(ParticleTypes.HEART,
                    player.getX() + (level.random.nextDouble() - 0.5) * 2,
                    player.getY() + 1 + level.random.nextDouble(),
                    player.getZ() + (level.random.nextDouble() - 0.5) * 2,
                    0, 0.1, 0);
        }
    }

}
