package de.tr7zw.itemnbtapi;

import org.bukkit.inventory.ItemStack;

public class NBTItem extends NBTCompound {

    private ItemStack bukkitItem;

    public NBTItem(ItemStack item) {
        super(null, null);
        bukkitItem = item.clone();
    }

    protected Object getCompound() {
        return NBTReflectionUtil.getItemRootNBTTagCompound(ReflectionMethod.ITEMSTACK_NMSCOPY.run(null, bukkitItem));
    }

    protected void setCompound(Object compound) {
        Object stack = ReflectionMethod.ITEMSTACK_NMSCOPY.run(null, bukkitItem);
        ReflectionMethod.ITEMSTACK_SET_TAG.run(stack, compound);
        bukkitItem = NBTReflectionUtil.getBukkitItemStack(stack);
    }

    public ItemStack getItem() {
        return bukkitItem;
    }

    protected void setItem(ItemStack item) {
        bukkitItem = item;
    }
    
    public static NBTContainer convertItemtoNBT(ItemStack item){
        return NBTReflectionUtil.convertNMSItemtoNBTCompound(ReflectionMethod.ITEMSTACK_NMSCOPY.run(null, item));
    }
    
    public static ItemStack convertNBTtoItem(NBTCompound comp){
        return NBTReflectionUtil.getBukkitItemStack(NBTReflectionUtil.convertNBTCompoundtoNMSItem(comp));
    }

}
