package de.tr7zw.nbtapi;

import org.apache.commons.lang.NotImplementedException;

/**
 * String implementation for NBTLists
 * 
 * @author tr7zw
 *
 */
@Deprecated(forRemoval = false)
public class NBTStringList extends NBTList<String> {

	protected NBTStringList(NBTCompound owner, String name, NBTType type, Object list) {
		super(owner, name, type, list);
	}

	@Override
	public String get(int index) {
		throw new NotImplementedException();
	}

}
