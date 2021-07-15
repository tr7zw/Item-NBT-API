package de.tr7zw.nbtapi;

import org.apache.commons.lang.NotImplementedException;

/**
 * Cut down version of the {@link NBTCompound} for inside
 * {@link NBTCompoundList} This Compound implementation is missing the ability
 * for further subCompounds and Lists. This class probably will change in the
 * future
 * 
 * @author tr7zw
 *
 */
@Deprecated
public class NBTListCompound extends NBTCompound {

	protected NBTListCompound(NBTList<?> parent, Object obj) {
		super(null, null);
	}
	
	public NBTList<?> getListParent() {
	    throw new NotImplementedException();
	}

	@Override
	public Object getCompound() {
	    throw new NotImplementedException();
	}

	@Override
	protected void setCompound(Object compound) {
	    throw new NotImplementedException();
	}

}
