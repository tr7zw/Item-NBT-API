package de.tr7zw.itemnbtapi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.Stack;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.itemnbtapi.utils.GsonWrapper;
import de.tr7zw.itemnbtapi.utils.MethodNames;
import de.tr7zw.itemnbtapi.utils.MinecraftVersion;

// TODO: finish codestyle cleanup -sgdc3
public class NBTReflectionUtil {

    private static final String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

    @SuppressWarnings("rawtypes")
    private static Class getCraftItemStack() {

        try {
            Class clazz = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack");
            return clazz;
        } catch (Exception ex) {
            System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
            ex.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    private static Class getCraftEntity() {
        try {
            Class clazz = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftEntity");
            return clazz;
        } catch (Exception ex) {
            System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
            ex.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    protected static Class getNBTBase() {
        try {
            Class clazz = Class.forName("net.minecraft.server." + version + ".NBTBase");
            return clazz;
        } catch (Exception ex) {
            System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
            ex.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    protected static Class getNBTTagString() {
        try {
            Class clazz = Class.forName("net.minecraft.server." + version + ".NBTTagString");
            return clazz;
        } catch (Exception ex) {
            System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
            ex.printStackTrace();
            return null;
        }
    }
    
    @SuppressWarnings("rawtypes")
    protected static Class getNMSItemStack() {
        try {
            Class clazz = Class.forName("net.minecraft.server." + version + ".ItemStack");
            return clazz;
        } catch (Exception ex) {
            System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
            ex.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    protected static Class getNBTTagCompound() {
        try {
            Class clazz = Class.forName("net.minecraft.server." + version + ".NBTTagCompound");
            return clazz;
        } catch (Exception ex) {
            System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
            ex.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    protected static Class getNBTCompressedStreamTools() {
        try {
            Class clazz = Class.forName("net.minecraft.server." + version + ".NBTCompressedStreamTools");
            return clazz;
        } catch (Exception ex) {
            System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
            ex.printStackTrace();
            return null;
        }
    }
    
    @SuppressWarnings("rawtypes")
    protected static Class getMojangsonParser() {
        try {
            Class c = Class.forName("net.minecraft.server." + version + ".MojangsonParser");
            return c;
        } catch (Exception ex) {
            System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
            ex.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    protected static Class getTileEntity() {
        try {
            Class clazz = Class.forName("net.minecraft.server." + version + ".TileEntity");
            return clazz;
        } catch (Exception ex) {
            System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
            ex.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    protected static Class getCraftWorld() {
        try {
            Class clazz = Class.forName("org.bukkit.craftbukkit." + version + ".CraftWorld");
            return clazz;
        } catch (Exception ex) {
            System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
            ex.printStackTrace();
            return null;
        }
    }

    public static Object getNewNBTTag() {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("net.minecraft.server." + version + ".NBTTagCompound");
            return c.newInstance();
        } catch (Exception ex) {
            System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
            ex.printStackTrace();
            return null;
        }
    }

    private static Object getNewBlockPosition(int x, int y, int z) {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        try {
            @SuppressWarnings("rawtypes")
            Class clazz = Class.forName("net.minecraft.server." + version + ".BlockPosition");
            return clazz.getConstructor(int.class, int.class, int.class).newInstance(x, y, z);
        } catch (Exception ex) {
            System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
            ex.printStackTrace();
            return null;
        }
    }

    public static Object setNBTTag(Object NBTTag, Object NMSItem) {
        try {
            Method method;
            method = NMSItem.getClass().getMethod("setTag", NBTTag.getClass());
            method.invoke(NMSItem, NBTTag);
            return NMSItem;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Object getNMSItemStack(ItemStack item) {
        @SuppressWarnings("rawtypes")
        Class clazz = getCraftItemStack();
        Method method;
        try {
            method = clazz.getMethod("asNMSCopy", ItemStack.class);
            Object answer = method.invoke(clazz, item);
            return answer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Object getNMSEntity(Entity entity) {
        @SuppressWarnings("rawtypes")
        Class clazz = getCraftEntity();
        Method method;
        try {
            method = clazz.getMethod("getHandle");
            return method.invoke(getCraftEntity().cast(entity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({"unchecked"})
    public static Object parseNBT(String json) {
        @SuppressWarnings("rawtypes")
        Class cis = getMojangsonParser();
        java.lang.reflect.Method method;
        try {
            method = cis.getMethod("parse", String.class);
            return method.invoke(null, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @SuppressWarnings({"unchecked"})
    public static Object readNBTFile(FileInputStream stream) {
        @SuppressWarnings("rawtypes")
        Class clazz = getNBTCompressedStreamTools();
        Method method;
        try {
            method = clazz.getMethod("a", InputStream.class);
            return method.invoke(clazz, stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({"unchecked"})
    public static Object saveNBTFile(Object nbt, FileOutputStream stream) {
        @SuppressWarnings("rawtypes")
        Class clazz = getNBTCompressedStreamTools();
        Method method;
        try {
            method = clazz.getMethod("a", getNBTTagCompound(), OutputStream.class);
            return method.invoke(clazz, nbt, stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({"unchecked"})
    public static ItemStack getBukkitItemStack(Object item) {
        @SuppressWarnings("rawtypes")
        Class clazz = getCraftItemStack();
        Method method;
        try {
            method = clazz.getMethod("asCraftMirror", item.getClass());
            Object answer = method.invoke(clazz, item);
            return (ItemStack) answer;
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
        Class clazz = getNMSItemStack();
        try {
            Object nmsstack = clazz.getConstructor(getNBTTagCompound()).newInstance(nbtcompound.getCompound());
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
            method = clazz.getMethod("save", getNBTTagCompound());
            Object answer = method.invoke(nmsitem, getNewNBTTag());
            return new NBTContainer(answer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({"unchecked"})
    public static Object getEntityNBTTagCompound(Object nmsitem) {
        @SuppressWarnings("rawtypes")
        Class c = nmsitem.getClass();
        Method method;
        try {
            method = c.getMethod(MethodNames.getEntityNbtGetterMethodName(), getNBTTagCompound());
            Object nbt = getNBTTagCompound().newInstance();
            Object answer = method.invoke(nmsitem, nbt);
            if (answer == null)
                answer = nbt;
            return answer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object setEntityNBTTag(Object NBTTag, Object NMSItem) {
        try {
            Method method;
            method = NMSItem.getClass().getMethod(MethodNames.getEntityNbtSetterMethodName(), getNBTTagCompound());
            method.invoke(NMSItem, NBTTag);
            return NMSItem;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Object getTileEntityNBTTagCompound(BlockState tile) {
        Method method;
        try {
            Object pos = getNewBlockPosition(tile.getX(), tile.getY(), tile.getZ());
            Object cworld = getCraftWorld().cast(tile.getWorld());
            Object nmsworld = cworld.getClass().getMethod("getHandle").invoke(cworld);
            Object o = nmsworld.getClass().getMethod("getTileEntity", pos.getClass()).invoke(nmsworld, pos);
            method = getTileEntity().getMethod(MethodNames.getTileDataMethodName(), getNBTTagCompound());
            Object tag = getNBTTagCompound().newInstance();
            Object answer = method.invoke(o, tag);
            if (answer == null)
                answer = tag;
            return answer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setTileEntityNBTTagCompound(BlockState tile, Object comp) {
        Method method;
        try {
            Object pos = getNewBlockPosition(tile.getX(), tile.getY(), tile.getZ());
            Object cworld = getCraftWorld().cast(tile.getWorld());
            Object nmsworld = cworld.getClass().getMethod("getHandle").invoke(cworld);
            Object o = nmsworld.getClass().getMethod("getTileEntity", pos.getClass()).invoke(nmsworld, pos);
            method = getTileEntity().getMethod("a", getNBTTagCompound());
            method.invoke(o, comp);
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
            nbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(nbttag, comp);
        Method method;
        try {
            method = workingtag.getClass().getMethod("set", String.class, getNBTBase());
            method.invoke(workingtag, name, getNBTTagCompound().newInstance());
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
            root = getNewNBTTag();
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
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try {
            method = workingtag.getClass().getMethod("a", getNBTTagCompound());
            method.invoke(workingtag, nbtcompound.getCompound());
            comp.setCompound(rootnbttag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getContent(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
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

    public static byte getType(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) return 0;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try {
            method = workingtag.getClass().getMethod(MethodNames.getTypeMethodName(), String.class);
            return (byte) method.invoke(workingtag, key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public static void set(NBTCompound comp, String key, Object val) {
        if (val == null) {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            new Throwable("InvalideCompound").printStackTrace();
            return;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try {
            method = workingtag.getClass().getMethod("set", String.class, getNBTBase());
            method.invoke(workingtag, key, val);
            comp.setCompound(rootnbttag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static NBTList getList(NBTCompound comp, String key, NBTType type) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
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
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try {
            method = workingtag.getClass().getMethod("remove", String.class);
            method.invoke(workingtag, key);
            comp.setCompound(rootnbttag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Boolean hasKey(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try {
            method = workingtag.getClass().getMethod("hasKey", String.class);
            return (Boolean) method.invoke(workingtag, key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Set<String> getKeys(NBTCompound comp) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try {
            method = workingtag.getClass().getMethod("c");
            return (Set<String>) method.invoke(workingtag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static void setData(NBTCompound comp, ReflectionMethod type, String key, Object data) {
        if (data == null) {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(rootnbttag, comp);
        type.run(workingtag, key, data);
        comp.setCompound(rootnbttag);
    }
    
    public static Object getData(NBTCompound comp, ReflectionMethod type, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        return type.run(workingtag, key);
    }

}
