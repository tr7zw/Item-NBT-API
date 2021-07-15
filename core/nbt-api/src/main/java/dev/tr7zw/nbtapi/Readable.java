package dev.tr7zw.nbtapi;

import java.util.Set;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;

public interface Readable {

    /**
     * Getter
     * 
     * @param key
     * @return The stored value or NMS fallback
     */
    String getString(String key);

    /**
     * Getter
     * 
     * @param key
     * @return The stored value or NMS fallback
     */
    Integer getInteger(String key);

    /**
     * Getter
     * 
     * @param key
     * @return The stored value or NMS fallback
     */
    Double getDouble(String key);

    /**
     * Getter
     * 
     * @param key
     * @return The stored value or NMS fallback
     */
    Byte getByte(String key);

    /**
     * Getter
     * 
     * @param key
     * @return The stored value or NMS fallback
     */
    Short getShort(String key);

    /**
     * Getter
     * 
     * @param key
     * @return The stored value or NMS fallback
     */
    Long getLong(String key);

    /**
     * Getter
     * 
     * @param key
     * @return The stored value or NMS fallback
     */
    Float getFloat(String key);

    /**
     * Getter
     * 
     * @param key
     * @return The stored value or NMS fallback
     */
    byte[] getByteArray(String key);

    /**
     * Getter
     * 
     * @param key
     * @return The stored value or NMS fallback
     */
    int[] getIntArray(String key);

    /**
     * Getter
     * 
     * @param key
     * @return The stored value or NMS fallback
     */
    Boolean getBoolean(String key);

    /**
     * Get an ItemStack that was saved at the given key
     * 
     * @param key
     * @return
     */
    ItemStack getItemStack(String key);

    /**
     * Getter
     *
     * @param key
     * @return The stored value or NMS fallback
     */
    UUID getUUID(String key);

    /**
     * @return Set of all stored Keys
     */
    Set<String> getKeys();

    /**
     * @param name
     * @return The Compound instance or null
     */
    Readable getCompound(String name);

    /**
     * @param name
     * @return The retrieved String List
     */
    NBTList<String> getStringList(String name);

    /**
     * @param name
     * @return The retrieved Integer List
     */
    NBTList<Integer> getIntegerList(String name);

    /**
     * @param name
     * @return The retrieved Float List
     */
    NBTList<Float> getFloatList(String name);

    /**
     * @param name
     * @return The retrieved Double List
     */
    NBTList<Double> getDoubleList(String name);

    /**
     * @param name
     * @return The retrieved Long List
     */
    NBTList<Long> getLongList(String name);

    /**
     * Returns the type of the list, null if not a list
     * 
     * @param name
     * @return
     */
    NBTType getListType(String name);

    /**
     * @param name
     * @return The retrieved Compound List
     */
    //NBTCompoundList getCompoundList(String name); //FIXME

    /**
     * @param name
     * @return The type of the given stored key or null
     */
    NBTType getType(String name);
    
    /**
     * @param name
     * @return True when this key is avaliable
     */
    boolean hasKey(String name);

}