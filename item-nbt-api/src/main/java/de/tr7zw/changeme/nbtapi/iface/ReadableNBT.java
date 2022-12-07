package de.tr7zw.changeme.nbtapi.iface;

import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBTType;

/**
 * This interface only exposes methods that get data without any changes to the
 * underlying object.
 * 
 * @author tr7zw
 *
 */
public interface ReadableNBT {

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
     * Get an {@link ItemStack} array that was saved at the given key, or null if no
     * stored data was found
     * 
     * @param key key
     * @return The stored {@link ItemStack} array, or null if stored data wasn't
     *         found
     */
    ItemStack[] getItemStackArray(String key);

    /**
     * Getter
     *
     * @param key
     * @return The stored value or NMS fallback
     */
    UUID getUUID(String key);

    /**
     * @param key String key
     * @return true, if the key is set
     */
    boolean hasTag(String key);

    /**
     * @return Set of all stored Keys
     */
    Set<String> getKeys();

    /**
     * @param name
     * @return The Compound instance or null
     */
    ReadableNBT getCompound(String name);

    /**
     * @param name
     * @return The retrieved String List
     */
    ReadableNBTList<String> getStringList(String name);

    /**
     * @param name
     * @return The retrieved Integer List
     */
    ReadableNBTList<Integer> getIntegerList(String name);

    /**
     * @param name
     * @return The retrieved Integer List
     */
    ReadableNBTList<int[]> getIntArrayList(String name);

    /**
     * @param name
     * @return The retrieved Integer List
     */
    ReadableNBTList<UUID> getUUIDList(String name);

    /**
     * @param name
     * @return The retrieved Float List
     */
    ReadableNBTList<Float> getFloatList(String name);

    /**
     * @param name
     * @return The retrieved Double List
     */
    ReadableNBTList<Double> getDoubleList(String name);

    /**
     * @param name
     * @return The retrieved Long List
     */
    ReadableNBTList<Long> getLongList(String name);

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
    ReadableNBTList<ReadWriteNBT> getCompoundList(String name);

    /**
     * Returns the stored value if exists, or provided value otherwise.
     * <p>
     * Supported types:
     * {@code byte/Byte, short/Short, int/Integer, long/Long, float/Float, double/Double, byte[], int[]},
     * {@link String}, {@link UUID}
     *
     * @param key          key
     * @param defaultValue default non-null value
     * @param <T>          value type
     * @return Stored or provided value
     */
    <T> T getOrDefault(String key, T defaultValue);

    /**
     * Returns the stored value if exists, or null.
     * <p>
     * Supported types:
     * {@code Byte, Short, Integer, Long, Float, Double, byte[], int[]},
     * {@link String}, {@link UUID}
     *
     * @param key  key
     * @param type data type
     * @param <T>  value type
     * @return Stored or provided value
     */
    <T> T getOrNull(String key, Class<?> type);

    /**
     * Get an Enum value that has been set via setEnum or setString(key,
     * value.name()). Passing null/invalid keys will return null.
     * 
     * @param <E>
     * @param key
     * @param type
     * @return
     */
    <E extends Enum<E>> E getEnum(String key, Class<E> type);

    /**
     * @param name
     * @return The type of the given stored key or null
     */
    NBTType getType(String name);

    /**
     * Write the content of this Compound into the provided stream.
     * 
     * @param stream
     */
    void writeCompound(OutputStream stream);

    /**
     * @return The NBT as printable NBT-Json.
     */
    String toString();

}