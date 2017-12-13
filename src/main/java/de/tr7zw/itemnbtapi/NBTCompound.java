package de.tr7zw.itemnbtapi;

import java.util.Set;


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

    protected Object getCompound() {
        return parent.getCompound();
    }
    
    protected void setCompound(Object comp) {
        parent.setCompound(comp);
    }

    public NBTCompound getParent(){
        return parent;
    }

    public void setString(String key, String value) {
        NBTReflectionUtil.setString(this, key, value);
    }

    public String getString(String key) {
        return NBTReflectionUtil.getString(this, key);
    }
    
    protected String getContent(String key) {
        return NBTReflectionUtil.getContent(this, key);
    }


    public void setInteger(String key, Integer value) {
        NBTReflectionUtil.setInt(this, key, value);
    }

    public Integer getInteger(String key) {
        return NBTReflectionUtil.getInt(this, key);
    }

    public void setDouble(String key, Double value) {
        NBTReflectionUtil.setDouble(this, key, value);
    }

    public Double getDouble(String key) {
        return NBTReflectionUtil.getDouble(this, key);
    }
    
    public void setByte(String key, Byte value) {
        NBTReflectionUtil.setByte(this, key, value);
    }

    public Byte getByte(String key) {
        return NBTReflectionUtil.getByte(this, key);
    }
    
    public void setShort(String key, Short value) {
        NBTReflectionUtil.setShort(this, key, value);
    }

    public Short getShort(String key) {
        return NBTReflectionUtil.getShort(this, key);
    }
    
    public void setLong(String key, Long value) {
        NBTReflectionUtil.setLong(this, key, value);
    }

    public Long getLong(String key) {
        return NBTReflectionUtil.getLong(this, key);
    }
    
    public void setFloat(String key, Float value) {
        NBTReflectionUtil.setFloat(this, key, value);
    }

    public Float getFloat(String key) {
        return NBTReflectionUtil.getFloat(this, key);
    }
    
    public void setByteArray(String key, byte[] value) {
        NBTReflectionUtil.setByteArray(this, key, value);
    }

    public byte[] getByteArray(String key) {
        return NBTReflectionUtil.getByteArray( this, key);
    }
    
    public void setIntArray(String key, int[] value) {
        NBTReflectionUtil.setIntArray(this, key, value);
    }

    public int[] getIntArray(String key) {
        return NBTReflectionUtil.getIntArray(this, key);
    }

    public void setBoolean(String key, Boolean value) {
        NBTReflectionUtil.setBoolean( this, key, value);
    }
    
    protected void set(String key, Object val){
    	NBTReflectionUtil.set( this, key, val);
    }

    public Boolean getBoolean(String key) {
        return NBTReflectionUtil.getBoolean( this, key);
    }

    public void setObject(String key, Object value) {
        NBTReflectionUtil.setObject( this, key, value);
    }

    public <T> T getObject(String key, Class<T> type) {
        return NBTReflectionUtil.getObject( this, key, type);
    }

    public Boolean hasKey(String key) {
        return NBTReflectionUtil.hasKey( this, key);
    }

    public void removeKey(String key){
        NBTReflectionUtil.remove( this, key);
    }

    public Set<String> getKeys(){
        return NBTReflectionUtil.getKeys( this);
    }

    public NBTCompound addCompound(String name){
        NBTReflectionUtil.addNBTTagCompound( this, name);
        return getCompound(name);
    }

    public NBTCompound getCompound(String name){
        NBTCompound next = new NBTCompound(this, name);
        if(NBTReflectionUtil.valideCompound( next))return next;
        return null;
    }
    
    public NBTList getList(String name, NBTType type){
    	return NBTReflectionUtil.getList(this, name, type);
    }
    
    public NBTType getType(String name){
        if(MinecraftVersion.getVersion() == MinecraftVersion.MC1_7_R4)return NBTType.NBTTagEnd;
    	return NBTType.valueOf(NBTReflectionUtil.getType( this, name));
    }
    
    @Override
    public String toString(){
        String str = "";
        for(String k : getKeys()){
            str += toString(k);
        }
        return str;
    }
    
    public String toString(String key){
        String s = "";
        NBTCompound c = this;
        while(c.getParent() != null){
            s += "   ";
            c = c.getParent();
        }
        if(this.getType(key) == NBTType.NBTTagCompound){
            return this.getCompound(key).toString();
        }else{
            return s + "-" + key + ": " + getContent(key) + System.lineSeparator();
        }
    }
    
    public String asNBTString(){
        return getCompound().toString();
    }

}
