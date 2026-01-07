package com.thirdlife.itermod.common.item;

import com.thirdlife.itermod.common.entity.misc.HellblazeArrowEntity;
import com.thirdlife.itermod.common.registry.ModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class HellblazeArrowItem extends ArrowItem {
    public HellblazeArrowItem() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON));
    }

    @Override
    public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
        return 0f;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("iter.desc.hellblaze_arrow"));
    }

    public AbstractArrow createArrow(Level level, ItemStack itemstack, LivingEntity sourceEntity) {
        return new HellblazeArrowEntity(ModEntities.HELLBLAZE_ARROW.get(), sourceEntity, level);
    }
}

