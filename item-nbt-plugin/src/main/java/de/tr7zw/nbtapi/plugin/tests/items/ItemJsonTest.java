package de.tr7zw.nbtapi.plugin.tests.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.gson.JsonElement;

import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.utils.NBTJsonUtil;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class ItemJsonTest implements Test {

    @Override
    public void test() throws Exception {
        ItemStack item = new ItemStack(Material.STONE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("test");
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        JsonElement elem = NBTJsonUtil.itemStackToJson(item);
        if (elem == null) {
            throw new NbtApiException("Getting the Json didn't work correctly! " + item);
        }
    }

}
