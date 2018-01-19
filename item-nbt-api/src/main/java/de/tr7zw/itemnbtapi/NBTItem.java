package de.tr7zw.itemnbtapi;

import org.bukkit.inventory.ItemStack;

public class NBTItem extends NBTCompound {

    private ItemStack bukkitItem;

    public NBTItem(ItemStack item) {
        super(null, null);
        bukkitItem = item.clone();
    }

    protected Object getCompound() {
        if(bukkitItem != null){
            Object itemStack = NBTReflectionUtil.getNMSItemStack(bukkitItem);
             if(itemStack != null){
                 return NBTReflectionUtil.getItemRootNBTTagCompound(itemStack);
             }
        }
        return null;
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
        Object itemStack = NBTReflectionUtil.getNMSItemStack(item);
        if(itemStack != null){
            return NBTReflectionUtil.convertNMSItemtoNBTCompound(itemStack);
        }
        return null;
    }

    public static ItemStack convertNBTtoItem(NBTCompound comp){
        return NBTReflectionUtil.getBukkitItemStack(NBTReflectionUtil.convertNBTCompoundtoNMSItem(comp));
    }

}
