package de.tr7zw.itemnbtapi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.Stack;

import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;

import de.tr7zw.itemnbtapi.utils.GsonWrapper;
import de.tr7zw.itemnbtapi.utils.MinecraftVersion;

public class NBTReflectionUtil {  

    public static Object getNMSEntity(Entity entity) {
        try {
            return ReflectionMethod.CRAFT_ENTITY_GET_HANDLE.run(ClassWrapper.CRAFT_ENTITY.getClazz().cast(entity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object readNBTFile(FileInputStream stream) {
        try {
            return ReflectionMethod.NBTFILE_READ.run(null, stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object saveNBTFile(Object nbt, FileOutputStream stream) {
        try {
            return ReflectionMethod.NBTFILE_WRITE.run(null, nbt, stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({"unchecked"})
    public static Object getItemRootNBTTagCompound(Object nmsitem) {
        @SuppressWarnings("rawtypes")
        Class clazz = nmsitem.getClass();
        Method method;
        try {
            method = clazz.getMethod("getTag");
            Object answer = method.invoke(nmsitem);
            return answer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({"unchecked"})
    public static Object convertNBTCompoundtoNMSItem(NBTCompound nbtcompound) {
        @SuppressWarnings("rawtypes")
        Class clazz = ClassWrapper.NMS_ITEMSTACK.getClazz();
        try {
            Object nmsstack = clazz.getConstructor(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()).newInstance(nbtcompound.getCompound());
            return nmsstack;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({"unchecked"})
    public static NBTContainer convertNMSItemtoNBTCompound(Object nmsitem) {
        @SuppressWarnings("rawtypes")
        Class clazz = nmsitem.getClass();
        Method method;
        try {
            method = clazz.getMethod("save", ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz());
            Object answer = method.invoke(nmsitem, ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance());
            return new NBTContainer(answer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getEntityNBTTagCompound(Object NMSEntity) {
        try {
            Object nbt = ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance();
            Object answer = ReflectionMethod.NMS_ENTITY_GET_NBT.run(NMSEntity, nbt);
            if (answer == null)
                answer = nbt;
            return answer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object setEntityNBTTag(Object NBTTag, Object NMSEntity) {
        try {
            ReflectionMethod.NMS_ENTITY_SET_NBT.run(NMSEntity, NBTTag);
            return NMSEntity;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Object getTileEntityNBTTagCompound(BlockState tile) {
        try {
            Object pos = ObjectCreator.NMS_BLOCKPOSITION.getInstance(tile.getX(), tile.getY(), tile.getZ());
            Object cworld = ClassWrapper.CRAFT_WORLD.getClazz().cast(tile.getWorld());
            Object nmsworld = ReflectionMethod.CRAFT_WORLD_GET_HANDLE.run(cworld);
            Object o = ReflectionMethod.NMS_WORLD_GET_TILEENTITY.run(nmsworld, pos);
            Object tag = ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance();
            Object answer = ReflectionMethod.TILEENTITY_GET_NBT.run(o, tag);
            if (answer == null)
                answer = tag;
            return answer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setTileEntityNBTTagCompound(BlockState tile, Object comp) {
        try {
            Object pos = ObjectCreator.NMS_BLOCKPOSITION.getInstance(tile.getX(), tile.getY(), tile.getZ());
            Object cworld = ClassWrapper.CRAFT_WORLD.getClazz().cast(tile.getWorld());
            Object nmsworld = ReflectionMethod.CRAFT_WORLD_GET_HANDLE.run(cworld);
            Object o = ReflectionMethod.NMS_WORLD_GET_TILEENTITY.run(nmsworld, pos);
            ReflectionMethod.TILEENTITY_SET_NBT.run(o, comp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
    public static Object getSubNBTTagCompound(Object compound, String name) {
        @SuppressWarnings("rawtypes")
        Class c = compound.getClass();
        Method method;
        try {
            method = c.getMethod("getCompound", String.class);
            Object answer = method.invoke(compound, name);
            return answer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addNBTTagCompound(NBTCompound comp, String name) {
        if (name == null) {
            remove(comp, name);
            return;
        }
        Object nbttag = comp.getCompound();
        if (nbttag == null) {
            nbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
        }
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(nbttag, comp);
        Method method;
        try {
            method = workingtag.getClass().getMethod("set", String.class, ClassWrapper.NMS_NBTBASE.getClazz());
            method.invoke(workingtag, name, ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance());
            comp.setCompound(nbttag);
            return;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return;
    }

    public static Boolean valideCompound(NBTCompound comp) {
        Object root = comp.getCompound();
        if (root == null) {
            root = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
        }
        return (gettoCompount(root, comp)) != null;
    }

    static Object gettoCompount(Object nbttag, NBTCompound comp) {
        Stack<String> structure = new Stack<>();
        while (comp.getParent() != null) {
            structure.add(comp.getName());
            comp = comp.getParent();
        }
        while (!structure.isEmpty()) {
            nbttag = getSubNBTTagCompound(nbttag, structure.pop());
            if (nbttag == null) {
                return null;
            }
        }
        return nbttag;
    }

    public static void addOtherNBTCompound(NBTCompound comp, NBTCompound nbtcompound) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
        }
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            ReflectionMethod.COMPOUND_ADD.run(workingtag, nbtcompound.getCompound());
            comp.setCompound(rootnbttag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getContent(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
        }
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try {
            method = workingtag.getClass().getMethod("get", String.class);
            return method.invoke(workingtag, key).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void set(NBTCompound comp, String key, Object val) {
        if (val == null) {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
        }
        if (!valideCompound(comp)) {
            new Throwable("InvalideCompound").printStackTrace();
            return;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try {
            method = workingtag.getClass().getMethod("set", String.class, ClassWrapper.NMS_NBTBASE.getClazz());
            method.invoke(workingtag, key, val);
            comp.setCompound(rootnbttag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static NBTList getList(NBTCompound comp, String key, NBTType type) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
        }
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try {
            method = workingtag.getClass().getMethod("getList", String.class, int.class);
            return new NBTList(comp, key, type, method.invoke(workingtag, key, type.getId()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void setObject(NBTCompound comp, String key, Object value) {
        if (!MinecraftVersion.hasGsonSupport()) return;
        try {
            String json = GsonWrapper.getString(value);
            setData(comp, ReflectionMethod.COMPOUND_SET_STRING, key, json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static <T> T getObject(NBTCompound comp, String key, Class<T> type) {
        if (!MinecraftVersion.hasGsonSupport()) return null;
        String json = (String) getData(comp, ReflectionMethod.COMPOUND_GET_STRING, key);
        if (json == null) {
            return null;
        }
        return GsonWrapper.deserializeJson(json, type);
    }

    public static void remove(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
        }
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(rootnbttag, comp);
        ReflectionMethod.COMPOUND_REMOVE_KEY.run(workingtag, key);
        comp.setCompound(rootnbttag);
    }

    @SuppressWarnings("unchecked")
    public static Set<String> getKeys(NBTCompound comp) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
        }
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        return (Set<String>) ReflectionMethod.COMPOUND_GET_KEYS.run(workingtag);
    }

    public static void setData(NBTCompound comp, ReflectionMethod type, String key, Object data) {
        if (data == null) {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
        }
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(rootnbttag, comp);
        type.run(workingtag, key, data);
        comp.setCompound(rootnbttag);
    }

    public static Object getData(NBTCompound comp, ReflectionMethod type, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            return null;
        }
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        return type.run(workingtag, key);
    }

}
