package de.tr7zw.changeme.nbtapi;

import java.util.function.Consumer;
import java.util.function.Function;

import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBT;

/**
 * General utility class for a clean and simple nbt access.
 * 
 * @author tr7zw
 *
 */
public class NBT {

    private NBT() {
        // No instances of NBT. Utility class
    }

    public static <T> T get(ItemStack item, Function<ReadableNBT, T> getter) {
        return getter.apply(new NBTItem(item));
    }

    public static <T> T get(Entity entity, Function<ReadableNBT, T> getter) {
        return getter.apply(new NBTEntity(entity));
    }

    public static <T> T get(BlockState blockState, Function<ReadableNBT, T> getter) {
        return getter.apply(new NBTTileEntity(blockState));
    }

    public static <T> T getPersistentData(Entity entity, Function<ReadableNBT, T> getter) {
        return getter.apply(new NBTEntity(entity).getPersistentDataContainer());
    }

    public static <T> T getPersistentData(BlockState blockState, Function<ReadableNBT, T> getter) {
        return getter.apply(new NBTTileEntity(blockState).getPersistentDataContainer());
    }

    public static <T> T modify(ItemStack item, Function<ReadWriteNBT, T> function) {
        NBTItem nbti = new NBTItem(item, true);
        T val = function.apply(nbti);
        nbti.applyNBT(item);
        return val;
    }

    public static void modify(ItemStack item, Consumer<ReadWriteNBT> consumer) {
        NBTItem nbti = new NBTItem(item, true);
        consumer.accept(nbti);
        nbti.applyNBT(item);
    }

    public static <T> T modify(Entity entity, Function<ReadWriteNBT, T> function) {
        return function.apply(new NBTEntity(entity));
    }

    public static void modify(Entity entity, Consumer<ReadWriteNBT> consumer) {
        consumer.accept(new NBTEntity(entity));
    }

    public static <T> T modifyPersistentData(Entity entity, Function<ReadWriteNBT, T> function) {
        return function.apply(new NBTEntity(entity).getPersistentDataContainer());
    }

    public static void modifyPersistentData(Entity entity, Consumer<ReadWriteNBT> consumer) {
        consumer.accept(new NBTEntity(entity).getPersistentDataContainer());
    }

    public static <T> T modify(BlockState blockState, Function<ReadWriteNBT, T> function) {
        return function.apply(new NBTTileEntity(blockState));
    }

    public static void modify(BlockState blockState, Consumer<ReadWriteNBT> consumer) {
        consumer.accept(new NBTTileEntity(blockState));
    }

    public static <T> T modifyPersistentData(BlockState blockState, Function<ReadWriteNBT, T> function) {
        return function.apply(new NBTTileEntity(blockState).getPersistentDataContainer());
    }

    public static void modifyPersistentData(BlockState blockState, Consumer<ReadWriteNBT> consumer) {
        consumer.accept(new NBTTileEntity(blockState).getPersistentDataContainer());
    }

}
