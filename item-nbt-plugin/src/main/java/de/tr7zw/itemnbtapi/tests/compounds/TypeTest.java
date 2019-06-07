package de.tr7zw.itemnbtapi.tests.compounds;

import de.tr7zw.itemnbtapi.NBTCompound;
import de.tr7zw.itemnbtapi.NBTContainer;
import de.tr7zw.itemnbtapi.NBTType;
import de.tr7zw.itemnbtapi.NbtApiException;
import de.tr7zw.itemnbtapi.tests.Test;

public class TypeTest implements Test{

	@Override
	public void test() throws Exception {
		NBTCompound comp = new NBTContainer();
		comp.setString("s", "test");
		comp.setInteger("i", 42);
		comp.addCompound("c");
		if(comp.getType("s") != NBTType.NBTTagString || comp.getType("i") != NBTType.NBTTagInt || comp.getType("c") != NBTType.NBTTagCompound)
			throw new NbtApiException("One parsed type did not match what it should have been!");
	}

}
