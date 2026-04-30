package com.malignant.iter.common.registry;

import com.malignant.iter.common.entity.BereftEntity;
import com.malignant.iter.common.entity.DarkSorcererEntity;
import com.malignant.iter.common.entity.GhoulEntity;
import com.malignant.iter.common.entity.GiantSpiderEntity;
import com.malignant.iter.IterMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = IterMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModSpawnRestrictions {

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(
                ModEntities.BEREFT.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BereftEntity::BereftSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );

        event.register(
                ModEntities.GHOUL.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                GhoulEntity::GhoulSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );

        event.register(
                ModEntities.DARK_SORCERER.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                DarkSorcererEntity::DarkSorcererSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );

        event.register(
                ModEntities.GIANT_SPIDER.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                GiantSpiderEntity::GiantSpiderSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
    }

    public static boolean defaultMonsterCheck(LevelAccessor level, BlockPos pos){

        if (level.getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        }

        if (level.getFluidState(pos).isSource()) {
            return false;
        }

        if (level.getRawBrightness(pos, 0) > 7) {
            return false;
        }

        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);

        if (!belowState.isValidSpawn(level, belowPos, EntityType.ZOMBIE)) {
            return false;
        }

        if (!level.getBlockState(pos).isAir() && !level.getBlockState(pos).canBeReplaced()) {
            return false;
        }

        return true;
    }
}