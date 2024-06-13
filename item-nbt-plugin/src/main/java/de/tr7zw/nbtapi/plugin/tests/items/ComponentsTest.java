package de.tr7zw.nbtapi.plugin.tests.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class ComponentsTest implements Test {

    @Override
    public void test() throws Exception {
        if(!MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_20_R4)) {
            return;
        }
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("test");
        item.setItemMeta(meta);
        String comp = NBT.modifyComponents(item, n -> {
           return n.toString();
        });
        if(!comp.contains("test")) {
            throw new NbtApiException("ReadComponent didn't work!");
        }
        NBT.modifyComponents(item, nbt -> {
            nbt.setString("minecraft:custom_name", "{\"extra\":[\"foobar\"],\"text\":\"\"}");
        });
        if(!item.getItemMeta().getDisplayName().equals("foobar")) {
            throw new NbtApiException("ModifyComponent didn't work!");
        }
    }

}
