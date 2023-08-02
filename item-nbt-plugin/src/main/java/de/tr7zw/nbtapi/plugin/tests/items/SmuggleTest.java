package de.tr7zw.nbtapi.plugin.tests.items;

import java.util.concurrent.atomic.AtomicReference;

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
        Exception error = null;
        try {
            ReadWriteNBTList<Long> list = NBT.modify(item, nbt -> {
                nbt.getLongList("test").add(123l);
                return nbt.getLongList("test");
            });
            error = new NbtApiException("Managed to smuggle nbt out of the context: " + list);
        } catch (Exception e) {
            // this is the excpected behavior
        }
        if (error != null) {
            throw error;
        }
        try {
            ReadableNBTList<Long> list = NBT.get(item, nbt -> {
                return nbt.getLongList("test");
            });
            error = new NbtApiException("Managed to smuggle nbt out of the context: " + list);
        } catch (Exception e) {
            // this is the excpected behavior
        }
        if (error != null) {
            throw error;
        }
        AtomicReference<ReadableNBTList<Long>> list = new AtomicReference<>();
        try {
            NBT.get(item, nbt -> {
                list.set(nbt.getLongList("test")); // smuggling the nbt out of the context
                return null;
            });
            if (list.get().size() == 1) {
                error = new NbtApiException("Managed to smuggle nbt out of the context: " + list);
            }
            error = new NbtApiException("Managed to smuggle nbt out of the context: " + list);
        } catch (Exception e) {
            // this is the excpected behavior
        }
        if (error != null) {
            throw error;
        }
    }

}
