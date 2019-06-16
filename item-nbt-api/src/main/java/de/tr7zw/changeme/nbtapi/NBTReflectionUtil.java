package de.tr7zw.changeme.nbtapi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;

import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;

import de.tr7zw.changeme.nbtapi.utils.GsonWrapper;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ClassWrapper;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ObjectCreator;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;

/**
 * Utility class for translating NBTApi calls to reflections into NMS code All
 * methods are allowed to throw {@link NbtApiException}
 * 
 * @author tr7zw
 *
 */
public class NBTReflectionUtil {

	/**
	 * Hidden constructor
	 */
	private NBTReflectionUtil() {

	}

	/**
	 * Gets the NMS Entity for a given Bukkit Entity
	 * 
	 * @param entity Bukkit Entity
	 * @return NMS Entity
	 */
	public static Object getNMSEntity(Entity entity) {
		try {
			return ReflectionMethod.CRAFT_ENTITY_GET_HANDLE.run(ClassWrapper.CRAFT_ENTITY.getClazz().cast(entity));
		} catch (Exception e) {
			throw new NbtApiException("Exception while getting the NMS Entity from a Bukkit Entity!", e);
		}
	}

	/**
	 * Reads in a FileInputStream as NMS Compound
	 * 
	 * @param stream InputStream of any NBT file
	 * @return NMS Compound
	 */
	public static Object readNBTFile(FileInputStream stream) {
		try {
			return ReflectionMethod.NBTFILE_READ.run(null, stream);
		} catch (Exception e) {
			throw new NbtApiException("Exception while reading a NBT File!", e);
		}
	}

	/**
	 * Writes a NMS Compound to a FileOutputStream
	 * 
	 * @param nbt    NMS Compound
	 * @param stream Stream to write to
	 * @return ???
	 */
	public static Object saveNBTFile(Object nbt, FileOutputStream stream) {
		try {
			return ReflectionMethod.NBTFILE_WRITE.run(null, nbt, stream);
		} catch (Exception e) {
			throw new NbtApiException("Exception while saving a NBT File!", e);
		}
	}

	/**
	 * Simulates getOrCreateTag. If an Item doesn't yet have a Tag, it will return a
	 * new empty tag.
	 * 
	 * @param nmsitem
	 * @return NMS Compound
	 */
	public static Object getItemRootNBTTagCompound(Object nmsitem) {
		try {
			Object answer = ReflectionMethod.NMSITEM_GETTAG.run(nmsitem);
			return answer != null ? answer : ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
		} catch (Exception e) {
			throw new NbtApiException("Exception while getting an Itemstack's NBTCompound!", e);
		}
	}

	/**
	 * Converts {@link NBTCompound} to NMS ItemStacks
	 * 
	 * @param nbtcompound Any valid {@link NBTCompound}
	 * @return NMS ItemStack
	 */
	public static Object convertNBTCompoundtoNMSItem(NBTCompound nbtcompound) {
		try {
			if (MinecraftVersion.getVersion().getVersionId() >= MinecraftVersion.MC1_11_R1.getVersionId()) {
				return ObjectCreator.NMS_COMPOUNDFROMITEM.getInstance(nbtcompound.getCompound());
			} else {
				return ReflectionMethod.NMSITEM_CREATESTACK.run(null, nbtcompound.getCompound());
			}
		} catch (Exception e) {
			throw new NbtApiException("Exception while converting NBTCompound to NMS ItemStack!", e);
		}
	}

	/**
	 * Converts NMS ItemStacks to {@link NBTContainer}
	 * 
	 * @param nmsitem NMS ItemStack
	 * @return {@link NBTContainer} with all the data
	 */
	public static NBTContainer convertNMSItemtoNBTCompound(Object nmsitem) {
		try {
			Object answer = ReflectionMethod.NMSITEM_SAVE.run(nmsitem, ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance());
			return new NBTContainer(answer);
		} catch (Exception e) {
			throw new NbtApiException("Exception while converting NMS ItemStack to NBTCompound!", e);
		}
	}

	/**
	 * Gets the Vanilla NBT Compound from a given NMS Entity
	 * 
	 * @param nmsEntity
	 * @return NMS NBT Compound
	 */
	public static Object getEntityNBTTagCompound(Object nmsEntity) {
		try {
			Object nbt = ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance();
			Object answer = ReflectionMethod.NMS_ENTITY_GET_NBT.run(nmsEntity, nbt);
			if (answer == null)
				answer = nbt;
			return answer;
		} catch (Exception e) {
			throw new NbtApiException("Exception while getting NBTCompound from NMS Entity!", e);
		}
	}

	/**
	 * Loads all Vanilla tags from a NMS Compound into a NMS Entity
	 * 
	 * @param nbtTag
	 * @param nmsEntity
	 * @return The NMS Entity
	 */
	public static Object setEntityNBTTag(Object nbtTag, Object nmsEntity) {
		try {
			ReflectionMethod.NMS_ENTITY_SET_NBT.run(nmsEntity, nbtTag);
			return nmsEntity;
		} catch (Exception ex) {
			throw new NbtApiException("Exception while setting the NBTCompound of an Entity", ex);
		}
	}

	/**
	 * Gets the NMS Compound from a given TileEntity
	 * 
	 * @param tile
	 * @return NMS Compound with the Vanilla data
	 */
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

	/**
	 * Sets Vanilla tags from a NMS Compound to a TileEntity
	 * 
	 * @param tile
	 * @param comp
	 */
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

	/**
	 * Gets the subCompound with a given name from a NMS Compound
	 * 
	 * @param compound
	 * @param name
	 * @return NMS Compound or null
	 */
	public static Object getSubNBTTagCompound(Object compound, String name) {
		try {
			return ReflectionMethod.COMPOUND_GET_COMPOUND.run(compound, name);
		} catch (Exception e) {
			throw new NbtApiException("Exception while getting NBT subcompounds!", e);
		}
	}

	/**
	 * Creates a subCompound with a given name in the given NMS Compound
	 * 
	 * @param comp
	 * @param name
	 */
	public static void addNBTTagCompound(NBTCompound comp, String name) {
		if (name == null) {
			remove(comp, name);
			return;
		}
		Object nbttag = comp.getCompound();
		if (nbttag == null) {
			nbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
		}
		if (!valideCompound(comp))
			return;
		Object workingtag = gettoCompount(nbttag, comp);
		try {
			ReflectionMethod.COMPOUND_SET.run(workingtag, name,
					ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance());
			comp.setCompound(nbttag);
		} catch (Exception e) {
			throw new NbtApiException("Exception while adding a Compound!", e);
		}
	}

	/**
	 * Checks if the Compound is correctly linked to it's roots
	 * 
	 * @param comp
	 * @return true if this is a valide Compound, else false
	 */
	public static Boolean valideCompound(NBTCompound comp) {
		Object root = comp.getCompound();
		if (root == null) {
			root = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
		}
		return (gettoCompount(root, comp)) != null;
	}

	protected static Object gettoCompount(Object nbttag, NBTCompound comp) {
		Deque<String> structure = new ArrayDeque<>();
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

	/**
	 * Merges the second {@link NBTCompound} into the first one
	 * 
	 * @param comp        Target for the merge
	 * @param nbtcompound Data to merge
	 */
	public static void addOtherNBTCompound(NBTCompound comp, NBTCompound nbtcompound) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
		}
		if (!valideCompound(comp))
			return;
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			ReflectionMethod.COMPOUND_ADD.run(workingtag, nbtcompound.getCompound());
			comp.setCompound(rootnbttag);
		} catch (Exception e) {
			throw new NbtApiException("Exception while merging two NBTCompounds!", e);
		}
	}

	/**
	 * Returns the content for a given key inside a Compound
	 * 
	 * @param comp
	 * @param key
	 * @return Content saved under this key
	 */
	public static String getContent(NBTCompound comp, String key) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
		}
		if (!valideCompound(comp))
			throw new NbtApiException("The Compound wasn't able to be linked back to the root!");
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			return ReflectionMethod.COMPOUND_GET.run(workingtag, key).toString();
		} catch (Exception e) {
			throw new NbtApiException("Exception while getting the Content for key '" + key + "'!", e);
		}
	}

	/**
	 * Sets a key in a {@link NBTCompound} to a given value
	 * 
	 * @param comp
	 * @param key
	 * @param val
	 */
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
			throw new NbtApiException("The Compound wasn't able to be linked back to the root!");
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			ReflectionMethod.COMPOUND_SET.run(workingtag, key, val);
			comp.setCompound(rootnbttag);
		} catch (Exception e) {
			throw new NbtApiException("Exception while setting key '" + key + "' to '" + val + "'!", e);
		}
	}

	/**
	 * Returns the List saved with a given key.
	 * 
	 * @param comp
	 * @param key
	 * @param type
	 * @param clazz
	 * @return The list at that key. Null if it's an invalide type
	 */
	@SuppressWarnings("unchecked")
	public static <T> NBTList<T> getList(NBTCompound comp, String key, NBTType type, Class<T> clazz) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
		}
		if (!valideCompound(comp))
			return null;
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Object nbt = ReflectionMethod.COMPOUND_GET_LIST.run(workingtag, key, type.getId());
			if (clazz == String.class) {
				return (NBTList<T>) new NBTStringList(comp, key, type, nbt);
			} else if (clazz == NBTListCompound.class) {
				return (NBTList<T>) new NBTCompoundList(comp, key, type, nbt);
			} else if (clazz == Integer.class) {
				return (NBTList<T>) new NBTIntegerList(comp, key, type, nbt);
			} else {
				return null;
			}
		} catch (Exception ex) {
			throw new NbtApiException("Exception while getting a list with the type '" + type + "'!", ex);
		}
	}

	/**
	 * Uses Gson to set a {@link Serializable} value in a Compound
	 * 
	 * @param comp
	 * @param key
	 * @param value
	 */
	public static void setObject(NBTCompound comp, String key, Object value) {
		if (!MinecraftVersion.hasGsonSupport())
			return;
		try {
			String json = GsonWrapper.getString(value);
			setData(comp, ReflectionMethod.COMPOUND_SET_STRING, key, json);
		} catch (Exception e) {
			throw new NbtApiException("Exception while setting the Object '" + value + "'!", e);
		}
	}

	/**
	 * Uses Gson to load back a {@link Serializable} object from the Compound
	 * 
	 * @param comp
	 * @param key
	 * @param type
	 * @return The loaded Object or null, if not found
	 */
	public static <T> T getObject(NBTCompound comp, String key, Class<T> type) {
		if (!MinecraftVersion.hasGsonSupport())
			return null;
		String json = (String) getData(comp, ReflectionMethod.COMPOUND_GET_STRING, key);
		if (json == null) {
			return null;
		}
		return GsonWrapper.deserializeJson(json, type);
	}

	/**
	 * Deletes the given key
	 * 
	 * @param comp
	 * @param key
	 */
	public static void remove(NBTCompound comp, String key) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
		}
		if (!valideCompound(comp))
			return;
		Object workingtag = gettoCompount(rootnbttag, comp);
		ReflectionMethod.COMPOUND_REMOVE_KEY.run(workingtag, key);
		comp.setCompound(rootnbttag);
	}

	/**
	 * Gets the Keyset inside this Compound
	 * 
	 * @param comp
	 * @return Set of all keys
	 */
	@SuppressWarnings("unchecked")
	public static Set<String> getKeys(NBTCompound comp) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
		}
		if (!valideCompound(comp))
			throw new NbtApiException("The Compound wasn't able to be linked back to the root!");
		Object workingtag = gettoCompount(rootnbttag, comp);
		return (Set<String>) ReflectionMethod.COMPOUND_GET_KEYS.run(workingtag);
	}

	/**
	 * Sets data inside the Compound
	 * 
	 * @param comp
	 * @param type
	 * @param key
	 * @param data
	 */
	public static void setData(NBTCompound comp, ReflectionMethod type, String key, Object data) {
		if (data == null) {
			remove(comp, key);
			return;
		}
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
		}
		if (!valideCompound(comp))
			throw new NbtApiException("The Compound wasn't able to be linked back to the root!");
		Object workingtag = gettoCompount(rootnbttag, comp);
		type.run(workingtag, key, data);
		comp.setCompound(rootnbttag);
	}

	/**
	 * Gets data from the Compound
	 * 
	 * @param comp
	 * @param type
	 * @param key
	 * @return The value or default fallback from NMS
	 */
	public static Object getData(NBTCompound comp, ReflectionMethod type, String key) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			return null;
		}
		if (!valideCompound(comp))
			throw new NbtApiException("The Compound wasn't able to be linked back to the root!");
		Object workingtag = gettoCompount(rootnbttag, comp);
		return type.run(workingtag, key);
	}

}
