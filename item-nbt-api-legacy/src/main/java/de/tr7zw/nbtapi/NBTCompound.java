package de.tr7zw.nbtapi;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.inventory.ItemStack;

/**
 * Base class representing NMS Compounds. For a standalone implementation check
 * {@link NBTContainer}
 * 
 * @author tr7zw
 *
 */
@Deprecated
public class NBTCompound {

	protected NBTCompound(NBTCompound owner, String name) {
	    throw new NotImplementedException();
	}


	protected void saveCompound() {
	    throw new NotImplementedException();
	}

	/**
	 * @return The Compound name
	 */
	public String getName() {
	    throw new NotImplementedException();
	}

	/**
	 * @return The NMS Compound behind this Object
	 */
	public Object getCompound() {
	    throw new NotImplementedException();
	}

	protected void setCompound(Object compound) {
	    throw new NotImplementedException();
	}

	/**
	 * @return The parent Compound
	 */
	public NBTCompound getParent() {
	    throw new NotImplementedException();
	}

	/**
	 * Merges all data from comp into this compound. This is done in one action, so
	 * it also works with Tiles/Entities
	 * 
	 * @param comp
	 */
	public void mergeCompound(NBTCompound comp) {
	    throw new NotImplementedException();
	}

	/**
	 * Setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setString(String key, String value) {
	    throw new NotImplementedException();
	}

	/**
	 * Getter
	 * 
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public String getString(String key) {
	    throw new NotImplementedException();
	}

	protected String getContent(String key) {
	    throw new NotImplementedException();
	}

	/**
	 * Setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setInteger(String key, Integer value) {
	    throw new NotImplementedException();
	}

	/**
	 * Getter
	 * 
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public Integer getInteger(String key) {
	    throw new NotImplementedException();
	}

	/**
	 * Setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setDouble(String key, Double value) {
	    throw new NotImplementedException();
	}

	/**
	 * Getter
	 * 
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public Double getDouble(String key) {
	    throw new NotImplementedException();
	}

	/**
	 * Setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setByte(String key, Byte value) {
	    throw new NotImplementedException();
	}

	/**
	 * Getter
	 * 
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public Byte getByte(String key) {
	    throw new NotImplementedException();
	}

	/**
	 * Setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setShort(String key, Short value) {
	    throw new NotImplementedException();
	}

	/**
	 * Getter
	 * 
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public Short getShort(String key) {
	    throw new NotImplementedException();
	}

	/**
	 * Setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setLong(String key, Long value) {
	    throw new NotImplementedException();
	}

	/**
	 * Getter
	 * 
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public Long getLong(String key) {
	    throw new NotImplementedException();
	}

	/**
	 * Setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setFloat(String key, Float value) {
	    throw new NotImplementedException();
	}

	/**
	 * Getter
	 * 
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public Float getFloat(String key) {
	    throw new NotImplementedException();
	}

	/**
	 * Setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setByteArray(String key, byte[] value) {
	    throw new NotImplementedException();
	}

	/**
	 * Getter
	 * 
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public byte[] getByteArray(String key) {
	    throw new NotImplementedException();
	}

	/**
	 * Setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setIntArray(String key, int[] value) {
	    throw new NotImplementedException();
	}

	/**
	 * Getter
	 * 
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public int[] getIntArray(String key) {
	    throw new NotImplementedException();
	}

	/**
	 * Setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setBoolean(String key, Boolean value) {
	    throw new NotImplementedException();
	}

	protected void set(String key, Object val) {
	    throw new NotImplementedException();
	}

	/**
	 * Getter
	 * 
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public Boolean getBoolean(String key) {
	    throw new NotImplementedException();
	}

	/**
	 * Uses Gson to store an {@link Serializable} Object
	 * 
	 * @param key
	 * @param value
	 */
	public void setObject(String key, Object value) {
	    throw new NotImplementedException();
	}

	/**
	 * Uses Gson to retrieve a stored Object
	 * 
	 * @param key
	 * @param type Class of the Object
	 * @return The created Object or null if empty
	 */
	public <T> T getObject(String key, Class<T> type) {
	    throw new NotImplementedException();
	}

	/**
	 * Save an ItemStack as a compound under a given key
	 * 
	 * @param key
	 * @param item
	 */
	public void setItemStack(String key, ItemStack item) {
	    throw new NotImplementedException();
	}

	/**
	 * Get an ItemStack that was saved at the given key
	 * 
	 * @param key
	 * @return
	 */
	public ItemStack getItemStack(String key) {
	    throw new NotImplementedException();
	}
	
		/**
	 * Setter
	 *
	 * @param key
	 * @param value
	 */
	public void setUUID(String key, UUID value) {
	    throw new NotImplementedException();
	}

	/**
	 * Getter
	 *
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public UUID getUUID(String key) {
	    throw new NotImplementedException();
	}

	/**
	 * @param key
	 * @return True if the key is set
	 */
	public Boolean hasKey(String key) {
	    throw new NotImplementedException();
	}

	/**
	 * @param key Deletes the given Key
	 */
	public void removeKey(String key) {
	    throw new NotImplementedException();
	}

	/**
	 * @return Set of all stored Keys
	 */
	public Set<String> getKeys() {
	    throw new NotImplementedException();
	}

	/**
	 * Creates a subCompound, or returns it if already provided
	 * 
	 * @param name Key to use
	 * @return The subCompound Object
	 */
	public NBTCompound addCompound(String name) {
	    throw new NotImplementedException();
	}

	/**
	 * @param name
	 * @return The Compound instance or null
	 */
	public NBTCompound getCompound(String name) {
	    throw new NotImplementedException();
	}
	
	/**
	 * The same as addCompound, just with a name that better reflects what it does
	 * 
	 * @param name
	 * @return
	 */
	public NBTCompound getOrCreateCompound(String name) {
	    throw new NotImplementedException();
	}

	/**
	 * @param name
	 * @return The retrieved String List
	 */
	public NBTList<String> getStringList(String name) {
	    throw new NotImplementedException();
	}

	/**
	 * @param name
	 * @return The retrieved Integer List
	 */
	public NBTList<Integer> getIntegerList(String name) {
	    throw new NotImplementedException();
	}

	/**
	 * @param name
	 * @return The retrieved Float List
	 */
	public NBTList<Float> getFloatList(String name) {
	    throw new NotImplementedException();
	}

	/**
	 * @param name
	 * @return The retrieved Double List
	 */
	public NBTList<Double> getDoubleList(String name) {
	    throw new NotImplementedException();
	}

	/**
	 * @param name
	 * @return The retrieved Long List
	 */
	public NBTList<Long> getLongList(String name) {
	    throw new NotImplementedException();
	}
	
	/**
	 * Returns the type of the list, null if not a list
	 * 
	 * @param name
	 * @return
	 */
	public NBTType getListType(String name) {
	    throw new NotImplementedException();
	}

	/**
	 * @param name
	 * @return The retrieved Compound List
	 */
	public NBTCompoundList getCompoundList(String name) {
	    throw new NotImplementedException();
	}

	/**
	 * @param name
	 * @return The type of the given stored key or null
	 */
	public NBTType getType(String name) {
	    throw new NotImplementedException();
	}

	public void writeCompound(OutputStream stream) {
	    throw new NotImplementedException();
	}

	@Override
	public String toString() {
	    throw new NotImplementedException();
	}

	/**
	 * @deprecated Just use toString()
	 * @param key
	 * @return A string representation of the given key
	 */
	@Deprecated
	public String toString(String key) {
	    throw new NotImplementedException();
	}

	/**
	 * @deprecated Just use toString()
	 * @return A {@link String} representation of the NBT in Mojang JSON. This is different from normal JSON!
	 */
	@Deprecated
	public String asNBTString() {
	    throw new NotImplementedException();
	}

	@Override
	public int hashCode() {
	    throw new NotImplementedException();
	}

	/**
	 * Does a deep compare to check if everything is the same
	 */
	@Override
	public boolean equals(Object obj) {
	    throw new NotImplementedException();
	}
	
	protected static boolean isEqual(NBTCompound compA, NBTCompound compB, String key) {
	    throw new NotImplementedException();
	}

}
