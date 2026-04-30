package com.malignant.iter.world.gui;

import com.malignant.iter.common.event.SpellBookUtils;
import com.malignant.iter.common.registry.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SpellBookGuiMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {

    public final static HashMap<String, Object> guistate = new HashMap<>();
    public final Level world;
    public final Player entity;
    public int x, y, z;
    private ContainerLevelAccess access = ContainerLevelAccess.NULL;
    private IItemHandler internal;
    private final Map<Integer, Slot> customSlots = new HashMap<>();
    private boolean bound = false;
    private Supplier<Boolean> boundItemMatcher = null;
    private Entity boundEntity = null;
    private BlockEntity boundBlockEntity = null;

    public SpellBookGuiMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        super(ModMenus.SPELLBOOK_GUI.get(), id);
        this.entity = inv.player;
        this.world = inv.player.level();
        this.internal = new ItemStackHandler(57);
        BlockPos pos = null;
        if (extraData != null) {
            pos = extraData.readBlockPos();
            this.x = pos.getX();
            this.y = pos.getY();
            this.z = pos.getZ();
            access = ContainerLevelAccess.create(world, pos);
        }

        if (pos != null) {
            if (extraData.readableBytes() == 1) { // bound to item
                byte hand = extraData.readByte();
                ItemStack itemstack = hand == 0 ? this.entity.getMainHandItem() : this.entity.getOffhandItem();
                this.boundItemMatcher = () -> itemstack == (hand == 0 ? this.entity.getMainHandItem() : this.entity.getOffhandItem());
                IItemHandler capability = itemstack.getCapability(Capabilities.ItemHandler.ITEM);
                if (capability != null) {
                    this.internal = capability;
                    this.bound = true;
                }
            } else if (extraData.readableBytes() > 1) {
                extraData.readByte();
                boundEntity = world.getEntity(extraData.readVarInt());
                if (boundEntity != null) {
                    EntityCapability<IItemHandler, Void> capabilityType = Capabilities.ItemHandler.ENTITY;
                    IItemHandler capability = boundEntity.getCapability(capabilityType);
                    if (capability != null) {
                        this.internal = capability;
                        this.bound = true;
                    }
                }
            } else {
                boundBlockEntity = this.world.getBlockEntity(pos);
                if (boundBlockEntity != null) {
                    BlockCapability<IItemHandler, @Nullable Direction> capabilityType = Capabilities.ItemHandler.BLOCK;
                    var blockState = this.world.getBlockState(pos);
                    IItemHandler capability = this.world.getCapability(capabilityType, pos, blockState, boundBlockEntity, null);
                    if (capability != null) {
                        this.internal = capability;
                        this.bound = true;
                    }
                }
            }
        }

        int[] bottomX = {38, 66, 94, 122, 169, 197, 225, 253};
        int[] bottomY = {131, 114, 127, 111, 111, 127, 114, 131};

        for (int i = 0; i < 8; i++) {
            this.customSlots.put(i + 1, createSlot(internal, i + 1, bottomX[i], bottomY[i]));
        }

        int xStart = 34;
        int yStart = 30;
        int[] yPattern = {0, 0, -1, -2, -3, -4, -4, -3, -2, -1, 0, 0};

        int slotid = 9;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                this.customSlots.put(slotid, createSlot(internal, slotid, xStart, (yStart + yPattern[j])));
                slotid++;
                if (j == 5) {
                    xStart += 44;
                } else xStart += 18;
            }
            yStart += 18;
            xStart = 34;
        }

        for (int si = 0; si < 3; ++si)
            for (int sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(inv, sj + (si + 1) * 9, 67 + 8 + sj * 18, 85 + 84 + si * 18));
        for (int si = 0; si < 9; ++si)
            this.addSlot(new Slot(inv, si, 67 + 8 + si * 18, 85 + 142));
    }

    private Slot createSlot(IItemHandler internal, int id, int x, int y) {
        Slot slot = new SlotItemHandler(internal, id, x, y) {
            private final int slotid = id;

            @Override
            public boolean mayPlace(ItemStack itemstack) {
                return SpellBookUtils.isSpellItem(itemstack);
            }
        };
        this.addSlot(slot);
        return slot;
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.bound) {
            if (this.boundItemMatcher != null)
                return this.boundItemMatcher.get();
            else if (this.boundBlockEntity != null)
                return AbstractContainerMenu.stillValid(this.access, player, this.boundBlockEntity.getBlockState().getBlock());
            else if (this.boundEntity != null)
                return this.boundEntity.isAlive();
        }
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < 54) {
                if (!this.moveItemStackTo(itemstack1, 54, this.slots.size(), true))
                    return ItemStack.EMPTY;
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (!this.moveItemStackTo(itemstack1, 0, 54, false)) {
                if (index < 54 + 27) {
                    if (!this.moveItemStackTo(itemstack1, 54 + 27, this.slots.size(), true))
                        return ItemStack.EMPTY;
                } else {
                    if (!this.moveItemStackTo(itemstack1, 54, 54 + 27, false))
                        return ItemStack.EMPTY;
                }
                return ItemStack.EMPTY;
            }
            if (itemstack1.getCount() == 0)
                slot.set(ItemStack.EMPTY);
            else
                slot.setChanged();
            if (itemstack1.getCount() == itemstack.getCount())
                return ItemStack.EMPTY;
            slot.onTake(playerIn, itemstack1);
        }
        return itemstack;
    }

    @Override
    protected boolean moveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        boolean flag = false;
        int i = startIndex;
        if (reverseDirection) {
            i = endIndex - 1;
        }
        if (stack.isStackable()) {
            while (!stack.isEmpty()) {
                if (reverseDirection) {
                    if (i < startIndex) {
                        break;
                    }
                } else if (i >= endIndex) {
                    break;
                }
                Slot slot = this.slots.get(i);
                ItemStack itemstack = slot.getItem();
                if (slot.mayPlace(itemstack) && !itemstack.isEmpty() && ItemStack.isSameItemSameComponents(stack, itemstack)) {
                    int j = itemstack.getCount() + stack.getCount();
                    int maxSize = Math.min(slot.getMaxStackSize(), stack.getMaxStackSize());
                    if (j <= maxSize) {
                        stack.setCount(0);
                        itemstack.setCount(j);
                        slot.set(itemstack);
                        flag = true;
                    } else if (itemstack.getCount() < maxSize) {
                        stack.shrink(maxSize - itemstack.getCount());
                        itemstack.setCount(maxSize);
                        slot.set(itemstack);
                        flag = true;
                    }
                }
                if (reverseDirection) {
                    --i;
                } else {
                    ++i;
                }
            }
        }
        if (!stack.isEmpty()) {
            if (reverseDirection) {
                i = endIndex - 1;
            } else {
                i = startIndex;
            }
            while (true) {
                if (reverseDirection) {
                    if (i < startIndex) {
                        break;
                    }
                } else if (i >= endIndex) {
                    break;
                }
                Slot slot1 = this.slots.get(i);
                ItemStack itemstack1 = slot1.getItem();
                if (itemstack1.isEmpty() && slot1.mayPlace(stack)) {
                    if (stack.getCount() > slot1.getMaxStackSize()) {
                        slot1.setByPlayer(stack.split(slot1.getMaxStackSize()));
                    } else {
                        slot1.setByPlayer(stack.split(stack.getCount()));
                    }
                    slot1.setChanged();
                    flag = true;
                    break;
                }
                if (reverseDirection) {
                    --i;
                } else {
                    ++i;
                }
            }
        }
        return flag;
    }

    @Override
    public void removed(Player playerIn) {
        super.removed(playerIn);
        if (!bound && playerIn instanceof ServerPlayer serverPlayer) {
            if (!serverPlayer.isAlive() || serverPlayer.hasDisconnected()) {
                for (int j = 0; j < internal.getSlots(); ++j) {
                    playerIn.drop(internal.extractItem(j, internal.getStackInSlot(j).getCount(), false), false);
                }
            } else {
                for (int i = 0; i < internal.getSlots(); ++i) {
                    playerIn.getInventory().placeItemBackInInventory(internal.extractItem(i, internal.getStackInSlot(i).getCount(), false));
                }
            }
        }
    }

    public Map<Integer, Slot> get() {
        return customSlots;
    }
}