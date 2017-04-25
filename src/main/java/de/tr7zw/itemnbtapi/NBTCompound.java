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

    public void setInteger(String key, Integer value) {
        setItem(NBTReflectionUtil.setInt(getItem(), this, key, value));
    }

    public Integer getInteger(String key) {
        return NBTReflectionUtil.getInt(getItem(), this, key);
    }

    public void setDouble(String key, Double value) {
        setItem(NBTReflectionUtil.setDouble(getItem(), this, key, value));
    }

    public Double getDouble(String key) {
        return NBTReflectionUtil.getDouble(getItem(), this, key);
    }
    
    public void setByte(String key, Byte value) {
        setItem(NBTReflectionUtil.setByte(getItem(), this, key, value));
    }

    public Byte getByte(String key) {
        return NBTReflectionUtil.getByte(getItem(), this, key);
    }
    
    public void setShort(String key, Short value) {
        setItem(NBTReflectionUtil.setShort(getItem(), this, key, value));
    }

    public Short getShort(String key) {
        return NBTReflectionUtil.getShort(getItem(), this, key);
    }
    
    public void setLong(String key, Long value) {
        setItem(NBTReflectionUtil.setLong(getItem(), this, key, value));
    }

    public Long getLong(String key) {
        return NBTReflectionUtil.getLong(getItem(), this, key);
    }
    
    public void setFloat(String key, Float value) {
        setItem(NBTReflectionUtil.setFloat(getItem(), this, key, value));
    }

    public Float getFloat(String key) {
        return NBTReflectionUtil.getFloat(getItem(), this, key);
    }
    
    public void setByteArray(String key, byte[] value) {
        setItem(NBTReflectionUtil.setByteArray(getItem(), this, key, value));
    }

    public byte[] getByteArray(String key) {
        return NBTReflectionUtil.getByteArray(getItem(), this, key);
    }
    
    public void setIntArray(String key, int[] value) {
        setItem(NBTReflectionUtil.setIntArray(getItem(), this, key, value));
    }

    public int[] getIntArray(String key) {
        return NBTReflectionUtil.getIntArray(getItem(), this, key);
    }

    public void setBoolean(String key, Boolean value) {
        setItem(NBTReflectionUtil.setBoolean(getItem(), this, key, value));
    }

    public Boolean getBoolean(String key) {
        return NBTReflectionUtil.getBoolean(getItem(), this, key);
    }

    public void setObject(String key, Object value) {
        setItem(NBTReflectionUtil.setObject(getItem(), this, key, value));
    }

    public <T> T getObject(String key, Class<T> type) {
        return NBTReflectionUtil.getObject(getItem(), this, key, type);
    }

    public Boolean hasKey(String key) {
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
