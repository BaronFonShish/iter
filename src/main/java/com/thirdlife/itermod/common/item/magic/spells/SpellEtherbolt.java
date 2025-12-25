package com.thirdlife.itermod.common.item.magic.spells;

import com.thirdlife.itermod.common.entity.misc.EtherboltEntity;
import com.thirdlife.itermod.common.item.magic.defaults.SpellItem;
import com.thirdlife.itermod.common.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class SpellEtherbolt extends SpellItem {

    public SpellEtherbolt() {super(new Properties(), "arcane", "force", 1, 10, 2, 5);}

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

        level.playSound(null, BlockPos.containing(player.position()), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.experience_orb.pickup"))), SoundSource.PLAYERS, (float) 0.5, (float) 1);

        level.addFreshEntity(etherbolt);
    }
}
