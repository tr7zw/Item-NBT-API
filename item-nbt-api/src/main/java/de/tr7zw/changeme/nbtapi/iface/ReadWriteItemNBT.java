package de.tr7zw.changeme.nbtapi.iface;

import java.util.function.BiConsumer;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.changeme.nbtapi.NBTItem;

public interface ReadWriteItemNBT extends ReadWriteNBT, ReadableItemNBT {

    /**
     * True, if the item has any tags now known for this item type.
     * 
     * @return true when custom tags are present
     */
    public boolean hasCustomNbtData();

    /**
     * Remove all custom (non-vanilla) NBT tags from the NBTItem.
     */
    public void clearCustomNBT();

    /**
     * Gives save access to the {@link ItemMeta} of the internal {@link ItemStack}.
     * Supported operations while inside this scope: - any get/set method of
     * {@link ItemMeta} - any getter on {@link NBTItem}
     * 
     * All changes made to the {@link NBTItem} during this scope will be reverted at
     * the end.
     * 
     * @param handler
     */
    public void modifyMeta(BiConsumer<ReadableNBT, ItemMeta> handler);

    /**
     * Gives save access to the {@link ItemMeta} of the internal {@link ItemStack}.
     * Supported operations while inside this scope: - any get/set method of
     * {@link ItemMeta} - any getter on {@link NBTItem}
     * 
     * All changes made to the {@link NBTItem} during this scope will be reverted at
     * the end.
     * 
     * @param handler
     */
    public <T extends ItemMeta> void modifyMeta(Class<T> type, BiConsumer<ReadableNBT, T> handler);

}
