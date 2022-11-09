package de.tr7zw.nbtapi.plugin.tests.compounds;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTType;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class EnumTest implements Test {

	@Override
	public void test() throws Exception {
		NBTCompound comp = new NBTContainer();
		comp.setEnum("test", NBTType.NBTTagEnd);
		NBTType type = comp.getEnum("test", NBTType.class);
		NBTType typeNonNull = comp.getOrNull("test", NBTType.class);
		NBTType typeDefault = comp.getOrDefault("invalid", NBTType.NBTTagByte);
		NBTType typeDefaultFound = comp.getOrDefault("test", NBTType.NBTTagByte);
		if (type != NBTType.NBTTagEnd || typeNonNull != NBTType.NBTTagEnd  || typeDefaultFound != NBTType.NBTTagEnd || typeDefault != NBTType.NBTTagByte)
			throw new NbtApiException("One enum did not match what it should have been!");
	}

}
