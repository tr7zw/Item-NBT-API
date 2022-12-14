package de.tr7zw.nbtapi.plugin.tests.compounds;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class InterfaceTest implements Test {

    @Override
    public void test() throws Exception {
        ReadWriteNBT src = NBT.createNBTObject();
        src.setString("foo", "bar");
        ReadWriteNBT target = NBT.createNBTObject();
        target.mergeCompound(src);
        if (!"bar".equals(target.getString("foo"))) {
            throw new NbtApiException("Wasn't able to check the key! The Item-NBT-API may not work!");
        }
    }

}
