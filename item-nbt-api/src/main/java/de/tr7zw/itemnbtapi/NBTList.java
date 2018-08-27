package de.tr7zw.itemnbtapi;

public class NBTList {

    private String listName;
    private NBTCompound parent;
    private NBTType type;
    private Object listObject;

    protected NBTList(NBTCompound owner, String name, NBTType type, Object list) {
        parent = owner;
        listName = name;
        this.type = type;
        this.listObject = list;
        if (!(type == NBTType.NBTTagString || type == NBTType.NBTTagCompound)) {
            System.err.println("List types != String/Compound are currently not implemented!");
        }
    }

    protected void save() {
        parent.set(listName, listObject);
    }

    public NBTListCompound addCompound() {
        if (type != NBTType.NBTTagCompound) {
            new Throwable("Using Compound method on a non Compound list!").printStackTrace();
            return null;
        }
        try {
            Object compound = ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance();
            ReflectionMethod.LIST_ADD.run(listObject, compound);
            return new NBTListCompound(this, compound);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public NBTListCompound getCompound(int id) {
        if (type != NBTType.NBTTagCompound) {
            new Throwable("Using Compound method on a non Compound list!").printStackTrace();
            return null;
        }
        try {
            Object compound = ReflectionMethod.LIST_GET.run(listObject, id);
            return new NBTListCompound(this, compound);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getString(int i) {
        if (type != NBTType.NBTTagString) {
            new Throwable("Using String method on a non String list!").printStackTrace();
            return null;
        }
        try {
            return (String) ReflectionMethod.LIST_GET_STRING.run(listObject, i);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void addString(String s) {
        if (type != NBTType.NBTTagString) {
            new Throwable("Using String method on a non String list!").printStackTrace();
            return;
        }
        try {
            ReflectionMethod.LIST_ADD.run(listObject, ClassWrapper.NMS_NBTTAGSTRING.getClazz().getConstructor(String.class).newInstance(s));
            save();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setString(int i, String s) {
        if (type != NBTType.NBTTagString) {
            new Throwable("Using String method on a non String list!").printStackTrace();
            return;
        }
        try {
            ReflectionMethod.LIST_SET.run(listObject, i, ClassWrapper.NMS_NBTTAGSTRING.getClazz().getConstructor(String.class).newInstance(s));
            save();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void remove(int i) {
        try {
            ReflectionMethod.LIST_REMOVE_KEY.run(listObject, i);
            save();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int size() {
        try {
            return (int) ReflectionMethod.LIST_SIZE.run(listObject);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public NBTType getType() {
        return type;
    }

}
