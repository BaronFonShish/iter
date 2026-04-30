package com.malignant.iter.common.event;

import com.malignant.iter.common.item.magic.defaults.SpellItem;
import com.malignant.iter.common.variables.IterPlayerDataUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = "iter")
public class SpelldropEvent {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event.getEntity() == null || event.getSource().getEntity() == null) return;

        if (event.getSource().getEntity() instanceof Player player) {
            Level level = event.getEntity().level();
            LivingEntity victim = event.getEntity();

            if (level.isClientSide()) return;

            boolean drop = false;
            float luck = IterPlayerDataUtils.getSpellLuck(player);
            if (luck >= Math.random() * 1000 + 100) {
                drop = true;
            }

            if (drop) {
                spelldrop(victim, (ServerLevel) level);
            } else {
                float pointstoadd = (victim.getMaxHealth() + 5);
                pointstoadd /= 100;
                IterPlayerDataUtils.addSpellLuck(player, pointstoadd);
            }
        }
    }

    public static void spelldrop(Entity target, ServerLevel level) {
        ResourceKey<LootTable> lootpath = ResourceKey.create(
                net.minecraft.core.registries.Registries.LOOT_TABLE,
                ResourceLocation.parse("iter:gameplay/spelldrop_novice")
        );

        if (Math.random() < 0.3d) {
            lootpath = ResourceKey.create(
                    net.minecraft.core.registries.Registries.LOOT_TABLE,
                    ResourceLocation.parse("iter:gameplay/spelldrop_adept")
            );
        }
        if (Math.random() < 0.05d) {
            lootpath = ResourceKey.create(
                    net.minecraft.core.registries.Registries.LOOT_TABLE,
                    ResourceLocation.parse("iter:gameplay/spelldrop_expert")
            );
        }

        BlockPos lootpos = BlockPos.containing(
                target.getX() + 0.5,
                target.getY() + target.getEyeHeight() / 2,
                target.getZ() + 0.5
        );

        LootTable lootTable = level.getServer().reloadableRegistries().getLootTable(lootpath);

        LootParams.Builder paramsBuilder = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(lootpos))
                .withOptionalParameter(LootContextParams.BLOCK_ENTITY, level.getBlockEntity(lootpos));

        LootParams params = paramsBuilder.create(LootContextParamSets.EMPTY);

        for (ItemStack itemstackiterator : lootTable.getRandomItems(params)) {
            if (itemstackiterator.getItem() instanceof SpellItem spell) {
                if (Math.random() > 0.75f) {
                    spell.setQuality(itemstackiterator, 1);
                } else {
                    spell.setQuality(itemstackiterator, 0);
                }
            }
            ItemEntity entityToSpawn = new ItemEntity(level, lootpos.getX(), lootpos.getY(), lootpos.getZ(), itemstackiterator);
            entityToSpawn.setPickUpDelay(10);
            level.addFreshEntity(entityToSpawn);
        }
    }
}