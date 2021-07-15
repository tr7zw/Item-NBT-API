package dev.tr7zw.nbtapi.plugin.tests.compounds;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import de.tr7zw.nbtapi.NBTContainer;
import dev.tr7zw.nbtapi.NbtApiException;
import dev.tr7zw.nbtapi.plugin.tests.Test;

public class StreamTest implements Test{

	@Override
	public void test() throws Exception {
		NBTContainer base = new NBTContainer();
		base.addCompound("sub").setString("hello", "world");
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		base.getCompound("sub").writeCompound(outStream);
		byte[] data = outStream.toByteArray();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
		NBTContainer container = new NBTContainer(inputStream);
		if(!container.toString().equals(base.getCompound("sub").toString())) {
			throw new NbtApiException("Component content did not match! " + base.getCompound("sub") + " " + container);
		}
	}

}
