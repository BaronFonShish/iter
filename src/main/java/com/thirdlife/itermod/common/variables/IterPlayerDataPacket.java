package com.thirdlife.itermod.common.variables;

import com.thirdlife.itermod.common.registry.ModCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class IterPlayerDataPacket {
    public enum DataType {
        BURNOUT,
        SPELL_SLOT,
        SPELL_BOOK,
        SPELL_LUCK,
        SPELLWEAVER_SWITCH,
        FULL_SYNC
    }

    private final DataType type;
    private final CompoundTag data;

    IterPlayerDataPacket(DataType type, CompoundTag data) {
        this.type = type;
        this.data = data;
    }

    public static void encode(IterPlayerDataPacket msg, FriendlyByteBuf buf) {
        buf.writeEnum(msg.type);
        buf.writeNbt(msg.data);
    }

    public static IterPlayerDataPacket decode(FriendlyByteBuf buf) {
        DataType type = buf.readEnum(DataType.class);
        CompoundTag data = buf.readNbt();
        return new IterPlayerDataPacket(type, data);
    }

    public static IterPlayerDataPacket burnout(float etherBurnout) {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("value", etherBurnout);
        return new IterPlayerDataPacket(DataType.BURNOUT, tag);
    }

    public static IterPlayerDataPacket spellSlot(int spellSlot) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("value", spellSlot);
        return new IterPlayerDataPacket(DataType.SPELL_SLOT, tag);
    }

    public static IterPlayerDataPacket spellLuck(float spellLuck) {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("value", spellLuck);
        return new IterPlayerDataPacket(DataType.SPELL_LUCK, tag);
    }

    public static IterPlayerDataPacket spellBook(ItemStack spellBook) {
        CompoundTag tag = new CompoundTag();
        if (!spellBook.isEmpty()) {
            CompoundTag bookTag = new CompoundTag();
            spellBook.save(bookTag);
            tag.put("value", bookTag);
        }
        return new IterPlayerDataPacket(DataType.SPELL_BOOK, tag);
    }

    public static IterPlayerDataPacket spellweaverSwitch(boolean switchState) {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("value", switchState);
        return new IterPlayerDataPacket(DataType.SPELLWEAVER_SWITCH, tag);
    }

    public static IterPlayerDataPacket fullSync(float etherBurnout, int spellSlot,
                                                float spellLuck, ItemStack spellBook,
                                                boolean spellweaverSwitch) {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("burnout", etherBurnout);
        tag.putInt("slot", spellSlot);
        tag.putFloat("luck", spellLuck);
        tag.putBoolean("switch", spellweaverSwitch);
        if (!spellBook.isEmpty()) {
            CompoundTag bookTag = new CompoundTag();
            spellBook.save(bookTag);
            tag.put("book", bookTag);
        }
        return new IterPlayerDataPacket(DataType.FULL_SYNC, tag);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Player player = context.get().getDirection().getReceptionSide().isClient()
                    ? Minecraft.getInstance().player
                    : context.get().getSender();

            if (player != null) {
                player.getCapability(ModCapabilities.ITER_PLAYER_DATA).ifPresent(data -> {
                    switch (type) {
                        case BURNOUT:
                            data.setEtherBurnout(this.data.getFloat("value"));
                            break;

                        case SPELL_SLOT:
                            data.setSelectedSpellSlot(this.data.getInt("value"));
                            break;

                        case SPELLWEAVER_SWITCH:
                            data.setSpellweaverSwitch(this.data.getBoolean("value"));
                            break;

                        case SPELL_BOOK:
                            if (this.data.contains("value")) {
                                data.setSelectedSpellBook(ItemStack.of(this.data.getCompound("value")));
                            } else {
                                data.setSelectedSpellBook(ItemStack.EMPTY);
                            }
                            break;

                        case FULL_SYNC:
                            data.setEtherBurnout(this.data.getFloat("burnout"));
                            data.setSelectedSpellSlot(this.data.getInt("slot"));
                            data.setSpellweaverSwitch(this.data.getBoolean("switch")); // Add this
                            if (this.data.contains("book")) {
                                data.setSelectedSpellBook(ItemStack.of(this.data.getCompound("book")));
                            } else {
                                data.setSelectedSpellBook(ItemStack.EMPTY);
                            }
                            break;
                    }
                });
            }
        });
        context.get().setPacketHandled(true);
    }
}