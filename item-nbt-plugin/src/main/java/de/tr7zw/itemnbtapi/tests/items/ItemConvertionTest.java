package de.tr7zw.itemnbtapi.tests.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;

import de.tr7zw.itemnbtapi.NBTContainer;
import de.tr7zw.itemnbtapi.NBTItem;
import de.tr7zw.itemnbtapi.NbtApiException;
import de.tr7zw.itemnbtapi.tests.Test;

public class ItemConvertionTest implements Test{

	@Override
	public void test() throws Exception {
		ItemStack item = new ItemStack(Material.STONE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Lists.newArrayList("Firest Line", "Second Line"));
		item.setItemMeta(meta);
		String nbt = NBTItem.convertItemtoNBT(item).asNBTString();
		if(!nbt.contains("Firest Line") || !nbt.contains("Second Line"))
			throw new NbtApiException("The Item nbt '" + nbt + "' didn't contain the lore");
		ItemStack rebuild = NBTItem.convertNBTtoItem(new NBTContainer(nbt));
		if(!item.isSimilar(rebuild))
			throw new NbtApiException("Rebuilt item did not match the original!");
	}

}
