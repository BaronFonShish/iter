package com.malignant.itermod.common.item.magic.spells;

import com.malignant.itermod.common.entity.misc.FlameboltEntity;
import com.malignant.itermod.common.item.magic.defaults.SpellItem;
import com.malignant.itermod.common.registry.ModEntities;
import com.malignant.itermod.common.registry.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SpellFlamebolt extends SpellItem {

    public SpellFlamebolt() {super(new Properties(), "primal", "force", "fire",2, 15, 4, 27);}

    @Override
    public void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower) {
        float damage = (5f * spellpower);

        FlameboltEntity flamebolt = new FlameboltEntity(ModEntities.FLAMEBOLT.get(), level, player, damage);

        Vec3 lookVec = player.getLookAngle();
        Vec3 spawnPos = player.getEyePosition().add(lookVec.scale(0.25));

        flamebolt.setPos(spawnPos.x, spawnPos.y, spawnPos.z);

        float velocity = (1.5f * (1 + spellpower/50));
        float inaccuracy = 0.25f / spellpower;
        velocity = Math.min(Math.max(0.05f, velocity), 50f);

        flamebolt.shootWithDamage(player, lookVec, velocity, inaccuracy, damage);

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                ModSounds.CAST_ARCANE.get(), SoundSource.PLAYERS, 0.8F, 1.0F);

        level.addFreshEntity(flamebolt);
    }
}
