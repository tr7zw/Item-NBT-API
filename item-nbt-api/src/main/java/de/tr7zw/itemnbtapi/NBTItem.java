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
    
    public static NBTContainer convertItemtoNBT(ItemStack item){
        return NBTReflectionUtil.convertNMSItemtoNBTCompound(NBTReflectionUtil.getNMSItemStack(item));
    }
    
    public static ItemStack convertNBTtoItem(NBTCompound comp){
        return NBTReflectionUtil.getBukkitItemStack(NBTReflectionUtil.convertNBTCompoundtoNMSItem(comp));
    }

}
