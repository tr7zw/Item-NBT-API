package de.tr7zw.nbtapi;

import org.apache.commons.lang.NotImplementedException;

/**
 * Float implementation for NBTLists
 * 
 * @author tr7zw
 *
 */
@Deprecated(forRemoval = false)
public class NBTFloatList extends NBTList<Float> {

	protected NBTFloatList(NBTCompound owner, String name, NBTType type, Object list) {
		super(owner, name, type, list);
	}


	@Override
	public Float get(int index) {
	    throw new NotImplementedException();
	}

}
