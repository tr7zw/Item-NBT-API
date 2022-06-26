package de.tr7zw.nbtapi.plugin.tests.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class DirectApplyTest implements Test{

	@Override
	public void test() throws Exception {
		ItemStack baseItem = new ItemStack(Material.STONE);
		NBTItem nbti = new NBTItem(baseItem, true);
		nbti.setString("SomeKey", "SomeValue");
		if(!baseItem.equals(nbti.getItem()) || !new NBTItem(baseItem).hasTag("SomeKey")) {
			throw new NbtApiException("The item's where not equal!");
		}
	}

}
