package com.thirdlife.itermod.common.event;

import com.thirdlife.itermod.common.procedures.ExpDropProcedure;
import com.thirdlife.itermod.common.registry.ModItems;
import com.thirdlife.itermod.iterMod;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = iterMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NostelonPolishedEvents {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        ItemStack held = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (held.getItem() == ModItems.ROUGH_NOSTELON.get()) {
            // Проверяем, что кликнули по точилу
            if (event.getLevel().getBlockState(event.getPos()).is(Blocks.GRINDSTONE)) {
                if (!event.getLevel().isClientSide()) {
                    held.shrink(1);

                    ItemStack normal = new ItemStack(ModItems.NOSTELON.get());
                    if (!player.getInventory().add(normal)) {
                        player.drop(normal, false);
                    }

                    event.getLevel().playSound(
                            null,
                            event.getPos(),
                            SoundEvents.GRINDSTONE_USE,
                            SoundSource.BLOCKS,
                            1.0f,
                            1.0f
                    );
                }

                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(event.getLevel().isClientSide()));
            }
        }
    }

}
