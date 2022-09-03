package de.tr7zw.nbtapi.plugin.tests.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class ItemStackConversionTest implements Test {

    @Override
    public void test() throws Exception {
        ItemStack[] src = new ItemStack[] { new ItemStack(Material.STONE), new ItemStack(Material.STICK),
                new ItemStack(Material.AIR), new ItemStack(Material.STONE) };
        NBTCompound comp = NBTItem.convertItemArraytoNBT(src);
        ItemStack[] recreated = NBTItem.convertNBTtoItemArray(comp);
        if(src.length != recreated.length) {
            throw new NbtApiException("Size did not match!");
        }
        for(int i = 0; i < src.length; i++) {
            if(!src[i].isSimilar(recreated[i])) {
                throw new NbtApiException("Rebuilt item did not match the original!");
            }
        }
    }

}
