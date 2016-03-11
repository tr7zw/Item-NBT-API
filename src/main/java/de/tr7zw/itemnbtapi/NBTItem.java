package de.tr7zw.itemnbtapi;

import org.bukkit.inventory.ItemStack;

public class NBTItem {

    private ItemStack bukkitItem;
    
    public NBTItem(ItemStack item) {
        bukkitItem = item.clone();
    }

    public ItemStack getItem() {
        return bukkitItem;
    }

    public void setString(String key, String value) {
        bukkitItem = NBTReflectionUtil.setString(bukkitItem, key, value);
    }

    public String getString(String key) {
        return NBTReflectionUtil.getString(bukkitItem, key);
    }

    public void setInteger(String key, int value) {
        bukkitItem = NBTReflectionUtil.setInt(bukkitItem, key, value);
    }

    public Integer getInteger(String key) {
        return NBTReflectionUtil.getInt(bukkitItem, key);
    }

    public void setDouble(String key, double value) {
        bukkitItem = NBTReflectionUtil.setDouble(bukkitItem, key, value);
    }

    public double getDouble(String key) {
        return NBTReflectionUtil.getDouble(bukkitItem, key);
    }

    public void setBoolean(String key, boolean value) {
        bukkitItem = NBTReflectionUtil.setBoolean(bukkitItem, key, value);
    }

    public boolean getBoolean(String key) {
        return NBTReflectionUtil.getBoolean(bukkitItem, key);
    }

    public boolean hasKey(String key) {
        return NBTReflectionUtil.hasKey(bukkitItem, key);
    }

}
