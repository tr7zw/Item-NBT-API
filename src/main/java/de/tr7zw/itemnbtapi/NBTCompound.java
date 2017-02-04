package de.tr7zw.itemnbtapi;

import java.util.Set;

import org.bukkit.inventory.ItemStack;


public class NBTCompound {

    private String compundname;
    private NBTCompound parent;

    protected NBTCompound(NBTCompound owner, String name) {
        this.compundname = name;
        this.parent = owner;
    }

    public String getName(){
        return compundname;
    }

    public ItemStack getItem() {
        return parent.getItem();
    }

    public NBTCompound getParent(){
        return parent;
    }

    protected void setItem(ItemStack item){
        parent.setItem(item);
    }

    public void setString(String key, String value) {
        setItem(NBTReflectionUtil.setString(getItem(), this, key, value));
    }

    public String getString(String key) {
        return NBTReflectionUtil.getString(getItem(), this, key);
    }

    public void setInteger(String key, int value) {
        setItem(NBTReflectionUtil.setInt(getItem(), this, key, value));
    }

    public Integer getInteger(String key) {
        return NBTReflectionUtil.getInt(getItem(), this, key);
    }

    public void setDouble(String key, double value) {
        setItem(NBTReflectionUtil.setDouble(getItem(), this, key, value));
    }

    public double getDouble(String key) {
        return NBTReflectionUtil.getDouble(getItem(), this, key);
    }

    public void setBoolean(String key, boolean value) {
        setItem(NBTReflectionUtil.setBoolean(getItem(), this, key, value));
    }

    public boolean getBoolean(String key) {
        return NBTReflectionUtil.getBoolean(getItem(), this, key);
    }

    public void setObject(String key, Object value) {
        setItem(NBTReflectionUtil.setObject(getItem(), this, key, value));
    }

    public <T> T getObject(String key, Class<T> type) {
        return NBTReflectionUtil.getObject(getItem(), this, key, type);
    }

    public boolean hasKey(String key) {
        return NBTReflectionUtil.hasKey(getItem(), this, key);
    }

    public void removeKey(String key){
        setItem(NBTReflectionUtil.remove(getItem(), this, key));
    }

    public Set<String> getKeys(){
        return NBTReflectionUtil.getKeys(getItem(), this);
    }

    public NBTCompound addCompound(String name){
        setItem(NBTReflectionUtil.addNBTTagCompound(getItem(), this, name));
        return getCompound(name);
    }

    public NBTCompound getCompound(String name){
        NBTCompound next = new NBTCompound(this, name);
        if(NBTReflectionUtil.valideCompound(getItem(), next))return next;
        return null;
    }

}
