package de.tr7zw.changeme.nbtapi;

import java.util.Set;

import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;


public class NBTCompound {

    private String compundName;
    private NBTCompound parent;

    protected NBTCompound(NBTCompound owner, String name) {
        this.compundName = name;
        this.parent = owner;
    }

    public String getName() {
        return compundName;
    }

    public Object getCompound() {
        return parent.getCompound();
    }

    protected void setCompound(Object compound) {
        parent.setCompound(compound);
    }

    public NBTCompound getParent() {
        return parent;
    }

    public void mergeCompound(NBTCompound comp){
        NBTReflectionUtil.addOtherNBTCompound(this, comp);
    }
    
    public void setString(String key, String value) {
        NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_STRING, key, value);
    }

    public String getString(String key) {
        return (String) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_STRING, key);
    }

    protected String getContent(String key) {
        return NBTReflectionUtil.getContent(this, key);
    }

    public void setInteger(String key, Integer value) {
        NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_INT, key, value);
    }

    public Integer getInteger(String key) {
        return (Integer) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_INT, key);
    }

    public void setDouble(String key, Double value) {
        NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_DOUBLE, key, value);
    }

    public Double getDouble(String key) {
        return (Double) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_DOUBLE, key);
    }

    public void setByte(String key, Byte value) {
        NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_BYTE, key, value);
    }

    public Byte getByte(String key) {
        return (Byte) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_BYTE, key);
    }

    public void setShort(String key, Short value) {
        NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_SHORT, key, value);
    }

    public Short getShort(String key) {
        return (Short) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_SHORT, key);
    }

    public void setLong(String key, Long value) {
        NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_LONG, key, value);
    }

    public Long getLong(String key) {
        return (Long) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_LONG, key);
    }

    public void setFloat(String key, Float value) {
        NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_FLOAT, key, value);
    }

    public Float getFloat(String key) {
        return (Float) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_FLOAT, key);
    }

    public void setByteArray(String key, byte[] value) {
        NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_BYTEARRAY, key, value);
    }

    public byte[] getByteArray(String key) {
        return (byte[]) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_BYTEARRAY, key);
    }

    public void setIntArray(String key, int[] value) {
        NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_INTARRAY, key, value);
    }

    public int[] getIntArray(String key) {
        return (int[]) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_INTARRAY, key);
    }

    public void setBoolean(String key, Boolean value) {
        NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_BOOLEAN, key, value);
    }

    protected void set(String key, Object val) {
        NBTReflectionUtil.set(this, key, val);
    }

    public Boolean getBoolean(String key) {
        return (Boolean) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_BOOLEAN, key);
    }

    public void setObject(String key, Object value) {
        NBTReflectionUtil.setObject(this, key, value);
    }

    public <T> T getObject(String key, Class<T> type) {
        return NBTReflectionUtil.getObject(this, key, type);
    }
    
    public Boolean hasKey(String key) {
        Boolean b =  (Boolean) NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_HAS_KEY, key);
        if(b == null)return false;
        return b;
    }

    public void removeKey(String key) {
        NBTReflectionUtil.remove(this, key);
    }

    public Set<String> getKeys() {
        return NBTReflectionUtil.getKeys(this);
    }

    public NBTCompound addCompound(String name) {
        if(getType(name) == NBTType.NBTTagCompound)return getCompound(name);
        NBTReflectionUtil.addNBTTagCompound(this, name);
        return getCompound(name);
    }

    public NBTCompound getCompound(String name) {
        NBTCompound next = new NBTCompound(this, name);
        if (NBTReflectionUtil.valideCompound(next)) return next;
        return null;
    }

    public NBTList<String> getStringList(String name) {
    	return NBTReflectionUtil.getList(this, name, NBTType.NBTTagString, String.class);
    }
    
    public NBTList<Integer> getIntegerList(String name) {
    	return NBTReflectionUtil.getList(this, name, NBTType.NBTTagInt, Integer.class);
    }
    
    public NBTCompoundList getCompoundList(String name) {
    	return (NBTCompoundList) NBTReflectionUtil.getList(this, name, NBTType.NBTTagCompound, NBTListCompound.class);
    }

    public NBTType getType(String name) {
        if (MinecraftVersion.getVersion() == MinecraftVersion.MC1_7_R4) return null;
        Object o = NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_TYPE, name);
        if(o == null)return null;
        return NBTType.valueOf((byte) o);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String key : getKeys()) {
            result.append(toString(key));
        }
        return result.toString();
    }

    public String toString(String key) {
        StringBuilder result = new StringBuilder();
        NBTCompound compound = this;
        while (compound.getParent() != null) {
            result.append("   ");
            compound = compound.getParent();
        }
        if (this.getType(key) == NBTType.NBTTagCompound) {
            return this.getCompound(key).toString();
        } else {
            return result + "-" + key + ": " + getContent(key) + System.lineSeparator();
        }
    }
    
    public String asNBTString(){
        Object comp = NBTReflectionUtil.gettoCompount(getCompound(), this);
        if(comp == null)return "{}";
        return comp.toString();
    }

}
