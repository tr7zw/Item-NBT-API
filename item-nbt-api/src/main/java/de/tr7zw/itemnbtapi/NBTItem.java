package de.tr7zw.itemnbtapi;

import org.bukkit.inventory.ItemStack;

public class NBTItem extends NBTCompound {

    private ItemStack bukkitItem;

    public NBTItem(ItemStack item) {
        super(null, null);
        bukkitItem = item.clone();
    }

    protected Object getCompound() {
        return NBTReflectionUtil.getItemRootNBTTagCompound(NBTReflectionUtil.getNMSItemStack(bukkitItem));
    }

    protected void setCompound(Object compound) {
        bukkitItem = NBTReflectionUtil.getBukkitItemStack(NBTReflectionUtil.setNBTTag(compound, NBTReflectionUtil.getNMSItemStack(bukkitItem)));
    }

    public ItemStack getItem() {
        return bukkitItem;
    }

    protected void setItem(ItemStack item) {
        bukkitItem = item;
    }

}
