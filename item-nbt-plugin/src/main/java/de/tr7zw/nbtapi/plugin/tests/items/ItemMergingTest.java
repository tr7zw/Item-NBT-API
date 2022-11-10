package de.tr7zw.nbtapi.plugin.tests.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.nbtapi.plugin.tests.Test;

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
        if (!new NBTItem(item).hasTag("test"))
            throw new NbtApiException("Couldn't merge custom NBT tag!");
        if ("New Author".equals(new NBTItem(item).getString("author")))
            throw new NbtApiException("Vanilla NBT tag was merged when shouldn't!");

        nbti.setString("test", "New Value");
        nbti.mergeNBT(item);
        if (!"New Author".equals(new NBTItem(item).getString("author"))
                || !"New Value".equals(new NBTItem(item).getString("test")))
            throw new NbtApiException("Couldn't replace NBT tag while merging!");

        ItemStack test = new ItemStack(Material.WRITTEN_BOOK);
        nbti.applyNBT(test);
        if (!item.isSimilar(test))
            throw new NbtApiException("ItemStacks didn't match! " + new NBTItem(item) + " " + new NBTItem(test));

        test = new ItemStack(Material.STONE);
        nbti.applyNBT(test);
        if (!nbti.hasTag("test"))
            throw new NbtApiException("Couldn't merge custom NBT tag!");
        if (!item.getItemMeta().getDisplayName().equals(test.getItemMeta().getDisplayName()))
            throw new NbtApiException("Couldn't merge vanilla NBT tag!");

        nbti.setBoolean("remove", true);
        nbti.clearCustomNBT();
        if (nbti.hasTag("remove"))
            throw new NbtApiException("Couldn't clear custom NBT tags!");
        if (!nbti.hasTag("author"))
            throw new NbtApiException("Vanilla tag was removed!");
    }

}
