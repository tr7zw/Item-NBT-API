package dev.tr7zw.nbtapi;

import java.util.UUID;

public interface Writeable<W extends Writeable<W>> {

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
    W setString(String key, String value);

    /**
     * Setter
     * 
     * @param key The String key.
     * @param value
     */
    W setInteger(String key, Integer value);

    /**
     * Setter
     * 
     * @param key The String key.
     * @param value
     */
    W setDouble(String key, Double value);

    /**
     * Setter
     * 
     * @param key The String key.
     * @param value
     */
    W setByte(String key, Byte value);

    /**
     * Setter
     * 
     * @param key The String key.
     * @param value
     */
    W setShort(String key, Short value);

    /**
     * Setter
     * 
     * @param key The String key.
     * @param value
     */
    W setLong(String key, Long value);

    /**
     * Setter
     * 
     * @param key The String key.
     * @param value
     */
    W setFloat(String key, Float value);

    /**
     * Setter
     * 
     * @param key The String key.
     * @param value
     */
    W setByteArray(String key, byte[] value);

    /**
     * Setter
     * 
     * @param key The String key.
     * @param value
     */
    W setIntArray(String key, int[] value);

    /**
     * Setter
     * 
     * @param key The String key.
     * @param value
     */
    W setBoolean(String key, Boolean value);

    /**
     * Set a UUID.
     *
     * @param key The String key.
     * @param value The UUID value.
     */
    W setUUID(String key, UUID value);

    /**
     * Removes the given key and its value.
     *
     * @param key The key to delete.
     */
    W removeKey(String key);

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