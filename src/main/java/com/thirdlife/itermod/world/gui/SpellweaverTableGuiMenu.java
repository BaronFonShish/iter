package com.thirdlife.itermod.world.gui;

import com.thirdlife.itermod.common.registry.ModBlocks;
import com.thirdlife.itermod.common.registry.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.world.inventory.*;


public class SpellweaverTableGuiMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
    public final static HashMap<String, Object> guistate = new HashMap<>();
    public final Level world;
    public final Player entity;
    public int x, y, z;
    private ContainerLevelAccess access = ContainerLevelAccess.NULL;
    private IItemHandler internal;
    private final Map<Integer, Slot> customSlots = new HashMap<>();
    private boolean bound = false;

    public SpellweaverTableGuiMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player, extraData);
    }

    public SpellweaverTableGuiMenu(int id, Inventory inv, Player player, FriendlyByteBuf extraData) {
        super(ModMenus.SPELLWEAVER_TABLE_GUI.get(), id);
        this.entity = player;
        this.world = player.level();
        this.internal = new ItemStackHandler(8);

        BlockPos pos = null;
        if (extraData != null) {
            pos = extraData.readBlockPos();
            this.x = pos.getX();
            this.y = pos.getY();
            this.z = pos.getZ();
            access = ContainerLevelAccess.create(world, pos);
        }

        this.customSlots.put(0, this.addSlot(new SlotItemHandler(internal, 0, 25, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return true;
            }
        }));

        this.customSlots.put(1, this.addSlot(new SlotItemHandler(internal, 1, 61, 26) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return true;
            }
        }));

        this.customSlots.put(2, this.addSlot(new SlotItemHandler(internal, 2, 79, 26) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return true;
            }
        }));

        this.customSlots.put(3, this.addSlot(new SlotItemHandler(internal, 3, 97, 26) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return true;
            }
        }));

        this.customSlots.put(4, this.addSlot(new SlotItemHandler(internal, 4, 97, 44) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return true;
            }
        }));

        this.customSlots.put(5, this.addSlot(new SlotItemHandler(internal, 5, 61, 44) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return true;
            }
        }));

        this.customSlots.put(6, this.addSlot(new SlotItemHandler(internal, 6, 79, 44) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return true;
            }
        }));

        this.customSlots.put(7, this.addSlot(new SlotItemHandler(internal, 7, 133, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        }));

        for (int si = 0; si < 3; ++si) {
            for (int sj = 0; sj < 9; ++sj) {
                this.addSlot(new Slot(inv, sj + (si + 1) * 9, 8 + sj * 18, 84 + si * 18));
            }
        }

        for (int si = 0; si < 9; ++si) {
            this.addSlot(new Slot(inv, si, 8 + si * 18, 142));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        if (access != ContainerLevelAccess.NULL) {
            return stillValid(access, player, ModBlocks.SPELLWEAVER_TABLE.get());
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

            if (index < 8) {
                if (!this.moveItemStackTo(itemstack1, 8, 44, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemstack1, itemstack);
            }

            else {
                if (!this.moveItemStackTo(itemstack1, 0, 8, false)) {
                    if (index < 35) {
                        if (!this.moveItemStackTo(itemstack1, 35, 44, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index < 44) {
                        if (!this.moveItemStackTo(itemstack1, 8, 35, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

    @Override
    public void removed(Player playerIn) {
        super.removed(playerIn);

        if (!playerIn.level().isClientSide()) {
            for (int i = 0; i < 8; i++) {
                Slot slot = this.customSlots.get(i);
                if (slot != null && slot.hasItem()) {
                    playerIn.drop(slot.getItem(), false);
                }
            }
        }
    }

    @Override
    public Map<Integer, Slot> get() {
        return customSlots;
    }
}
