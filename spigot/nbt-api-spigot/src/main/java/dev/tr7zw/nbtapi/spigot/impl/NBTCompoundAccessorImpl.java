package dev.tr7zw.nbtapi.spigot.impl;

import java.util.Set;
import java.util.UUID;

import dev.tr7zw.nbtapi.NBTCompound;
import dev.tr7zw.nbtapi.NBTCompoundAccessor;
import dev.tr7zw.nbtapi.NBTList;
import dev.tr7zw.nbtapi.NBTType;
import dev.tr7zw.nbtapi.Readable;

import org.bukkit.util.Vector;

public class NBTCompoundAccessorImpl implements NBTCompoundAccessor {

    @Override
    public String getString(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer getInteger(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double getDouble(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Byte getByte(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Short getShort(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long getLong(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Float getFloat(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] getByteArray(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getIntArray(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean getBoolean(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UUID getUUID(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> getKeys() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Readable getCompound(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTList<String> getStringList(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTList<Integer> getIntegerList(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTList<Float> getFloatList(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTList<Double> getDoubleList(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTList<Long> getLongList(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTType getListType(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTType getType(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasKey(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    public Vector getVector(String name) {
        Readable compound = getCompound(name);
        return new Vector(compound.getDouble("x"), compound.getDouble("y"), compound.getDouble("z"));
    }
}
