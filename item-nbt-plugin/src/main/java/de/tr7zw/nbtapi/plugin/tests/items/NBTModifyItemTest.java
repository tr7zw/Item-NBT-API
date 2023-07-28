package de.tr7zw.nbtapi.plugin.tests.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class NBTModifyItemTest implements Test {

    @Override
    public void test() throws Exception {
        ItemStack baseItem = new ItemStack(Material.STONE);
        NBT.modify(baseItem, nbti -> {
            nbti.setString("SomeKey", "SomeValue");
        });
        if (baseItem.equals(new ItemStack(Material.STONE)) || !new NBTItem(baseItem).hasTag("SomeKey")) {
            throw new NbtApiException("The item's where not equal!");
        }
        baseItem = new ItemStack(Material.STONE);
        // test using a "real" item
        baseItem = NBT.itemStackFromNBT(NBT.itemStackToNBT(baseItem));
        String inside = NBT.modify(baseItem, nbt -> {
            nbt.setString("SomeKey", "SomeValue");
            nbt.modifyMeta((r, meta) -> {
                meta.setDisplayName("Test");
            });
            nbt.getOrCreateCompound("sub").setInteger("val", 42);
            return nbt.getString("SomeKey");
        });
        String outside = NBT.get(baseItem, nbt -> nbt.getString("SomeKey"));
        if (baseItem == null) {
            throw new NbtApiException("Base item was null!");
        }
        if (!new NBTItem(baseItem).hasTag("SomeKey")) {
            throw new NbtApiException("The data was not applied!");
        }
        if (new NBTItem(baseItem).getOrCreateCompound("sub").getInteger("val") != 42) {
            throw new NbtApiException("The sub value was not applied!");
        }
        if (!"SomeValue".equals(inside)) {
            throw new NbtApiException("Inside returned the wrong value!");
        }
        if (!"SomeValue".equals(outside)) {
            throw new NbtApiException("Outside returned the wrong value!");
        }
        if (!"Test".equals(baseItem.getItemMeta().getDisplayName())) {
            throw new NbtApiException("The display name was not applied!");
        }
        baseItem = new ItemStack(Material.STONE);
        // test using a "fake" item
        inside = NBT.modify(baseItem, nbt -> {
            nbt.setString("SomeKey", "SomeValue");
            nbt.modifyMeta((r, meta) -> {
                meta.setDisplayName("Test");
            });
            nbt.getOrCreateCompound("sub").setInteger("val", 42);
            return nbt.getString("SomeKey");
        });
        outside = NBT.get(baseItem, nbt -> nbt.getString("SomeKey"));
        if (!new NBTItem(baseItem).hasTag("SomeKey")) {
            throw new NbtApiException("The data was not applied!");
        }
        if (new NBTItem(baseItem).getOrCreateCompound("sub").getInteger("val") != 42) {
            throw new NbtApiException("The sub value was not applied!");
        }
        if (!"SomeValue".equals(inside)) {
            throw new NbtApiException("Inside returned the wrong value!");
        }
        if (!"SomeValue".equals(outside)) {
            throw new NbtApiException("Outside returned the wrong value!");
        }
        if (!"Test".equals(baseItem.getItemMeta().getDisplayName())) {
            throw new NbtApiException("The display name was not applied!");
        }
        // other ordering
        baseItem = new ItemStack(Material.STONE);
        NBT.modify(baseItem, nbti -> {
            nbti.setString("a", "SomeValue");
        });
        NBT.modify(baseItem, nbt -> {
            nbt.modifyMeta((r, meta) -> {
                meta.setDisplayName("Test");
            });
            nbt.setInteger("b", 12);
        });
        if (!new NBTItem(baseItem).hasTag("a") || !new NBTItem(baseItem).hasTag("b")
                || !"Test".equals(baseItem.getItemMeta().getDisplayName())) {
            throw new NbtApiException("The data was not applied: " + new NBTItem(baseItem).toString());
        }
    }

}
