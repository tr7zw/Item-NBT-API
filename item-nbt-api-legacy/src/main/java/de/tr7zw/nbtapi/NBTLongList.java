package de.tr7zw.nbtapi;

import org.apache.commons.lang.NotImplementedException;

/**
 * Long implementation for NBTLists
 * 
 * @author tr7zw
 *
 */
@Deprecated
public class NBTLongList extends NBTList<Long> {

	protected NBTLongList(NBTCompound owner, String name, NBTType type, Object list) {
		super(owner, name, type, list);
	}

	@Override
	public Long get(int index) {
	    throw new NotImplementedException();
	}

}
