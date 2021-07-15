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
     * @param key
     * @param value
     */
    void setString(String key, String value);

    /**
     * Setter
     * 
     * @param key
     * @param value
     */
    void setInteger(String key, Integer value);

    /**
     * Setter
     * 
     * @param key
     * @param value
     */
    void setDouble(String key, Double value);

    /**
     * Setter
     * 
     * @param key
     * @param value
     */
    void setByte(String key, Byte value);

    /**
     * Setter
     * 
     * @param key
     * @param value
     */
    void setShort(String key, Short value);

    /**
     * Setter
     * 
     * @param key
     * @param value
     */
    void setLong(String key, Long value);

    /**
     * Setter
     * 
     * @param key
     * @param value
     */
    void setFloat(String key, Float value);

    /**
     * Setter
     * 
     * @param key
     * @param value
     */
    void setByteArray(String key, byte[] value);

    /**
     * Setter
     * 
     * @param key
     * @param value
     */
    void setIntArray(String key, int[] value);

    /**
     * Setter
     * 
     * @param key
     * @param value
     */
    void setBoolean(String key, Boolean value);

    /**
     * Save an ItemStack as a compound under a given key
     * 
     * @param key
     * @param item
     */
    void setItemStack(String key, ItemStack item);

    /**
     * Setter
     *
     * @param key
     * @param value
     */
    void setUUID(String key, UUID value);

    /**
     * @param key Deletes the given Key
     */
    void removeKey(String key);

    /**
     * The same as addCompound, just with a name that better reflects what it does
     * 
     * @param name
     * @return
     */
    Writeable getOrCreateCompound(String name);

    /**
     * @param name
     * @return The retrieved String List
     */
    NBTList<String> getStringList(String name);

    /**
     * @param name
     * @return The type of the given stored key or null
     */
    NBTType getType(String name);

}