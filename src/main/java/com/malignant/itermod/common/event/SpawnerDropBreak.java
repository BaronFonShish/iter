package com.malignant.itermod.common.event;

import com.malignant.itermod.common.registry.ModItems;
import com.malignant.itermod.iterMod;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = iterMod.MOD_ID)
public class SpawnerDropBreak {
    @SubscribeEvent
    public static void onBlockHarvestDrops(BlockEvent.BreakEvent event) {
        if (event.getState().getBlock() == Blocks.SPAWNER) {
            if (event.getPlayer() == null) return;
            if (!SpiderEggHatchEvent.isValid(event.getPlayer())) return;
            Level level = (Level) event.getLevel();

            int amount = level.random.nextInt(2) + 1;

            ItemStack fragmentStack = new ItemStack(ModItems.SPAWNER_FRAGMENT.get(), amount);

            ItemEntity itemEntity = new ItemEntity(
                    level,
                    event.getPos().getX() + 0.5,
                    event.getPos().getY() + 0.5,
                    event.getPos().getZ() + 0.5,
                    fragmentStack
            );

            level.addFreshEntity(itemEntity);
        }
    }
}
