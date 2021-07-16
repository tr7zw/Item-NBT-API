package dev.tr7zw.nbtapi.plugin.tests.legacy.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;

import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import dev.tr7zw.nbtapi.NBTApiException;
import dev.tr7zw.nbtapi.plugin.tests.Test;

public class ItemConvertionTest implements Test {

	@Override
	public void test() throws Exception {
		ItemStack item = new ItemStack(Material.STONE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Lists.newArrayList("Firest Line", "Second Line"));
		item.setItemMeta(meta);
		String nbt = NBTItem.convertItemtoNBT(item).toString();
		if (!nbt.contains("Firest Line") || !nbt.contains("Second Line"))
			throw new NBTApiException("The Item nbt '" + nbt + "' didn't contain the lore");
		ItemStack rebuild = NBTItem.convertNBTtoItem(new NBTContainer(nbt));
		if (!item.isSimilar(rebuild))
			throw new NBTApiException("Rebuilt item did not match the original!");
		
		NBTContainer cont = new NBTContainer();
		cont.setItemStack("testItem", item);
		if(!cont.getItemStack("testItem").isSimilar(item))
			throw new NBTApiException("Rebuilt item did not match the original!");
	}

}
