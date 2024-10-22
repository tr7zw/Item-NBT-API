package de.tr7zw.nbtapi.plugin.tests.compounds;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class EqualsTest implements Test {

    @Override
    public void test() throws Exception {
        ReadWriteNBT cont = NBT.createNBTObject();
        cont.setString("hello", "world");
        cont.setInteger("theAnswer", 42);
        cont.getOrCreateCompound("sub").setString("me", "too");
        cont.getStringList("somelist").addAll(Arrays.asList("a", "b", "c"));
        ItemStack item = new ItemStack(Material.STONE);
        NBT.modify(item, nbti -> {
            ReadWriteNBT customData = nbti.getOrCreateCompound("customData");
            // reverse order
            customData.getOrCreateCompound("sub").setString("me", "too");
            customData.setInteger("theAnswer", 42);
            customData.setString("hello", "world");
            customData.getStringList("somelist").addAll(Arrays.asList("a", "b", "c"));
            if (!customData.equals(cont)) {
                throw new NbtApiException("Compounds did not match! " + customData + " " + cont);
            }
        });

        // empty test

        if (!NBT.createNBTObject().equals(NBT.createNBTObject())) {
            throw new NbtApiException("Two empty tags did not match!");
        }

        // not equal test
        ReadWriteNBT part1 = NBT.createNBTObject();
        part1.setString("a", "a");
        part1.setString("b", "b");
        ReadWriteNBT part2 = NBT.createNBTObject();
        part2.setString("a", "a");
        part2.setString("b", "a");
        if (part1.equals(part2)) {
            throw new NbtApiException("Missmatched nbt did match!");
        }
    }

}
