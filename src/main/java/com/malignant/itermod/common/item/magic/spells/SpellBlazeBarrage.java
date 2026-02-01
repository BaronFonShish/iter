package com.malignant.itermod.common.item.magic.spells;

import com.malignant.itermod.common.item.magic.defaults.SpellItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpellBlazeBarrage extends SpellItem {

    public SpellBlazeBarrage() {super(new Properties(), "primal", "force", "fire",2, 25, 5, 30);}

    @Override
    public void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower) {

        if (!level.isClientSide) {
            for (int i=0; i<3; i++) {
                SmallFireball fireball = new SmallFireball(level, player, player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z);
                fireball.setPos(player.getX(), player.getEyeY(), player.getZ());
                fireball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, (1 + spellpower/5 + (float) Math.random() * 0.5f), 16.0f / (1 +spellpower/4));
                level.addFreshEntity(fireball);
            }

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.FIRECHARGE_USE,
                    SoundSource.PLAYERS, 0.75f, 1f);
        }
    }
}
