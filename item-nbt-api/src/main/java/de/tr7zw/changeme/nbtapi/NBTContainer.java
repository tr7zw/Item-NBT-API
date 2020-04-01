package de.tr7zw.changeme.nbtapi;

import java.io.InputStream;

import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ClassWrapper;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ObjectCreator;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;

/**
 * A Standalone {@link NBTCompound} implementation. All data is just kept inside
 * this Object.
 * 
 * @author tr7zw
 *
 */
public class NBTContainer extends NBTCompound {

	private Object nbt;

	/**
	 * Creates an empty, standalone NBTCompound
	 */
	public NBTContainer() {
		super(null, null);
		nbt = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
	}

	/**
	 * Takes in any NMS Compound to wrap it
	 * 
	 * @param nbt
	 */
	public NBTContainer(Object nbt) {
		super(null, null);
		if (nbt == null) {
			throw new NullPointerException("The NBT-Object can't be null!");
		}
		if (!ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().isAssignableFrom(nbt.getClass())) {
			throw new NbtApiException("The object '" + nbt.getClass() + "' is not a valid NBT-Object!");
		}
		this.nbt = nbt;
	}

	/**
	 * Reads in a NBT InputStream
	 * 
	 * @param inputsteam
	 */
	public NBTContainer(InputStream inputsteam) {
		super(null, null);
		this.nbt = NBTReflectionUtil.readNBT(inputsteam);
	}

	/**
	 * Parses in a NBT String to a standalone {@link NBTCompound}. Can throw a
	 * {@link NbtApiException} in case something goes wrong.
	 * 
	 * @param nbtString
	 */
	public NBTContainer(String nbtString) {
		super(null, null);
		if (nbtString == null) {
			throw new NullPointerException("The String can't be null!");
		}
		try {
			nbt = ReflectionMethod.PARSE_NBT.run(null, nbtString);
		} catch (Exception ex) {
			throw new NbtApiException("Unable to parse Malformed Json!", ex);
		}
	}

	@Override
	public Object getCompound() {
		return nbt;
	}

	@Override
	public void setCompound(Object tag) {
		nbt = tag;
	}

}
