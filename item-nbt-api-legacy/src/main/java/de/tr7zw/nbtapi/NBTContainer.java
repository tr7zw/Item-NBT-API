package de.tr7zw.nbtapi;

import java.io.InputStream;

import org.apache.commons.lang.NotImplementedException;

/**
 * A Standalone {@link NBTCompound} implementation. All data is just kept inside
 * this Object.
 * 
 * @author tr7zw
 *
 */
@Deprecated
public class NBTContainer extends NBTCompound {

	/**
	 * Creates an empty, standalone NBTCompound
	 */
	public NBTContainer() {
		super(null, null);
		throw new NotImplementedException();
	}

	/**
	 * Takes in any NMS Compound to wrap it
	 * 
	 * @param nbt
	 */
	public NBTContainer(Object nbt) {
		super(null, null);
		throw new NotImplementedException();
	}

	/**
	 * Reads in a NBT InputStream
	 * 
	 * @param inputsteam
	 */
	public NBTContainer(InputStream inputsteam) {
		super(null, null);
		throw new NotImplementedException();
	}

	/**
	 * Parses in a NBT String to a standalone {@link NBTCompound}. Can throw a
	 * {@link NbtApiException} in case something goes wrong.
	 * 
	 * @param nbtString
	 */
	public NBTContainer(String nbtString) {
		super(null, null);
		throw new NotImplementedException();
	}

	@Override
	public Object getCompound() {
	    throw new NotImplementedException();
	}

	@Override
	public void setCompound(Object tag) {
	    throw new NotImplementedException();
	}

}
