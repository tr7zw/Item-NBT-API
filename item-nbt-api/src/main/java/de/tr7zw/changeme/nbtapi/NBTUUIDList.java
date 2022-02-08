package de.tr7zw.changeme.nbtapi;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import de.tr7zw.changeme.nbtapi.utils.UUIDUtil;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ClassWrapper;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;

/**
 * Integer implementation for NBTLists
 * 
 * @author tr7zw
 *
 */
public class NBTUUIDList extends NBTList<UUID> {

    private final NBTContainer tmpContainer;
    
	protected NBTUUIDList(NBTCompound owner, String name, NBTType type, Object list) {
		super(owner, name, type, list);
		this.tmpContainer = new NBTContainer();
	}

	@Override
	protected Object asTag(UUID object) {
		try {
			Constructor<?> con = ClassWrapper.NMS_NBTTAGINTARRAY.getClazz().getDeclaredConstructor(int[].class);
			con.setAccessible(true);
			return con.newInstance(UUIDUtil.uuidToIntArray(object));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new NbtApiException("Error while wrapping the Object " + object + " to it's NMS object!", e);
		}
	}

	@Override
	public UUID get(int index) {
		try {
			Object obj = ReflectionMethod.LIST_GET.run(listObject, index);
			ReflectionMethod.COMPOUND_SET.run(tmpContainer.getCompound(), "tmp", obj);
			int[] val = tmpContainer.getIntArray("tmp");
			tmpContainer.removeKey("tmp");
			return UUIDUtil.uuidFromIntArray(val);
		} catch (NumberFormatException nf) {
			return null;
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

}
