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
     * Given a key, return the value associated with that key.
     * 
     * @param key The key to get the value for.
     * @return The value of the key.
     */
    String getString(String key);

    /**
     * Given a key, return the value associated with that key as an Integer, or 0 if
     * the key is not found.
     * 
     * @param key The key to look up in the properties file.
     * @return The value of the key.
     */
    Integer getInteger(String key);

    /**
     * Returns the value associated with the given key as a double, or false of not
     * found.
     * 
     * @param key The key of the preference to retrieve.
     * @return A double value
     */
    Double getDouble(String key);

    /**
     * Get the value of the given key as a byte, or 0 if the key is not found.
     * 
     * @param key The key to get the value for.
     * @return A byte
     */
    Byte getByte(String key);

    /**
     * Returns the value of the key as a Short, or 0 if the key is not found.
     * 
     * @param key The key of the value you want to get.
     * @return A short value
     */
    Short getShort(String key);

    /**
     * Returns the value associated with the given key as a Long, or 0 if the key is
     * not found.
     * 
     * @param key The key of the value you want to get.
     * @return A Long object
     */
    Long getLong(String key);

    /**
     * Returns the value of the given key as a Float, or 0 if the key does not
     * exist.
     * 
     * @param key The key of the preference to retrieve.
     * @return A float value
     */
    Float getFloat(String key);

    /**
     * Returns the value associated with the given key as a byte array, or null if
     * the key is not found.
     * 
     * @param key The key to use to retrieve the value.
     * @return A byte array.
     */
    byte[] getByteArray(String key);

    /**
     * Returns the value associated with the given key as an array of integers, or
     * null if the key does not exist.
     * 
     * @param key The key of the value you want to get.
     * @return An array of integers.
     */
    int[] getIntArray(String key);

    /**
     * Returns the value associated with the given key, or false if the key is not
     * found.
     * 
     * @param key The key of the preference to retrieve.
     * @return A boolean value.
     */
    Boolean getBoolean(String key);

    /**
     * It returns an ItemStack associated with the given key, or null if the key
     * does not exist.
     * 
     * @param key The key of the itemstack you want to get.
     * @return An ItemStack
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
     * Given a key, return the UUID of the key.
     * 
     * @param key The key to get the value from
     * @return A UUID object.
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