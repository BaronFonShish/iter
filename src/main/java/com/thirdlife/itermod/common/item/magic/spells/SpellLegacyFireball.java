package com.thirdlife.itermod.common.item.magic.spells;

import com.thirdlife.itermod.common.item.magic.defaults.SpellItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpellLegacyFireball extends SpellItem {

    public SpellLegacyFireball() {super(new Properties(), "primal", "force", "fire",3, 28, 8, 120);}

    @Override
    public void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower) {

        if (!level.isClientSide) {
            LargeFireball fireball = new LargeFireball(level, player, player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z, 1);
            fireball.setPos(player.getX(), player.getEyeY(), player.getZ());
            fireball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 1.5f * spellpower, 1.0f/spellpower);
            level.addFreshEntity(fireball);

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.FIRECHARGE_USE,
                    SoundSource.PLAYERS, 0.75f, 0.85f);
        }
    }
}
