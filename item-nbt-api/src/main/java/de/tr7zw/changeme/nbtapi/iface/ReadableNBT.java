package de.tr7zw.changeme.nbtapi.iface;

import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

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
    @Nullable
    byte[] getByteArray(String key);

    /**
     * Returns the value associated with the given key as an array of integers, or
     * null if the key does not exist.
     * 
     * @param key The key of the value you want to get.
     * @return An array of integers.
     */
    @Nullable
    int[] getIntArray(String key);

    /**
     * Returns the value associated with the given key as an array of longs, or null
     * if the key does not exist.
     * 
     * Requires 1.16+
     * 
     * @param key The key of the value you want to get.
     * @return An array of integers.
     */
    @Nullable
    long[] getLongArray(String key);

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
    @Nullable
    ItemStack getItemStack(String key);

    /**
     * Get an {@link ItemStack} array that was saved at the given key, or null if no
     * stored data was found
     * 
     * @param key key
     * @return The stored {@link ItemStack} array, or null if stored data wasn't
     *         found
     */
    @Nullable
    ItemStack[] getItemStackArray(String key);

    /**
     * Given a key, return the UUID of the key.
     * 
     * @param key The key to get the value from
     * @return A UUID object.
     */
    @Nullable
    UUID getUUID(String key);

    /**
     * Checks whether the provided key exists
     *
     * @param key String key
     * @return true, if the key is set
     */
    boolean hasTag(String key);

    /**
     * Checks whether the provided key exists and has the specified type
     *
     * @param key  String key
     * @param type nbt tag type
     * @return whether the key is set and has the specified type
     */
    default boolean hasTag(String key, NBTType type) {
        return hasTag(key) && getType(key) == type;
    }

    /**
     * @return Set of all stored Keys
     */
    Set<String> getKeys();

    /**
     * @param name
     * @return The Compound instance or null
     */
    @Nullable
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
    @Nullable
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
     * {@code Boolean, Byte, Short, Integer, Long, Float, Double, byte[], int[], long[]},
     * {@link String}, {@link UUID}, and {@link Enum}
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
     * {@code Boolean, Byte, Short, Integer, Long, Float, Double, byte[], int[], long[]},
     * {@link String}, {@link UUID}, and {@link Enum}
     *
     * @param key  key
     * @param type data type
     * @param <T>  value type
     * @return Stored or provided value
     */
    @Nullable
    <T> T getOrNull(String key, Class<?> type);

    /**
     * Returns the resolved value if exists, or null.
     * <p>
     * Supported types:
     * {@code Boolean, Byte, Short, Integer, Long, Float, Double, byte[], int[], long[]},
     * {@link String}, {@link UUID}, and {@link Enum}
     *
     * @param key  Path key, seperated by '.'. For example: "foo.bar.baz". Dots can
     *             be escaped with a backslash.
     * @param type data type
     * @param <T>  value type
     * @return resolved or provided value
     */
    @Nullable
    <T> T resolveOrNull(String key, Class<?> type);

    /**
     * Returns the resolved value if exists, or provided value otherwise.
     * <p>
     * Supported types:
     * {@code Boolean, Byte, Short, Integer, Long, Float, Double, byte[], int[], long[]},
     * {@link String}, {@link UUID}, and {@link Enum}
     *
     * @param key          Path key, seperated by '.'. For example: "foo.bar.baz".
     *                     Dots can be escaped with a backslash.
     * @param defaultValue default non-null value
     * @param <T>          value type
     * @return resolved or provided value
     */
    <T> T resolveOrDefault(String key, T defaultValue);

    /**
     * Returns the resolved Compound if exists, or null.
     * <p>
     * 
     * @param key Path key, seperated by '.'. For example: "foo.bar.baz". Dots can
     *            be escaped with a backslash.
     * @return The resolved value if exists, or null.
     */
    @Nullable
    ReadableNBT resolveCompound(String key);

    /**
     * Get the object at the specified key via the handler.
     * 
     * @param <T>
     * @param key
     * @param handler
     * @return
     */
    <T> T get(String key, NBTHandler<T> handler);

    /**
     * Get an Enum value that has been set via setEnum or setString(key,
     * value.name()). Passing null/invalid keys will return null.
     * 
     * @param <E>
     * @param key
     * @param type
     * @return
     */
    @Nullable
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