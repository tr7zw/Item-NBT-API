package de.tr7zw.nbtapi;

import org.apache.commons.lang.NotImplementedException;

/**
 * {@link NBTListCompound} implementation for NBTLists
 * 
 * @author tr7zw
 *
 */
@Deprecated(forRemoval = false)
public class NBTCompoundList extends NBTList<NBTListCompound> {

	protected NBTCompoundList(NBTCompound owner, String name, NBTType type, Object list) {
		super(owner, name, type, list);
	}

	/**
	 * Adds a new Compound to the end of the List and returns it.
	 * 
	 * @return The added {@link NBTListCompound}
	 */
	public NBTListCompound addCompound() {
		return (NBTListCompound) addCompound(null);
	}
	
	/**
	 * Adds a copy of the Compound to the end of the List and returns it.
	 * When null is given, a new Compound will be created
	 * 
	 * @param comp
	 * @return
	 */
	public NBTCompound addCompound(NBTCompound comp) {
	    throw new NotImplementedException();
	}

	/**
	 * Adds a new Compound to the end of the List.
	 * 
	 * 
	 * @deprecated Please use addCompound!
	 * @param empty
	 * @return True, if compound was added
	 */
	@Override
	@Deprecated
	public boolean add(NBTListCompound empty) {
	    throw new NotImplementedException();
	}

	@Override
	public void add(int index, NBTListCompound element) {
	    throw new NotImplementedException();
	}

	@Override
	public NBTListCompound get(int index) {
	    throw new NotImplementedException();
	}

	@Override
	public NBTListCompound set(int index, NBTListCompound element) {
		throw new NotImplementedException("This method doesn't work in the ListCompound context.");
	}

}
