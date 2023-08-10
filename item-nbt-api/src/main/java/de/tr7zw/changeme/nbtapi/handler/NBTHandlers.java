package de.tr7zw.changeme.nbtapi.handler;

import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.NBTHandler;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBT;

public class NBTHandlers {

    public static final NBTHandler<ItemStack> ITEM_STACK = new NBTHandler<ItemStack>() {

        @Override
        public boolean fuzzyMatch(Object obj) {
            return obj instanceof ItemStack;
        }

        @Override
        public void set(ReadWriteNBT nbt, String key, ItemStack value) {
            nbt.removeKey(key);
            ReadWriteNBT tag = nbt.getOrCreateCompound(key);
            tag.mergeCompound(NBT.itemStackToNBT(value));
        }

        @Override
        public ItemStack get(ReadableNBT nbt, String key) {
            ReadableNBT tag = nbt.getCompound(key);
            if (tag != null) {
                return NBT.itemStackFromNBT(tag);
            }
            return null;
        }

    };

    public static final NBTHandler<ReadableNBT> STORE_READABLE_TAG = new NBTHandler<ReadableNBT>() {

        @Override
        public boolean fuzzyMatch(Object obj) {
            return obj instanceof ReadableNBT;
        }

        @Override
        public void set(ReadWriteNBT nbt, String key, ReadableNBT value) {
            nbt.removeKey(key);
            nbt.getOrCreateCompound(key).mergeCompound(value);
        }

        @Override
        public ReadableNBT get(ReadableNBT nbt, String key) {
            ReadableNBT tag = nbt.getCompound(key);
            if (tag != null) {
                ReadWriteNBT value = NBT.createNBTObject();
                value.mergeCompound(tag);
                return value;
            }
            return null;
        }

    };

    public static final NBTHandler<ReadWriteNBT> STORE_READWRITE_TAG = new NBTHandler<ReadWriteNBT>() {

        @Override
        public boolean fuzzyMatch(Object obj) {
            return obj instanceof ReadWriteNBT;
        }

        @Override
        public void set(ReadWriteNBT nbt, String key, ReadWriteNBT value) {
            nbt.removeKey(key);
            nbt.getOrCreateCompound(key).mergeCompound(value);
        }

        @Override
        public ReadWriteNBT get(ReadableNBT nbt, String key) {
            ReadableNBT tag = nbt.getCompound(key);
            if (tag != null) {
                ReadWriteNBT value = NBT.createNBTObject();
                value.mergeCompound(tag);
                return value;
            }
            return null;
        }

    };

}
