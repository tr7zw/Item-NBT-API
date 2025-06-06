package de.tr7zw.nbtapi.plugin.tests.compounds;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class StreamTest implements Test {

    @Override
    public void test() throws Exception {
        ReadWriteNBT base = NBT.createNBTObject();
        base.getOrCreateCompound("sub").setString("hello", "world");
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        base.getOrCreateCompound("sub").writeCompound(outStream);
        byte[] data = outStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        ReadWriteNBT container = NBT.readNBT(inputStream);
        if (!container.toString().equals(base.getOrCreateCompound("sub").toString())) {
            throw new NbtApiException("Component content did not match! " + base.getCompound("sub") + " " + container);
        }
        ItemStack item = new ItemStack(Material.STICK);
        
        NBT.modify(item, nbt -> {
            nbt.writeCompound(outStream);
        });
    }

}
