package de.tr7zw.changeme.nbtapi;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import de.tr7zw.changeme.nbtapi.utils.nmsmappings.Forge1710Mappings;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;

/**
 * Base class representing NMS Compounds. For a standalone implementation check
 * {@link NBTContainer}
 * 
 * @author tr7zw
 *
 */
public class NBTCompound {

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final Lock readLock = readWriteLock.readLock();
	private final Lock writeLock = readWriteLock.writeLock();

	private String compundName;
	private NBTCompound parent;

	protected NBTCompound(NBTCompound owner, String name) {
		this.compundName = name;
		this.parent = owner;
	}

	protected Lock getReadLock() {
		return readLock;
	}

	protected Lock getWriteLock() {
		return writeLock;
	}

	protected void saveCompound() {
		if (parent != null)
			parent.saveCompound();
	}

	/**
	 * @return The Compound name
	 */
	public String getName() {
		return compundName;
	}

	/**
	 * @return The NMS Compound behind this Object
	 */
	public Object getCompound() {
		return parent.getCompound();
	}

	protected void setCompound(Object compound) {
		parent.setCompound(compound);
	}

	/**
	 * @return The parent Compound
	 */
	public NBTCompound getParent() {
		return parent;
	}

	/**
	 * Merges all data from comp into this compound. This is done in one action, so
	 * it also works with Tiles/Entities
	 * 
	 * @param comp
	 */
	public void mergeCompound(NBTCompound comp) {
		try {
			writeLock.lock();
			NBTReflectionUtil.mergeOtherNBTCompound(this, comp);
			saveCompound();
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setString(String key, String value) {
		try {
			writeLock.lock();
			NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_STRING, key, value);
			saveCompound();
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Getter
	 * 
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public String getString(String key) {
		try {
			readLock.lock();
			return (String) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_STRING, key);
		} finally {
			readLock.unlock();
		}
	}

	protected String getContent(String key) {
		return NBTReflectionUtil.getContent(this, key);
	}

	/**
	 * Setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setInteger(String key, Integer value) {
		try {
			writeLock.lock();
			NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_INT, key, value);
			saveCompound();
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Getter
	 * 
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public Integer getInteger(String key) {
		try {
			readLock.lock();
			return (Integer) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_INT, key);
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setDouble(String key, Double value) {
		try {
			writeLock.lock();
			NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_DOUBLE, key, value);
			saveCompound();
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Getter
	 * 
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public Double getDouble(String key) {
		try {
			readLock.lock();
			return (Double) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_DOUBLE, key);
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setByte(String key, Byte value) {
		try {
			writeLock.lock();
			NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_BYTE, key, value);
			saveCompound();
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Getter
	 * 
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public Byte getByte(String key) {
		try {
			readLock.lock();
			return (Byte) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_BYTE, key);
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setShort(String key, Short value) {
		try {
			writeLock.lock();
			NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_SHORT, key, value);
			saveCompound();
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Getter
	 * 
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public Short getShort(String key) {
		try {
			readLock.lock();
			return (Short) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_SHORT, key);
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setLong(String key, Long value) {
		try {
			writeLock.lock();
			NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_LONG, key, value);
			saveCompound();
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Getter
	 * 
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public Long getLong(String key) {
		try {
			readLock.lock();
			return (Long) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_LONG, key);
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setFloat(String key, Float value) {
		try {
			writeLock.lock();
			NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_FLOAT, key, value);
			saveCompound();
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Getter
	 * 
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public Float getFloat(String key) {
		try {
			readLock.lock();
			return (Float) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_FLOAT, key);
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setByteArray(String key, byte[] value) {
		try {
			writeLock.lock();
			NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_BYTEARRAY, key, value);
			saveCompound();
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Getter
	 * 
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public byte[] getByteArray(String key) {
		try {
			readLock.lock();
			return (byte[]) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_BYTEARRAY, key);
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setIntArray(String key, int[] value) {
		try {
			writeLock.lock();
			NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_INTARRAY, key, value);
			saveCompound();
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Getter
	 * 
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public int[] getIntArray(String key) {
		try {
			readLock.lock();
			return (int[]) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_INTARRAY, key);
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setBoolean(String key, Boolean value) {
		try {
			writeLock.lock();
			NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_BOOLEAN, key, value);
			saveCompound();
		} finally {
			writeLock.unlock();
		}
	}

	protected void set(String key, Object val) {
		NBTReflectionUtil.set(this, key, val);
		saveCompound();
	}

	/**
	 * Getter
	 * 
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public Boolean getBoolean(String key) {
		try {
			readLock.lock();
			return (Boolean) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_BOOLEAN, key);
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Uses Gson to store an {@link Serializable} Object
	 * 
	 * @param key
	 * @param value
	 */
	public void setObject(String key, Object value) {
		try {
			writeLock.lock();
			NBTReflectionUtil.setObject(this, key, value);
			saveCompound();
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Uses Gson to retrieve a stored Object
	 * 
	 * @param key
	 * @param type Class of the Object
	 * @return The created Object or null if empty
	 */
	public <T> T getObject(String key, Class<T> type) {
		try {
			readLock.lock();
			return NBTReflectionUtil.getObject(this, key, type);
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Save an ItemStack as a compound under a given key
	 * 
	 * @param key
	 * @param item
	 */
	public void setItemStack(String key, ItemStack item) {
		try {
			writeLock.lock();
			removeKey(key);
			addCompound(key).mergeCompound(NBTItem.convertItemtoNBT(item));
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Get an ItemStack that was saved at the given key
	 * 
	 * @param key
	 * @return
	 */
	public ItemStack getItemStack(String key) {
		try {
			readLock.lock();
			NBTCompound comp = getCompound(key);
			return NBTItem.convertNBTtoItem(comp);
		} finally {
			readLock.unlock();
		}
	}
	
	/**
	 * Setter
	 *
	 * @param key
	 * @param value
	 */
	public void setUUID(String key, UUID value) {
		try {
			writeLock.lock();
			NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_UUID, key, value);
			saveCompound();
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Getter
	 *
	 * @param key
	 * @return The stored value or NMS fallback
	 */
	public UUID getUUID(String key) {
		try {
			readLock.lock();
			return (UUID) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_UUID, key);
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * @param key String key
	 * @return True if the key is set
	 * @deprecated Use {@link #hasTag(String)} instead
	 */
	@Deprecated
	public Boolean hasKey(String key) {
		return hasTag(key);
	}

	/**
	 * @param key String key
	 * @return true, if the key is set
	 */
	public boolean hasTag(String key) {
		try {
			readLock.lock();
			Boolean b = (Boolean) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_HAS_KEY, key);
			if (b == null)
				return false;
			return b;
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * @param key Deletes the given Key
	 */
	public void removeKey(String key) {
		try {
			writeLock.lock();
			NBTReflectionUtil.remove(this, key);
			saveCompound();
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * @return Set of all stored Keys
	 */
	public Set<String> getKeys() {
		try {
			readLock.lock();
			return NBTReflectionUtil.getKeys(this);
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Creates a subCompound, or returns it if already provided
	 * 
	 * @param name Key to use
	 * @return The subCompound Object
	 */
	public NBTCompound addCompound(String name) {
		try {
			writeLock.lock();
			if (getType(name) == NBTType.NBTTagCompound)
				return getCompound(name);
			NBTReflectionUtil.addNBTTagCompound(this, name);
			NBTCompound comp = getCompound(name);
			if (comp == null)
				throw new NbtApiException("Error while adding Compound, got null!");
			saveCompound();
			return comp;
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * @param name
	 * @return The Compound instance or null
	 */
	public NBTCompound getCompound(String name) {
		try {
			readLock.lock();
			if (getType(name) != NBTType.NBTTagCompound)
				return null;
			NBTCompound next = new NBTCompound(this, name);
			if (NBTReflectionUtil.valideCompound(next))
				return next;
			return null;
		} finally {
			readLock.unlock();
		}
	}
	
	/**
	 * The same as addCompound, just with a name that better reflects what it does
	 * 
	 * @param name
	 * @return
	 */
	public NBTCompound getOrCreateCompound(String name) {
		return addCompound(name);
	}

	/**
	 * @param name
	 * @return The retrieved String List
	 */
	public NBTList<String> getStringList(String name) {
		try {
			writeLock.lock();
			NBTList<String> list = NBTReflectionUtil.getList(this, name, NBTType.NBTTagString, String.class);
			saveCompound();
			return list;
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * @param name
	 * @return The retrieved Integer List
	 */
	public NBTList<Integer> getIntegerList(String name) {
		try {
			writeLock.lock();
			NBTList<Integer> list = NBTReflectionUtil.getList(this, name, NBTType.NBTTagInt, Integer.class);
			saveCompound();
			return list;
		} finally {
			writeLock.unlock();
		}
	}
	
	/**
     * @param name
     * @return The retrieved Integer List
     */
    public NBTList<int[]> getIntArrayList(String name) {
        try {
            writeLock.lock();
            NBTList<int[]> list = NBTReflectionUtil.getList(this, name, NBTType.NBTTagIntArray, int[].class);
            saveCompound();
            return list;
        } finally {
            writeLock.unlock();
        }
    }
    
   /**
    * @param name
    * @return The retrieved Integer List
    */
   public NBTList<UUID> getUUIDList(String name) {
       try {
           writeLock.lock();
           NBTList<UUID> list = NBTReflectionUtil.getList(this, name, NBTType.NBTTagIntArray, UUID.class);
           saveCompound();
           return list;
       } finally {
           writeLock.unlock();
       }
   }

	/**
	 * @param name
	 * @return The retrieved Float List
	 */
	public NBTList<Float> getFloatList(String name) {
		try {
			writeLock.lock();
			NBTList<Float> list = NBTReflectionUtil.getList(this, name, NBTType.NBTTagFloat, Float.class);
			saveCompound();
			return list;
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * @param name
	 * @return The retrieved Double List
	 */
	public NBTList<Double> getDoubleList(String name) {
		try {
			writeLock.lock();
			NBTList<Double> list = NBTReflectionUtil.getList(this, name, NBTType.NBTTagDouble, Double.class);
			saveCompound();
			return list;
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * @param name
	 * @return The retrieved Long List
	 */
	public NBTList<Long> getLongList(String name) {
		try {
			writeLock.lock();
			NBTList<Long> list = NBTReflectionUtil.getList(this, name, NBTType.NBTTagLong, Long.class);
			saveCompound();
			return list;
		} finally {
			writeLock.unlock();
		}
	}
	
	/**
	 * Returns the type of the list, null if not a list
	 * 
	 * @param name
	 * @return
	 */
	public NBTType getListType(String name) {
		try {
			readLock.lock();
			if (getType(name) != NBTType.NBTTagList)
				return null;
			return NBTReflectionUtil.getListType(this, name);
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * @param name
	 * @return The retrieved Compound List
	 */
	public NBTCompoundList getCompoundList(String name) {
		try {
			writeLock.lock();
			NBTCompoundList list = (NBTCompoundList) NBTReflectionUtil.getList(this, name, NBTType.NBTTagCompound,
					NBTListCompound.class);
			saveCompound();
			return list;
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Returns the stored value if exists, or provided value otherwise.
	 * <p>Supported types: {@code byte/Byte, short/Short, int/Integer, long/Long, float/Float, double/Double, byte[], int[]}, {@link String}, {@link UUID}
	 *
	 * @param key key
	 * @param defaultValue default non-null value
	 * @param <T> value type
	 * @return Stored or provided value
	 */
	@SuppressWarnings("unchecked")
	public <T> T getOrDefault(String key, T defaultValue) {
		if (defaultValue == null) throw new NullPointerException("Default type in getOrDefault can't be null!");
		if (!hasTag(key)) return defaultValue;

		Class<?> clazz = defaultValue.getClass();
		if (clazz == Byte.class) return (T) getByte(key);
		if (clazz == Short.class) return (T) getShort(key);
		if (clazz == Integer.class) return (T) getInteger(key);
		if (clazz == Long.class) return (T) getLong(key);
		if (clazz == Float.class) return (T) getFloat(key);
		if (clazz == Double.class) return (T) getDouble(key);
		if (clazz == byte[].class) return (T) getByteArray(key);
		if (clazz == int[].class) return (T) getIntArray(key);
		if (clazz == String.class) return (T) getString(key);
		if (clazz == UUID.class) return (T) getUUID(key);

		throw new NbtApiException("Unsupported type for getOrDefault: " + clazz.getName());
	}

	/**
	 * @param name
	 * @return The type of the given stored key or null
	 */
	public NBTType getType(String name) {
		try {
			readLock.lock();
			if (MinecraftVersion.getVersion() == MinecraftVersion.MC1_7_R4) {
				Object nbtbase = NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET, name);
				if(nbtbase == null)
					return null;
				return NBTType.valueOf((byte)ReflectionMethod.COMPOUND_OWN_TYPE.run(nbtbase));
			}
			Object o = NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_TYPE, name);
			if (o == null)
				return null;
			return NBTType.valueOf((byte) o);
		} finally {
			readLock.unlock();
		}
	}

	public void writeCompound(OutputStream stream) {
		try {
			writeLock.lock();
			NBTReflectionUtil.writeApiNBT(this, stream);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public String toString() {
		/*
		 * StringBuilder result = new StringBuilder(); for (String key : getKeys()) {
		 * result.append(toString(key)); } return result.toString();
		 */
		return asNBTString();
	}

	/**
	 * @deprecated Just use toString()
	 * @param key
	 * @return A string representation of the given key
	 */
	@Deprecated
	public String toString(String key) {
		/*
		 * StringBuilder result = new StringBuilder(); NBTCompound compound = this;
		 * while (compound.getParent() != null) { result.append("   "); compound =
		 * compound.getParent(); } if (this.getType(key) == NBTType.NBTTagCompound) {
		 * return this.getCompound(key).toString(); } else { return result + "-" + key +
		 * ": " + getContent(key) + System.lineSeparator(); }
		 */
		return asNBTString();
	}

	/**
	 * Remove all keys from this compound
	 */
	public void clearNBT(){
		for (String key : getKeys()) {
			removeKey(key);
		}
	}

	/**
	 * @deprecated Just use toString()
	 * @return A {@link String} representation of the NBT in Mojang JSON. This is different from normal JSON!
	 */
	@Deprecated
	public String asNBTString() {
		try {
			readLock.lock();
			Object comp = NBTReflectionUtil.gettoCompount(getCompound(), this);
			if (comp == null)
				return "{}";
			if (MinecraftVersion.isForgePresent() && MinecraftVersion.getVersion() == MinecraftVersion.MC1_7_R4){
				return Forge1710Mappings.toString(comp);
			}else {
				return comp.toString();
			}
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	/**
	 * Does a deep compare to check if everything is the same
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if(obj instanceof NBTCompound) {
			NBTCompound other = (NBTCompound) obj;
			if(getKeys().equals(other.getKeys())) {
				for(String key : getKeys()) {
					if(!isEqual(this, other, key)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	
	protected static boolean isEqual(NBTCompound compA, NBTCompound compB, String key) {
		if(compA.getType(key) != compB.getType(key))return false;
		switch(compA.getType(key)) {
		case NBTTagByte:
			return compA.getByte(key).equals(compB.getByte(key));
		case NBTTagByteArray:
			return Arrays.equals(compA.getByteArray(key), compB.getByteArray(key));
		case NBTTagCompound:
			return compA.getCompound(key).equals(compB.getCompound(key));
		case NBTTagDouble:
			return compA.getDouble(key).equals(compB.getDouble(key));
		case NBTTagEnd:
			return true; //??
		case NBTTagFloat:
			return compA.getFloat(key).equals(compB.getFloat(key));
		case NBTTagInt:
			return compA.getInteger(key).equals(compB.getInteger(key));
		case NBTTagIntArray:
			return Arrays.equals(compA.getIntArray(key), compB.getIntArray(key));
		case NBTTagList:
			return NBTReflectionUtil.getEntry(compA, key).toString().equals(NBTReflectionUtil.getEntry(compB, key).toString()); // Just string compare the 2 lists
		case NBTTagLong:
			return compA.getLong(key).equals(compB.getLong(key));
		case NBTTagShort:
			return compA.getShort(key).equals(compB.getShort(key));
		case NBTTagString:
			return compA.getString(key).equals(compB.getString(key));
		}
		return false;
	}

}
