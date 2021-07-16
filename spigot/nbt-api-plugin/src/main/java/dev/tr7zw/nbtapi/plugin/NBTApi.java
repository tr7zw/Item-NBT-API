package dev.tr7zw.nbtapi.plugin;

import org.bukkit.inventory.ItemStack;

import dev.tr7zw.nbtapi.handler.ItemHandler;
import dev.tr7zw.nbtapi.plugin.impl.NBTItemStack;
import dev.tr7zw.nbtapi.plugin.impl.NBTItemStackAccessor;

public class NBTApi implements ItemHandler<ItemStack, NBTItemStackAccessor, NBTItemStack>{

    @Override
    public NBTItemStackAccessor getItemStackAccessor(ItemStack item) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTItemStack getItemStack(ItemStack item) {
        // TODO Auto-generated method stub
        return null;
    }

}
