package de.tr7zw.nbtapi.plugin.tests.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class EmptyItemTest implements Test {

    @Override
    public void test() throws Exception {
        ItemStack item = new ItemStack(Material.STONE);
        NBTItem nbti = new NBTItem(item);
        if (nbti.hasNBTData()) {
            throw new NbtApiException("Item reported to have data");
        }
        if (nbti.getBoolean("test") == null || nbti.getString("test") == null || nbti.getFloatList("test") == null) {
            throw new NbtApiException("Getters return null instead of the default value");
        }
        if (!nbti.getKeys().isEmpty()) {
            throw new NbtApiException("getKeys() returned keys!");
        }
        nbti.setString("test", "test");
        if (!nbti.hasNBTData()) {
            throw new NbtApiException("Item reported to have no data");
        }
        nbti.removeKey("test");
        if (nbti.hasNBTData()) {
            throw new NbtApiException("Item reported to have data after deletion");
        }

        try {
            Material barrel = Material.valueOf("BARREL");
            item = new ItemStack(barrel);
            nbti = new NBTItem(item);
        } catch (IllegalArgumentException ex) {
            // old version
        }

        item = new ItemStack(Material.STONE);
        NBT.modify(item, nbt -> {
            nbt.setString("test", "test");
        });
        NBT.get(item, nbt -> {
            if (!nbt.hasNBTData()) {
                throw new NbtApiException("Item reported to have no data");
            }
            return nbt.hasNBTData();
        });
        NBT.modify(item, nbt -> {
            nbt.removeKey("test");
        });
        NBT.get(item, nbt -> {
            if (nbt.hasNBTData()) {
                throw new NbtApiException("Item reported to have data after deletion");
            }
            return nbt.hasNBTData();
        });

        ItemStack testItem = new ItemStack(Material.STONE);
        NBTItem nbt = new NBTItem(testItem, true);
        nbt.removeKey("not there");
        nbt.setBoolean("test", true);
    }

}
