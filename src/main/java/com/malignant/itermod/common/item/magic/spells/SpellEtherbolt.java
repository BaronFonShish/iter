package com.malignant.itermod.common.item.magic.spells;

import com.malignant.itermod.common.entity.misc.EtherboltEntity;
import com.malignant.itermod.common.item.magic.defaults.SpellItem;
import com.malignant.itermod.common.registry.ModEntities;
import com.malignant.itermod.common.registry.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SpellEtherbolt extends SpellItem {

    public SpellEtherbolt() {super(new Properties(), "arcane", "force", "ether", 1, 10, 2, 5);}

    @Override
    public void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower) {
        float damage = (4f * spellpower);

        EtherboltEntity etherbolt = new EtherboltEntity(ModEntities.ETHERBOLT.get(), level, player, damage);

        Vec3 lookVec = player.getLookAngle();
        Vec3 spawnPos = player.getEyePosition().add(lookVec.scale(0.25));

        etherbolt.setPos(spawnPos.x, spawnPos.y, spawnPos.z);

        float velocity = (1.25f * (1 + spellpower/50));
        float inaccuracy = 0.25f / spellpower;
        velocity = Math.min(Math.max(0.05f, velocity), 50f);

        etherbolt.shootWithDamage(player, lookVec, velocity, inaccuracy, damage);

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                ModSounds.CAST_ARCANE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);

        level.addFreshEntity(etherbolt);
    }
}
