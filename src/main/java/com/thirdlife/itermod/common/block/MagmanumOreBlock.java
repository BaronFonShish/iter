package com.thirdlife.itermod.common.block;

import com.thirdlife.itermod.common.event.VaseBrokenEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;


public class MagmanumOreBlock extends DropExperienceBlock {
    public MagmanumOreBlock() {
        super(Properties.of().instrument(NoteBlockInstrument.BIT).sound(SoundType.NETHER_ORE).strength(1.6f, 6f).lightLevel(s -> 4)
                .requiresCorrectToolForDrops(), UniformInt.of(3, 5));
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 15;
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
        if (player.getInventory().getSelected().getItem() instanceof PickaxeItem tieredItem)
            return tieredItem.getTier().getLevel() >= 3;
        return false;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState blockstate, Level world, BlockPos pos, Player entity, boolean willHarvest, FluidState fluid) {
        boolean retval = super.onDestroyedByPlayer(blockstate, world, pos, entity, willHarvest, fluid);

        if (entity instanceof ServerPlayer serverPlayer) {
            ItemStack mainHandItem = serverPlayer.getMainHandItem();

            if ((EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, mainHandItem) == 0) && (serverPlayer.gameMode.getGameModeForPlayer() != GameType.CREATIVE)) {
                Magmanumexplode(world, pos, entity);
            }
        }
        return retval;
    }

    public void Magmanumexplode (Level level, BlockPos pos, Player player){
        level.explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1.5f, true, Level.ExplosionInteraction.BLOCK);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FLAME,
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    16,
                    0.05, 0.05, 0.05, 0.05);
            serverLevel.sendParticles(ParticleTypes.LAVA,
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    12,
                    0.05, 0.05, 0.05, 0.05);
        }

        final Vec3 center = new Vec3(pos.getX(), pos.getY(), pos.getZ());

        List<LivingEntity> hitEntities = level.getEntitiesOfClass
                (LivingEntity.class, new AABB(center, center)
                        .inflate((1.5)));

        for (LivingEntity entity : hitEntities) {
            entity.setSecondsOnFire(8);
        }
    }
}