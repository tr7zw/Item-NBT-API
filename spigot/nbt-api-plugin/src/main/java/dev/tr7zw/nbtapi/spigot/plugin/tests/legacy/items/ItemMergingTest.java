package dev.tr7zw.nbtapi.spigot.plugin.tests.legacy.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import de.tr7zw.nbtapi.NBTItem;
import dev.tr7zw.nbtapi.NBTApiException;
import dev.tr7zw.nbtapi.spigot.plugin.tests.Test;

public class ItemMergingTest implements Test {

	@Override
	public void test() throws Exception {
		ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bookMeta = (BookMeta) item.getItemMeta();
		bookMeta.setAuthor("Author");
		bookMeta.setDisplayName("name");
		item.setItemMeta(bookMeta);

		NBTItem nbti = new NBTItem(item);
		nbti.setString("author", "New Author");
		nbti.setString("test", "value");

		nbti.mergeCustomNBT(item);
		if (!new NBTItem(item).hasKey("test"))
			throw new NBTApiException("Couldn't merge custom NBT tag!");
		if ("New Author".equals(new NBTItem(item).getString("author")))
			throw new NBTApiException("Vanilla NBT tag was merged when shouldn't!");

		nbti.setString("test", "New Value");
		nbti.mergeNBT(item);
		if (!"New Author".equals(new NBTItem(item).getString("author")) || !"New Value".equals(new NBTItem(item).getString("test")))
			throw new NBTApiException("Couldn't replace NBT tag while merging!");

		ItemStack test = new ItemStack(Material.WRITTEN_BOOK);
		nbti.applyNBT(test);
		if (!item.isSimilar(test))
			throw new NBTApiException("ItemStacks didn't match! " + new NBTItem(item) + " " + new NBTItem(test));

		test = new ItemStack(Material.STONE);
		nbti.applyNBT(test);
		if (!nbti.hasKey("test"))
			throw new NBTApiException("Couldn't merge custom NBT tag!");
		if (!item.getItemMeta().getDisplayName().equals(test.getItemMeta().getDisplayName()))
			throw new NBTApiException("Couldn't merge vanilla NBT tag!");

		nbti.setBoolean("remove", true);
		nbti.clearCustomNBT();
		if (nbti.hasKey("remove"))
			throw new NBTApiException("Couldn't clear custom NBT tags!");
		if (!nbti.hasKey("author"))
			throw new NBTApiException("Vanilla tag was removed!");
	}

}