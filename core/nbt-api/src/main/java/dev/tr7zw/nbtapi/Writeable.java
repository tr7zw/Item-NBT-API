package dev.tr7zw.nbtapi;

import java.util.UUID;

import org.bukkit.inventory.ItemStack;

public interface Writeable {

    /**
     * Merges all data from comp into this compound. This is done in one action, so
     * it also works with Tiles/Entities
     * 
     * @param comp
     */
    void mergeCompound(Readable comp);

    /**
     * Setter
     * 
     * @param key The String key.
     * @param value
     */
    void setString(String key, String value);

    /**
     * Setter
     * 
     * @param key The String key.
     * @param value
     */
    void setInteger(String key, Integer value);

    /**
     * Setter
     * 
     * @param key The String key.
     * @param value
     */
    void setDouble(String key, Double value);

    /**
     * Setter
     * 
     * @param key The String key.
     * @param value
     */
    void setByte(String key, Byte value);

    /**
     * Setter
     * 
     * @param key The String key.
     * @param value
     */
    void setShort(String key, Short value);

    /**
     * Setter
     * 
     * @param key The String key.
     * @param value
     */
    void setLong(String key, Long value);

    /**
     * Setter
     * 
     * @param key The String key.
     * @param value
     */
    void setFloat(String key, Float value);

    /**
     * Setter
     * 
     * @param key The String key.
     * @param value
     */
    void setByteArray(String key, byte[] value);

    /**
     * Setter
     * 
     * @param key The String key.
     * @param value
     */
    void setIntArray(String key, int[] value);

    /**
     * Setter
     * 
     * @param key The String key.
     * @param value
     */
    void setBoolean(String key, Boolean value);

    /**
     * Save an ItemStack as a compound under a given key
     * 
     * @param key The String key.
     * @param item
     */
    void setItemStack(String key, ItemStack item);

    /**
     * Set a UUID.
     *
     * @param key The String key.
     * @param value The UUID value.
     */
    void setUUID(String key, UUID value);

    /**
     * Removes the given key and its value.
     *
     * @param key The key to delete.
     */
    void removeKey(String key);

    /**
     * Gets an existing compound, or creates it if it
     * doesn't exist yet.
     * 
     * @param name The key of the compound.
     */
    Writeable getOrCreateCompound(String name);

    /**
     * @param name
     * @return The retrieved String List
     */
    NBTList<String> getStringList(String name);

    /**
     * Get the type of a key.
     *
     * @param name The String key.
     * @return The type of the given stored key or null
     */
    NBTType getType(String name);

}