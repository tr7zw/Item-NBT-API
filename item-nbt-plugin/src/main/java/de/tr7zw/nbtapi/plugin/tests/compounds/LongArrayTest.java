package de.tr7zw.nbtapi.plugin.tests.compounds;

import java.util.Arrays;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class LongArrayTest implements Test {

    @Override
    public void test() throws Exception {
        NBTContainer comp = new NBTContainer();

        comp.setLongArray("test", new long[] { 1, 2, 3, 4, Long.MAX_VALUE });

        if (!comp.hasTag("test")) {
            throw new NbtApiException("Wasn't able to check a key! The Item-NBT-API may not work!");
        }
        if (!Arrays.equals(comp.getLongArray("test"), new long[] { 1, 2, 3, 4, Long.MAX_VALUE })) {
            throw new NbtApiException("The long key does not equal the original value! The Item-NBT-API may not work!");
        }
    }

}
