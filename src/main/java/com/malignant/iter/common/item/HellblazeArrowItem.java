package com.malignant.iter.common.item;

import com.malignant.iter.common.entity.misc.HellblazeArrowEntity;
import com.malignant.iter.common.registry.ModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
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
    public void appendHoverText(ItemStack itemstack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        list.add(Component.translatable("iter.desc.hellblaze_arrow"));
    }

    public Projectile asProjectile(Level level, LivingEntity shooter, ItemStack stack, boolean isCrit) {
        HellblazeArrowEntity arrow = new HellblazeArrowEntity(ModEntities.HELLBLAZE_ARROW.get(), shooter, level);
        return arrow;
    }
}