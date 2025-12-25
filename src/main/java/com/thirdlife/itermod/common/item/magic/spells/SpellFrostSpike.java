package com.thirdlife.itermod.common.item.magic.spells;

import com.thirdlife.itermod.client.model.FrostSpikeModel;
import com.thirdlife.itermod.common.entity.misc.EtherboltEntity;
import com.thirdlife.itermod.common.entity.misc.FrostSpikeEntity;
import com.thirdlife.itermod.common.item.magic.defaults.SpellItem;
import com.thirdlife.itermod.common.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class SpellFrostSpike extends SpellItem {

    public SpellFrostSpike() {super(new Properties(), "elemental", "force", 2, 15, 4, 30);}

    @Override
    public void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower) {
        float damage = (6f * spellpower);

        FrostSpikeEntity frostSpike = new FrostSpikeEntity(ModEntities.FROST_SPIKE.get(), level, player, damage);

        Vec3 lookVec = player.getLookAngle();
        Vec3 spawnPos = player.getEyePosition().add(lookVec.scale(0.25));

        frostSpike.setPos(spawnPos.x, spawnPos.y, spawnPos.z);

        float velocity = (1.75f * (1 + spellpower/50));
        float inaccuracy = 0.25f / spellpower;
        velocity = Math.min(Math.max(0.05f, velocity), 50f);

        frostSpike.shootWithDamage(player, lookVec, velocity, inaccuracy, damage);

        level.playSound(null, BlockPos.containing(player.position()), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.experience_orb.pickup"))), SoundSource.PLAYERS, (float) 0.5, (float) 1);

        level.addFreshEntity(frostSpike);
    }
}
