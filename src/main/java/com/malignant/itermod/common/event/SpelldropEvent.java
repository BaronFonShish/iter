package com.malignant.itermod.common.event;

import com.malignant.itermod.common.item.magic.defaults.SpellItem;
import com.malignant.itermod.common.variables.IterPlayerDataUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
            if (event.getSource().getEntity() instanceof Player player){
                Level level = event.getEntity().level();
                LivingEntity victim = event.getEntity();
                boolean drop = false;
                float luck = IterPlayerDataUtils.getSpellLuck(player);
                if (luck >= Math.random()*1000 + 100){
                    drop = true;
                }
                if (drop){spelldrop(victim, level);}
                else {
                    float pointstoadd = (victim.getMaxHealth() + 5);
                    pointstoadd /= 100;
                    IterPlayerDataUtils.addSpellLuck(player, pointstoadd);
                }
            }
        }
    }

    public static void spelldrop(Entity target, Level level){
        ResourceLocation lootpath = new ResourceLocation("iter:gameplay/spelldrop_novice");
        if (Math.random()<0.3d){lootpath = new ResourceLocation("iter:gameplay/spelldrop_adept");}
        if (Math.random()<0.05d){lootpath = new ResourceLocation("iter:gameplay/spelldrop_expert");}

        BlockPos lootpos = BlockPos.containing(target.getX()+0.5, (target.getY() + target.getEyeHeight()/2), target.getZ()+0.5);
        for (ItemStack itemstackiterator : level.getServer().getLootData().getLootTable(lootpath)
                .getRandomItems(new LootParams.Builder((ServerLevel) level).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(lootpos)).withParameter(LootContextParams.BLOCK_STATE, level.getBlockState(lootpos))
                        .withOptionalParameter(LootContextParams.BLOCK_ENTITY, level.getBlockEntity(lootpos)).create(LootContextParamSets.EMPTY))) {
            if (itemstackiterator.getItem() instanceof SpellItem spell){
                if (Math.random()>0.75f){
                    spell.setQuality(itemstackiterator, 1);
                }else spell.setQuality(itemstackiterator, 0);
            }
            ItemEntity entityToSpawn = new ItemEntity(level, lootpos.getX(), lootpos.getY(), lootpos.getZ(), itemstackiterator);
            entityToSpawn.setPickUpDelay(10);
            level.addFreshEntity(entityToSpawn);
        }
    }
}
