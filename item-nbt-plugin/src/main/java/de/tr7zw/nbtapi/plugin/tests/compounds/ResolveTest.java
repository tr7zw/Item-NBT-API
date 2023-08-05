package de.tr7zw.nbtapi.plugin.tests.compounds;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class ResolveTest implements Test {

    @Override
    public void test() throws Exception {
        ReadWriteNBT src = NBT.createNBTObject();
        src.resolveOrCreateCompound("foo.bar.baz").setInteger("test", 42);
        if (src.resolveOrDefault("foo.bar.baz.test", 0) != 42) {
            throw new NbtApiException("Wasn't able to check the value! The Item-NBT-API may not work!");
        }
        if (!Integer.valueOf(42).equals(src.resolveOrNull("foo.bar.baz.test", Integer.class))) {
            throw new NbtApiException("Wasn't able to check the value! The Item-NBT-API may not work!");
        }
        src.resolveOrCreateCompound("foo.some\\.key.baz").setInteger("other", 123);
        if (src.getOrCreateCompound("foo").getOrCreateCompound("some.key").getOrCreateCompound("baz")
                .getInteger("other") != 123) {
            throw new NbtApiException("Wasn't able to check the value! The Item-NBT-API may not work!");
        }
    }

}
