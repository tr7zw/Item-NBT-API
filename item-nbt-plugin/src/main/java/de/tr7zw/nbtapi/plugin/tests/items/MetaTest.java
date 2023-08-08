package de.tr7zw.nbtapi.plugin.tests.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class MetaTest implements Test {

    @Override
    public void test() throws Exception {
        ItemStack item = new ItemStack(Material.STONE);
        NBT.modify(item, nbt -> {
            nbt.setInteger("HideFlags", 1);
            nbt.modifyMeta((rnbt, meta) -> {
                if (!meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS) || rnbt.getInteger("HideFlags") != 1) {
                    throw new NbtApiException("The meta did not correctly update or read! " + rnbt);
                }
            });
        });

    }

}
