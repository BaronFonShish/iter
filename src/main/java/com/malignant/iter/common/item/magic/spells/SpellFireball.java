package com.malignant.iter.common.item.magic.spells;

import com.malignant.iter.common.entity.misc.FireballEntity;
import com.malignant.iter.common.item.magic.defaults.SpellItem;
import com.malignant.iter.common.registry.ModEntities;
import com.malignant.iter.common.registry.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SpellFireball extends SpellItem {

    public SpellFireball() {super(new Properties(), "primal", "force", "fire",3, 35, 8, 70);}

    @Override
    public void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower) {
        float damage = (16f * spellpower);

        FireballEntity fireball = new FireballEntity(ModEntities.FIREBALL.get(), level, player, damage);

        Vec3 lookVec = player.getLookAngle();
        Vec3 spawnPos = player.getEyePosition().add(lookVec.scale(0.25));

        fireball.setPos(spawnPos.x, spawnPos.y, spawnPos.z);

        float velocity = (2f * (1 + spellpower/50));
        float inaccuracy = 0.25f / spellpower;
        velocity = Math.min(Math.max(0.05f, velocity), 50f);

        fireball.shootWithDamage(player, lookVec, velocity, inaccuracy, damage);

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                ModSounds.CAST_ARCANE.get(), SoundSource.PLAYERS, 0.8F, 1.0F);

        level.addFreshEntity(fireball);
    }
}
