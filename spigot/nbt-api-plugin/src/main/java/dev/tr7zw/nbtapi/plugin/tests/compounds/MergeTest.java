package dev.tr7zw.nbtapi.plugin.tests.compounds;

import de.tr7zw.nbtapi.NBTContainer;
import dev.tr7zw.nbtapi.NbtApiException;
import dev.tr7zw.nbtapi.plugin.tests.Test;

public class MergeTest implements Test {

	@Override
	public void test() throws Exception {
		NBTContainer test1 = new NBTContainer();
		test1.setString("test1", "test");
		NBTContainer test2 = new NBTContainer();
		test2.setString("test2", "test");
		test2.addCompound("test").setLong("time", System.currentTimeMillis());
		test1.mergeCompound(test2);
		if (!test1.getString("test1").equals(test1.getString("test2"))) {
			throw new NbtApiException("Wasn't able to merge Compounds!");
		}
	}

}
