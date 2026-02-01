package com.malignant.itermod.common.item.magic.defaults;

import com.malignant.itermod.common.variables.SpellbookCapability;
import com.malignant.itermod.world.gui.SpellBookGuiMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class SpellBook extends Item {

    public SpellBook() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        if (entity instanceof ServerPlayer serverPlayer) {
            NetworkHooks.openScreen(serverPlayer, new MenuProvider() {
                @Override
                public @NotNull Component getDisplayName() {
                    return Component.literal("Spell Book");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    FriendlyByteBuf packetBuffer = new FriendlyByteBuf(Unpooled.buffer());
                    packetBuffer.writeBlockPos(entity.blockPosition());
                    packetBuffer.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);
                    return new SpellBookGuiMenu(id, inventory, packetBuffer);
                }
                public boolean stillValid(Player player) {
                    return true;
                }
            }, buf -> {
                buf.writeBlockPos(entity.blockPosition());
                buf.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);
            });
        }
        return ar;
    }


    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        stack.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> nbt.put("Inventory", ((ItemStackHandler) capability).serializeNBT()));
        return nbt;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag compound) {
        return new SpellbookCapability();
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        super.readShareTag(stack, nbt);
        if (nbt != null)
            stack.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> ((ItemStackHandler) capability).deserializeNBT((CompoundTag) nbt.get("Inventory")));
    }
}