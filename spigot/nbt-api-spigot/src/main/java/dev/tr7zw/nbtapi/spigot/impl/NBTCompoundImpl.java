package dev.tr7zw.nbtapi.spigot.impl;

import java.util.UUID;

import dev.tr7zw.nbtapi.NBTCompound;
import dev.tr7zw.nbtapi.Readable;

import org.bukkit.util.Vector;

public class NBTCompoundImpl extends NBTCompoundAccessorImpl implements NBTCompound{

    @Override
    public void mergeCompound(Readable comp) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public NBTCompound setString(String key, String value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTCompound setInteger(String key, Integer value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTCompound setDouble(String key, Double value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTCompound setByte(String key, Byte value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTCompound setShort(String key, Short value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTCompound setLong(String key, Long value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTCompound setFloat(String key, Float value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTCompound setByteArray(String key, byte[] value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTCompound setIntArray(String key, int[] value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTCompound setBoolean(String key, Boolean value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTCompound setUUID(String key, UUID value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTCompound removeKey(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTCompound getOrCreateCompound(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    public NBTCompound setVector(String name, Vector vector) {
        NBTCompound compound = getOrCreateCompound(name);
        compound.setDouble("x", vector.getX());
        compound.setDouble("y", vector.getY());
        compound.setDouble("z", vector.getZ());
        return this;
    }
}
