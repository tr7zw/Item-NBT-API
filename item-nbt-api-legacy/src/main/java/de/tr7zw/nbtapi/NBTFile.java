package de.tr7zw.nbtapi;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.NotImplementedException;

/**
 * {@link NBTCompound} implementation backed by a {@link File}
 * 
 * @author tr7zw
 *
 */
@Deprecated(forRemoval = false)
public class NBTFile extends NBTCompound {

	/**
	 * Creates a NBTFile that uses @param file to store it's data. If this file
	 * exists, the data will be loaded.
	 * 
	 * @param file
	 * @throws IOException
	 */
	public NBTFile(File file) throws IOException {
		super(null, null);
		throw new NotImplementedException();
	}

	/**
	 * Saves the data to the file
	 * 
	 * @throws IOException
	 */
	public void save() throws IOException {
	    throw new NotImplementedException();
	}

	/**
	 * @return The File used to store the data
	 */
	public File getFile() {
	    throw new NotImplementedException();
	}

	@Override
	public Object getCompound() {
	    throw new NotImplementedException();
	}

}
