package de.tr7zw.itemnbtapi;

import java.util.Set;
import java.util.Stack;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class NBTReflectionUtil {

    private static final Gson gson = new Gson();
    private static final String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    
    @SuppressWarnings("rawtypes")
    private static Class getCraftItemStack() {
        
        try {
            Class c = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack");
            return c;
        } catch (Exception ex) {
            System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
            ex.printStackTrace();
            return null;
        }
    }
    
    @SuppressWarnings("rawtypes")
    private static Class getNBTBase() {
        try {
            Class c = Class.forName("net.minecraft.server." + version + ".NBTBase");
            return c;
        } catch (Exception ex) {
            System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
            ex.printStackTrace();
            return null;
        }
    }
    
    @SuppressWarnings("rawtypes")
    private static Class getNBTTagCompound() {
        try {
            Class c = Class.forName("net.minecraft.server." + version + ".NBTTagCompound");
            return c;
        } catch (Exception ex) {
            System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
            ex.printStackTrace();
            return null;
        }
    }

    private static Object getNewNBTTag() {
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

    private static Object setNBTTag(Object NBTTag, Object NMSItem) {
        try {
            java.lang.reflect.Method method;
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
        Class cis = getCraftItemStack();
        java.lang.reflect.Method method;
        try {
            method = cis.getMethod("asNMSCopy", ItemStack.class);
            Object answer = method.invoke(cis, item);
            return answer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({"unchecked"})
    private static ItemStack getBukkitItemStack(Object item) {
        @SuppressWarnings("rawtypes")
        Class cis = getCraftItemStack();
        java.lang.reflect.Method method;
        try {
            method = cis.getMethod("asCraftMirror", item.getClass());
            Object answer = method.invoke(cis, item);
            return (ItemStack) answer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({"unchecked"})
    public static Object getRootNBTTagCompound(Object nmsitem) {
        @SuppressWarnings("rawtypes")
        Class c = nmsitem.getClass();
        java.lang.reflect.Method method;
        try {
            method = c.getMethod("getTag");
            Object answer = method.invoke(nmsitem);
            return answer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Object getSubNBTTagCompound(Object compound, String name){
        @SuppressWarnings("rawtypes")
        Class c = compound.getClass();
        java.lang.reflect.Method method;
        try {
            method = c.getMethod("getCompound", String.class);
            Object answer = method.invoke(compound, name);
            return answer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ItemStack addNBTTagCompound(ItemStack item, NBTCompound comp, String name){
        if(name == null)return remove(item, comp, name);
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object nbttag = getRootNBTTagCompound(nmsitem);
        if (nbttag == null) {
            nbttag = getNewNBTTag();
        }
        if(!valideCompound(item, comp))return item;
        Object workingtag = gettoCompount(nbttag, comp);
        java.lang.reflect.Method method;
        try {
            method = workingtag.getClass().getMethod("set", String.class, getNBTBase());
            method.invoke(workingtag, name, getNBTTagCompound().newInstance());
            nmsitem = setNBTTag(nbttag, nmsitem);
            return getBukkitItemStack(nmsitem);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return item;
    }

    public static Boolean valideCompound(ItemStack item, NBTCompound comp){
        Object root = getRootNBTTagCompound(getNMSItemStack(item));
        if (root == null) {
            root = getNewNBTTag();
        }
        return (gettoCompount(root, comp)) != null;
    }

    private static Object gettoCompount(Object nbttag, NBTCompound comp){
        Stack<String> structure = new Stack<>();
        while(comp.getParent() != null){
            structure.add(comp.getName());
            comp = comp.getParent();
        }
        while(!structure.isEmpty()){
            nbttag = getSubNBTTagCompound(nbttag, structure.pop());
            if(nbttag == null){
                return null;
            }
        }
        return nbttag;
    }

    public static ItemStack setString(ItemStack item, NBTCompound comp, String key, String text) {
        if(text == null)return remove(item, comp, key);
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object rootnbttag = getRootNBTTagCompound(nmsitem);
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if(!valideCompound(item, comp))return item;
        Object workingtag = gettoCompount(rootnbttag, comp);
        java.lang.reflect.Method method;
        try {
            method = workingtag.getClass().getMethod("setString", String.class, String.class);
            method.invoke(workingtag, key, text);
            nmsitem = setNBTTag(rootnbttag, nmsitem);
            return getBukkitItemStack(nmsitem);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return item;
    }

    public static String getString(ItemStack item, NBTCompound comp, String key) {
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object rootnbttag = getRootNBTTagCompound(nmsitem);
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if(!valideCompound(item, comp))return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        java.lang.reflect.Method method;
        try {
            method = workingtag.getClass().getMethod("getString", String.class);
            return (String) method.invoke(workingtag, key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ItemStack setInt(ItemStack item, NBTCompound comp, String key, Integer i) {
        if(i == null)return remove(item, comp, key);
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object rootnbttag = getRootNBTTagCompound(nmsitem);
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if(!valideCompound(item, comp))return item;
        Object workingtag = gettoCompount(rootnbttag, comp);
        java.lang.reflect.Method method;
        try {
            method = workingtag.getClass().getMethod("setInt", String.class, int.class);
            method.invoke(workingtag, key, i);
            nmsitem = setNBTTag(rootnbttag, nmsitem);
            return getBukkitItemStack(nmsitem);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return item;
    }

    public static Integer getInt(ItemStack item, NBTCompound comp, String key) {
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object rootnbttag = getRootNBTTagCompound(nmsitem);
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if(!valideCompound(item, comp))return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        java.lang.reflect.Method method;
        try {
            method = workingtag.getClass().getMethod("getInt", String.class);
            return (Integer) method.invoke(workingtag, key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ItemStack setDouble(ItemStack item, NBTCompound comp, String key, Double d) {
        if(d == null)return remove(item, comp, key);
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object rootnbttag = getRootNBTTagCompound(nmsitem);
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if(!valideCompound(item, comp))return item;
        Object workingtag = gettoCompount(rootnbttag, comp);
        java.lang.reflect.Method method;
        try {
            method = workingtag.getClass().getMethod("setDouble", String.class, double.class);
            method.invoke(workingtag, key, d);
            nmsitem = setNBTTag(rootnbttag, nmsitem);
            return getBukkitItemStack(nmsitem);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return item;
    }

    public static Double getDouble(ItemStack item, NBTCompound comp, String key) {
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object rootnbttag = getRootNBTTagCompound(nmsitem);
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if(!valideCompound(item, comp))return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        java.lang.reflect.Method method;
        try {
            method = workingtag.getClass().getMethod("getDouble", String.class);
            return (Double) method.invoke(workingtag, key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ItemStack setBoolean(ItemStack item, NBTCompound comp, String key, Boolean d) {
        if(d == null)return remove(item, comp, key);
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object rootnbttag = getRootNBTTagCompound(nmsitem);
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if(!valideCompound(item, comp))return item;
        Object workingtag = gettoCompount(rootnbttag, comp);
        java.lang.reflect.Method method;
        try {
            method = workingtag.getClass().getMethod("setBoolean", String.class, boolean.class);
            method.invoke(workingtag, key, d);
            nmsitem = setNBTTag(rootnbttag, nmsitem);
            return getBukkitItemStack(nmsitem);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return item;
    }

    public static Boolean getBoolean(ItemStack item, NBTCompound comp, String key) {
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object rootnbttag = getRootNBTTagCompound(nmsitem);
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if(!valideCompound(item, comp))return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        java.lang.reflect.Method method;
        try {
            method = workingtag.getClass().getMethod("getBoolean", String.class);
            return (Boolean) method.invoke(workingtag, key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ItemStack setObject(ItemStack item, NBTCompound comp, String key, Object value) {
        try {
            String json = gson.toJson(value);
            return setString(item, comp, key, json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static <T> T getObject(ItemStack item, NBTCompound comp, String key, Class<T> type) {
        String json = getString(item, comp, key);
        if(json == null){
            return null;
        }
        try {
            return deserializeJson(json, type);
        } catch (JsonSyntaxException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static <T> T deserializeJson(String json, Class<T> type) throws JsonSyntaxException {
        if (json == null) {
            return null;
        }

        T obj = gson.fromJson(json, type);
        return type.cast(obj);
    }

    public static ItemStack remove(ItemStack item, NBTCompound comp, String key) {
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object rootnbttag = getRootNBTTagCompound(nmsitem);
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if(!valideCompound(item, comp))return item;
        Object workingtag = gettoCompount(rootnbttag, comp);
        java.lang.reflect.Method method;
        try {
            method = workingtag.getClass().getMethod("remove", String.class);
            method.invoke(workingtag, key);
            nmsitem = setNBTTag(rootnbttag, nmsitem);
            return getBukkitItemStack(nmsitem);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return item;
    }

    public static Boolean hasKey(ItemStack item, NBTCompound comp, String key) {
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object rootnbttag = getRootNBTTagCompound(nmsitem);
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if(!valideCompound(item, comp))return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        java.lang.reflect.Method method;
        try {
            method = workingtag.getClass().getMethod("hasKey", String.class);
            return (Boolean) method.invoke(workingtag, key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Set<String> getKeys(ItemStack item, NBTCompound comp) {
        Object nmsitem = getNMSItemStack(item);
        if (nmsitem == null) {
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }
        Object rootnbttag = getRootNBTTagCompound(nmsitem);
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if(!valideCompound(item, comp))return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        java.lang.reflect.Method method;
        try {
            method = workingtag.getClass().getMethod("c");
            return (Set<String>) method.invoke(workingtag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
