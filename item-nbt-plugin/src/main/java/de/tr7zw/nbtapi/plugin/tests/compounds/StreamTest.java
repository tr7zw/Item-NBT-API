package de.tr7zw.nbtapi.plugin.tests.compounds;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class StreamTest implements Test {

    @Override
    public void test() throws Exception {
        NBTContainer base = new NBTContainer();
        base.addCompound("sub").setString("hello", "world");
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        base.getOrCreateCompound("sub").writeCompound(outStream);
        byte[] data = outStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        NBTContainer container = new NBTContainer(inputStream);
        if (!container.toString().equals(base.getOrCreateCompound("sub").toString())) {
            throw new NbtApiException("Component content did not match! " + base.getCompound("sub") + " " + container);
        }
    }

}
