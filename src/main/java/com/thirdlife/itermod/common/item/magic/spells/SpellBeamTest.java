package com.thirdlife.itermod.common.item.magic.spells;

import com.thirdlife.itermod.common.entity.misc.EtherboltEntity;
import com.thirdlife.itermod.common.entity.misc.StraightBeam;
import com.thirdlife.itermod.common.item.magic.defaults.SpellItem;
import com.thirdlife.itermod.common.registry.ModEntities;
import com.thirdlife.itermod.common.registry.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SpellBeamTest extends SpellItem {

    public SpellBeamTest() {super(new Properties(), "arcane", "force", "ether", 1, 10, 0, 5);}

    @Override
    public void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower) {
        Vec3 startPos = player.getEyePosition();

        Vec3 lookDirection = player.getLookAngle();
        double maxDistance = 50;

        startPos = startPos.add(lookDirection.scale(2));
        Vec3 endPos = startPos.add(lookDirection.scale(maxDistance));

        Vec3 up = new Vec3(0, 1, 0);
        Vec3 side = up.cross(lookDirection).normalize();
        float up_offset = 0.1f;
        float side_offset = 0.15f;
        if (player.getMainArm() == HumanoidArm.RIGHT){
            side_offset *= -1;
        }

        startPos = startPos
                .add(side.scale(side_offset)
                .add(0, up_offset, 0));


        StraightBeam beam = new StraightBeam(level, startPos, endPos, 500, 5, false, true, false);

        level.addFreshEntity(beam);
    }
}