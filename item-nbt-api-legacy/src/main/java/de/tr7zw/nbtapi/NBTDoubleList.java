package de.tr7zw.nbtapi;

import org.apache.commons.lang.NotImplementedException;

/**
 * Double implementation for NBTLists
 * 
 * @author tr7zw
 *
 */
@Deprecated(forRemoval = false)
public class NBTDoubleList extends NBTList<Double> {

	protected NBTDoubleList(NBTCompound owner, String name, NBTType type, Object list) {
		super(owner, name, type, list);
	}

	@Override
	public Double get(int index) {
	    throw new NotImplementedException();
	}

}
