package de.tr7zw.nbtapi.plugin.tests.compounds;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTType;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class TypeTest implements Test {

    @Override
    public void test() throws Exception {
        NBTCompound comp = new NBTContainer();
        comp.setString("s", "test");
        comp.setInteger("i", 42);
        comp.addCompound("c");
        if (comp.getType("s") != NBTType.NBTTagString || comp.getType("i") != NBTType.NBTTagInt
                || comp.getType("c") != NBTType.NBTTagCompound)
            throw new NbtApiException("One parsed type did not match what it should have been!");
    }

}
