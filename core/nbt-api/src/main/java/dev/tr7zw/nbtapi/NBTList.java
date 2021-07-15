package dev.tr7zw.nbtapi;

import java.util.List;

/**
 * Abstract List implementation for ListCompounds
 * 
 * @author tr7zw
 *
 * @param <T>
 */
public abstract class NBTList<T> implements List<T> {


	protected abstract Object asTag(T object);

	/**
	 * @return The type that this list contains
	 */
	public abstract NBTType getType();

}
