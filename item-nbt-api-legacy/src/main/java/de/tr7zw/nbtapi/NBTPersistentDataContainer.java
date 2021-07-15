package de.tr7zw.nbtapi;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.persistence.PersistentDataContainer;

@Deprecated
public class NBTPersistentDataContainer extends NBTCompound {

	private final PersistentDataContainer container;
	
	protected NBTPersistentDataContainer(PersistentDataContainer container) {
		super(null, null);
		this.container = container;
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
