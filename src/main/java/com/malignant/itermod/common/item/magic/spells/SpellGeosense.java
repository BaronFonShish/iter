package com.malignant.itermod.common.item.magic.spells;

import com.malignant.itermod.common.item.magic.defaults.SpellItem;
import com.malignant.itermod.common.registry.ModSounds;
import net.minecraft.core.BlockPos;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;


public class SpellGeosense extends SpellItem {

    public SpellGeosense() {super(new Properties(), "primal", "conveyance", "earth",1, 20, 4, 25);}

    @Override
    public void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower) {
        int iterations = (int) (50 + (50 * spellpower));

        float dist = 1.5f;
        double yheight = player.getEyePosition().y;
        double xdir = player.getLookAngle().x;
        double ydir = player.getLookAngle().y;
        double zdir = player.getLookAngle().z;
        boolean flag = true;

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                ModSounds.CAST_ARCANE.get(), SoundSource.PLAYERS, 0.5F, 1.0F);

        level.playSound(null, player.blockPosition(),
                SoundEvents.WARDEN_TENDRIL_CLICKS,
                SoundSource.PLAYERS, 0.8f, 1.2f);

        Block targetBlock = Blocks.AIR;
        ItemStack offhandStack = player.getOffhandItem();
        if (offhandStack.isEmpty()){
            targetBlock = Blocks.AIR;
        } else {targetBlock = Block.byItem(offhandStack.getItem());}

        for (int i = 0; i < iterations; i++) {
            if (flag) {

                BlockPos checkpos = BlockPos.containing(player.getX() + xdir * dist, yheight + ydir * dist, player.getZ() + zdir * dist);
                BlockState testblock = level.getBlockState(checkpos);

                if (findBlock(testblock, targetBlock)){
                    flag = false;
                    int distance = (int) dist;
                    String blockName = targetBlock == Blocks.AIR ? "Air" : targetBlock.getName().getString();
                    Component message = Component.literal(
                            String.format("§aFound §e%s §a%d blocks away.",
                                    blockName, distance)
                    );
                    player.sendSystemMessage(message);
                }

                dist = dist + 0.5f;
            }
        }
    }

    public boolean findBlock(BlockState blockfortest, Block target){
        if (blockfortest.getBlock() == target) { return true; }
        if (target == Blocks.AIR){
            return (!blockfortest.isSolid() || blockfortest.isAir());
        }
        return false;
    }
}