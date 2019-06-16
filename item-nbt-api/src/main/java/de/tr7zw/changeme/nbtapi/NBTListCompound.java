package de.tr7zw.changeme.nbtapi;

import java.util.Set;

import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;

/**
 * Cut down version of the {@link NBTCompound} for inside
 * {@link NBTCompoundList} This Compound implementation is missing the ability
 * for further subCompounds and Lists. This class probably will change in the
 * future
 * 
 * @author tr7zw
 *
 */
public class NBTListCompound {

	private NBTList<?> owner;
	private Object compound;

	protected NBTListCompound(NBTList<?> parent, Object obj) {
		owner = parent;
		compound = obj;
	}

	/**
	 * String setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setString(String key, String value) {
		if (value == null) {
			remove(key);
			return;
		}
		try {
			compound.getClass().getMethod("setString", String.class, String.class).invoke(compound, key, value);
			owner.save();
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

	/**
	 * int setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setInteger(String key, int value) {
		try {
			compound.getClass().getMethod("setInt", String.class, int.class).invoke(compound, key, value);
			owner.save();
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

	/**
	 * int getter
	 * 
	 * @param value
	 * @return Saved value or NMS fallback
	 */
	public int getInteger(String value) {
		try {
			return (int) compound.getClass().getMethod("getInt", String.class).invoke(compound, value);
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

	/**
	 * double setter
	 * 
	 * @param key
	 * @param value
	 */
	public void setDouble(String key, double value) {
		try {
			compound.getClass().getMethod("setDouble", String.class, double.class).invoke(compound, key, value);
			owner.save();
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

	/**
	 * double getter
	 * 
	 * @param key
	 * @return Saved value or NMS fallback
	 */
	public double getDouble(String key) {
		try {
			return (double) compound.getClass().getMethod("getDouble", String.class).invoke(compound, key);
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

	/**
	 * String getter
	 * 
	 * @param key
	 * @return Saved value or NMS fallback
	 */
	public String getString(String key) {
		try {
			return (String) compound.getClass().getMethod("getString", String.class).invoke(compound, key);
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

	/**
	 * Checks for a given key
	 * 
	 * @param key
	 * @return true if the Compound has this key, else false
	 */
	public boolean hasKey(String key) {
		try {
			return (boolean) compound.getClass().getMethod("hasKey", String.class).invoke(compound, key);
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

	/**
	 * @return Set containing all keys
	 */
	@SuppressWarnings("unchecked")
	public Set<String> getKeys() {
		try {
			return (Set<String>) ReflectionMethod.LISTCOMPOUND_GET_KEYS.run(compound);
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

	/**
	 * Removes a key from the Compound
	 * 
	 * @param key
	 */
	public void remove(String key) {
		try {
			compound.getClass().getMethod("remove", String.class).invoke(compound, key);
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

}
