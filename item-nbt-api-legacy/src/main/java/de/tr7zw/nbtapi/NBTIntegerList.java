package de.tr7zw.nbtapi;

import org.apache.commons.lang.NotImplementedException;

/**
 * Integer implementation for NBTLists
 * 
 * @author tr7zw
 *
 */
@Deprecated
public class NBTIntegerList extends NBTList<Integer> {

	protected NBTIntegerList(NBTCompound owner, String name, NBTType type, Object list) {
		super(owner, name, type, list);
	}

	@Override
	public Integer get(int index) {
	    throw new NotImplementedException();
	}

}
