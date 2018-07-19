package de.tr7zw.itemnbtapi;

import java.util.HashSet;
import java.util.Set;

public class NBTListCompound {

    private NBTList owner;
    private Object compound;

    protected NBTListCompound(NBTList parent, Object obj) {
        owner = parent;
        compound = obj;
    }

    public void setString(String key, String value) {
        if (value == null) {
            remove(key);
            return;
        }
        try {
            compound.getClass().getMethod("setString", String.class, String.class).invoke(compound, key, value);
            owner.save();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setInteger(String key, int value) {
        try {
            compound.getClass().getMethod("setInt", String.class, int.class).invoke(compound, key, value);
            owner.save();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getInteger(String value) {
        try {
            return (int) compound.getClass().getMethod("getInt", String.class).invoke(compound, value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public void setDouble(String key, double value) {
        try {
            compound.getClass().getMethod("setDouble", String.class, double.class).invoke(compound, key, value);
            owner.save();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public double getDouble(String key) {
        try {
            return (double) compound.getClass().getMethod("getDouble", String.class).invoke(compound, key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }


    public String getString(String key) {
        try {
            return (String) compound.getClass().getMethod("getString", String.class).invoke(compound, key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public boolean hasKey(String key) {
        try {
            return (boolean) compound.getClass().getMethod("hasKey", String.class).invoke(compound, key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public Set<String> getKeys() {
        try {
            return (Set<String>) ReflectionMethod.LISTCOMPOUND_GET_KEYS.run(compound);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new HashSet<>();
    }

    public void remove(String key) {
        try {
            compound.getClass().getMethod("remove", String.class).invoke(compound, key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
