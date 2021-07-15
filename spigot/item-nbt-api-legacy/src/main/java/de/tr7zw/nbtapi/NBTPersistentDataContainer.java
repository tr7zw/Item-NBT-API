package de.tr7zw.nbtapi;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.persistence.PersistentDataContainer;

@Deprecated
public class NBTPersistentDataContainer extends NBTCompound {
	
	protected NBTPersistentDataContainer(PersistentDataContainer container) {
		super(null, null);
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
