package de.tr7zw.changeme.nbtapi;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ClassWrapper;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;

/**
 * Long implementation for NBTLists
 * 
 * @author tr7zw
 *
 */
public class NBTLongList extends NBTList<Long> {

	protected NBTLongList(NBTCompound owner, String name, NBTType type, Object list) {
		super(owner, name, type, list);
	}

	@Override
	protected Object asTag(Long object) {
		try {
			Constructor<?> con = ClassWrapper.NMS_NBTTAGLONG.getClazz().getDeclaredConstructor(long.class);
			con.setAccessible(true);
			return con.newInstance(object);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new NbtApiException("Error while wrapping the Object " + object + " to it's NMS object!", e);
		}
	}

	@Override
	public Long get(int index) {
		try {
			Object obj = ReflectionMethod.LIST_GET.run(listObject, index);
			return Long.valueOf(obj.toString().replace("L", ""));
		} catch (NumberFormatException nf) {
			return 0l;
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

}
