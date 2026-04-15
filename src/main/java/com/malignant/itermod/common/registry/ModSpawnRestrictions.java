package com.malignant.itermod.common.registry;

import com.malignant.itermod.common.entity.BereftEntity;
import com.malignant.itermod.common.entity.DarkSorcererEntity;
import com.malignant.itermod.common.entity.GhoulEntity;
import com.malignant.itermod.common.entity.GiantSpiderEntity;
import com.malignant.itermod.iterMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = iterMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSpawnRestrictions {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            registerSpawnConditions();
        });
    }

    private static void registerSpawnConditions() {
        SpawnPlacements.register(
                ModEntities.BEREFT.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BereftEntity::BereftSpawnRules
        );

        SpawnPlacements.register(
                ModEntities.GHOUL.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                GhoulEntity::GhoulSpawnRules
        );

        SpawnPlacements.register(
                ModEntities.DARK_SORCERER.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                DarkSorcererEntity::DarkSorcererSpawnRules
        );

        SpawnPlacements.register(
                ModEntities.GIANT_SPIDER.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                GiantSpiderEntity::GiantSpiderSpawnRules
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
