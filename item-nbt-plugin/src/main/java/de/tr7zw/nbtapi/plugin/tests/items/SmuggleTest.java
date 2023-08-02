package de.tr7zw.nbtapi.plugin.tests.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBTList;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBTList;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class SmuggleTest implements Test {

    @Override
    public void test() throws Exception {
        ItemStack item = new ItemStack(Material.STONE);
        try {
            ReadWriteNBTList<Long> list = NBT.modify(item, nbt -> {
                nbt.getLongList("test").add(123l);
                return nbt.getLongList("test");
            });
            throw new NbtApiException("Managed to smuggle nbt out of the context: " + list);
        } catch (Exception e) {
            // this is the excpected behavior
        }
        try {
            ReadableNBTList<Long> list = NBT.get(item, nbt -> {
                return nbt.getLongList("test");
            });
            throw new NbtApiException("Managed to smuggle nbt out of the context: " + list);
        } catch (Exception e) {
            // this is the excpected behavior
        }

    }

}
