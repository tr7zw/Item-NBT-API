package de.tr7zw.nbtapi.plugin.tests.compounds;

import java.util.Arrays;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBTCompoundList;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBT;
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
        ReadWriteNBT comp = src.resolveOrCreateCompound("foo.bar.baz");
        comp.setIntArray("intarray", new int[] {1,2,3,4,5,6});
        comp.getStringList("strlist").addAll(Arrays.asList("Hello", "World!"));
        ReadWriteNBTCompoundList list = comp.getCompoundList("somelist");
        list.addCompound().setInteger("id", 1);
        list.addCompound().setInteger("id", 2);
        list.addCompound().setInteger("id", 3);
        list.addCompound().setInteger("id", 4);
        list.addCompound().setInteger("id", 5);
        list.addCompound().setInteger("id", 6);
        ReadableNBT listEntry = src.resolveCompound("foo.bar.baz.somelist[0]");
        if(listEntry == null || listEntry.getInteger("id") != 1) {
            throw new NbtApiException("The value is not what was expected! The Item-NBT-API may not work!");
        }
        ReadableNBT lastEntry = src.resolveOrCreateCompound("foo.bar.baz.somelist[-1]");
        if(lastEntry == null || lastEntry.getInteger("id") != 6) {
            throw new NbtApiException("The value is not what was expected! The Item-NBT-API may not work!");
        }
        if(src.resolveOrDefault("foo.bar.baz.intarray[1]", 0) != 2) {
            throw new NbtApiException("The value is not what was expected! The Item-NBT-API may not work!");
        }
        if(src.resolveOrNull("foo.bar.baz.intarray[2]", Integer.class) != (Integer)3) {
            throw new NbtApiException("The value is not what was expected! The Item-NBT-API may not work!");
        }
        if(!"World!".equals(src.resolveOrNull("foo.bar.baz.strlist[1]", String.class))) {
            throw new NbtApiException("The value is not what was expected! The Item-NBT-API may not work!");
        } 
    }

}
