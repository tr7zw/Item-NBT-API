package de.tr7zw.changeme.nbtapi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.util.Set;
import java.util.Stack;

import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;

import de.tr7zw.changeme.nbtapi.utils.GsonWrapper;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;

public class NBTReflectionUtil {  

    public static Object getNMSEntity(Entity entity) {
        try {
            return ReflectionMethod.CRAFT_ENTITY_GET_HANDLE.run(ClassWrapper.CRAFT_ENTITY.getClazz().cast(entity));
        } catch (Exception e) {
        	throw new NbtApiException("Exception while getting the NMS Entity from a Bukkit Entity!", e);
        }
    }

    public static Object readNBTFile(FileInputStream stream) {
        try {
            return ReflectionMethod.NBTFILE_READ.run(null, stream);
        } catch (Exception e) {
        	throw new NbtApiException("Exception while reading a NBT File!", e);
        }
    }

    public static Object saveNBTFile(Object nbt, FileOutputStream stream) {
        try {
            return ReflectionMethod.NBTFILE_WRITE.run(null, nbt, stream);
        } catch (Exception e) {
        	throw new NbtApiException("Exception while saving a NBT File!", e);
        }
    }

    /**
     * Simulates getOrCreateTag. If an Item doesn't yet have a Tag, it will return a new empty tag.
     * 
     * @param nmsitem
     * @return
     */
    public static Object getItemRootNBTTagCompound(Object nmsitem) {
        try {
            Object answer = ReflectionMethod.NMSITEM_GETTAG.run(nmsitem);
            return answer != null ? answer : ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
        } catch (Exception e) {
            throw new NbtApiException("Exception while getting an Itemstack's NBTCompound!", e);
        }
    }

    public static Object convertNBTCompoundtoNMSItem(NBTCompound nbtcompound) {
        Class<?> clazz = ClassWrapper.NMS_ITEMSTACK.getClazz();
        try {
            if(MinecraftVersion.getVersion().getVersionId() >= MinecraftVersion.MC1_11_R1.getVersionId()){
                Constructor<?> constructor = clazz.getDeclaredConstructor(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz());
                constructor.setAccessible(true);
                return constructor.newInstance(nbtcompound.getCompound());
            }else{
                return ReflectionMethod.NMSITEM_CREATESTACK.run(null, nbtcompound.getCompound());
            }
        } catch (Exception e) {
        	throw new NbtApiException("Exception while converting NBTCompound to NMS ItemStack!", e);
        }
    }

    public static NBTContainer convertNMSItemtoNBTCompound(Object nmsitem) {
        try {
            Object answer = ReflectionMethod.NMSITEM_SAVE.run(nmsitem, ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance());
            return new NBTContainer(answer);
        } catch (Exception e) {
        	throw new NbtApiException("Exception while converting NMS ItemStack to NBTCompound!", e);
        }
    }

    public static Object getEntityNBTTagCompound(Object NMSEntity) {
        try {
            Object nbt = ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance();
            Object answer = ReflectionMethod.NMS_ENTITY_GET_NBT.run(NMSEntity, nbt);
            if (answer == null)
                answer = nbt;
            return answer;
        } catch (Exception e) {
        	throw new NbtApiException("Exception while getting NBTCompound from NMS Entity!", e);
        }
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
        	throw new NbtApiException("Exception while getting NBTCompound from TileEntity!", e);
        }
    }

    public static void setTileEntityNBTTagCompound(BlockState tile, Object comp) {
        try {
            Object pos = ObjectCreator.NMS_BLOCKPOSITION.getInstance(tile.getX(), tile.getY(), tile.getZ());
            Object cworld = ClassWrapper.CRAFT_WORLD.getClazz().cast(tile.getWorld());
            Object nmsworld = ReflectionMethod.CRAFT_WORLD_GET_HANDLE.run(cworld);
            Object o = ReflectionMethod.NMS_WORLD_GET_TILEENTITY.run(nmsworld, pos);
            ReflectionMethod.TILEENTITY_SET_NBT.run(o, comp);
        } catch (Exception e) {
        	throw new NbtApiException("Exception while setting NBTData for a TileEntity!", e);
        }
    }


    public static Object getSubNBTTagCompound(Object compound, String name) {
        try {
            Object answer = ReflectionMethod.COMPOUND_GET_COMPOUND.run(compound, name);
            return answer;
        } catch (Exception e) {
        	throw new NbtApiException("Exception while getting NBT subcompounds!", e);
        }
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
        try {
            ReflectionMethod.COMPOUND_SET.run(workingtag, name, ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance());
            comp.setCompound(nbttag);
            return;
        } catch (Exception e) {
        	throw new NbtApiException("Exception while adding a Compound!", e);
        }
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
        } catch (Exception e) {
        	throw new NbtApiException("Exception while merging two NBTCompounds!", e);
        }
    }

    public static String getContent(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
        }
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            return ReflectionMethod.COMPOUND_GET.run(workingtag, key).toString();
        } catch (Exception e) {
        	throw new NbtApiException("Exception while getting the Content for key '" + key + "'!", e);
        }
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
        try {
            ReflectionMethod.COMPOUND_SET.run(workingtag, key, val);
            comp.setCompound(rootnbttag);
        } catch (Exception e) {
        	throw new NbtApiException("Exception while setting key '" + key + "' to '" + val + "'!", e);
        }
    }

    @SuppressWarnings("unchecked")
	public static <T> NBTList<T> getList(NBTCompound comp, String key, NBTType type, Class<T> clazz) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
        }
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Object nbt = ReflectionMethod.COMPOUND_GET_LIST.run(workingtag, key, type.getId());
            if(clazz == String.class) {
            	return (NBTList<T>) new NBTStringList(comp, key, type, nbt);
            } else if(clazz == NBTListCompound.class) {
            	return (NBTList<T>) new NBTCompoundList(comp, key, type, nbt);
            } else if(clazz == Integer.class) {
            	return (NBTList<T>) new NBTIntegerList(comp, key, type, nbt);
            }else {
            	return null;
            }
        } catch (Exception ex) {
            throw new NbtApiException("Exception while getting a list with the type '" + type + "'!", ex);
        }
    }

    public static void setObject(NBTCompound comp, String key, Object value) {
        if (!MinecraftVersion.hasGsonSupport()) return;
        try {
            String json = GsonWrapper.getString(value);
            setData(comp, ReflectionMethod.COMPOUND_SET_STRING, key, json);
        } catch (Exception e) {
        	throw new NbtApiException("Exception while setting the Object '" + value + "'!", e);
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
