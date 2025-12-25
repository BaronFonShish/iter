package com.thirdlife.itermod.common.event;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber

public class SpelldropEvent {
    @SubscribeEvent

    public static void onEntityDeath(LivingDeathEvent event) {
        if (event != null && event.getEntity() != null && event.getSource().getEntity() != null) {
            if (event.getSource().getEntity() instanceof Player){
                Level level = event.getEntity().level();

                if (Math.random()<0.025d){
                    ResourceLocation lootpath = new ResourceLocation("iter:gameplay/spelldrop_novice");
                    if (Math.random()<0.25d){lootpath = new ResourceLocation("iter:gameplay/spelldrop_adept");}

                BlockPos lootpos = BlockPos.containing(event.getEntity().getX()+0.5, (event.getEntity().getY() + event.getEntity().getEyeHeight()/2), event.getEntity().getZ()+0.5);
                for (ItemStack itemstackiterator : level.getServer().getLootData().getLootTable(lootpath)
                        .getRandomItems(new LootParams.Builder((ServerLevel) level).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(lootpos)).withParameter(LootContextParams.BLOCK_STATE, level.getBlockState(lootpos))
                                .withOptionalParameter(LootContextParams.BLOCK_ENTITY, level.getBlockEntity(lootpos)).create(LootContextParamSets.EMPTY))) {
                    ItemEntity entityToSpawn = new ItemEntity(level, lootpos.getX(), lootpos.getY(), lootpos.getZ(), itemstackiterator);
                    entityToSpawn.setPickUpDelay(10);
                    level.addFreshEntity(entityToSpawn);
                   }
                }
            }
        }
    }
}
