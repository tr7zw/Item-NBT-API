package dev.tr7zw.nbtapi;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import dev.tr7zw.nbtapi.utils.nmsmappings.ClassWrapper;
import dev.tr7zw.nbtapi.utils.nmsmappings.ReflectionMethod;

/**
 * Float implementation for NBTLists
 * 
 * @author tr7zw
 *
 */
public class NBTFloatList extends NBTList<Float> {

	protected NBTFloatList(NBTCompound owner, String name, NBTType type, Object list) {
		super(owner, name, type, list);
	}

	@Override
	protected Object asTag(Float object) {
		try {
			Constructor<?> con = ClassWrapper.NMS_NBTTAGFLOAT.getClazz().getDeclaredConstructor(float.class);
			con.setAccessible(true);
			return con.newInstance(object);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new NbtApiException("Error while wrapping the Object " + object + " to it's NMS object!", e);
		}
	}

	@Override
	public Float get(int index) {
		try {
			Object obj = ReflectionMethod.LIST_GET.run(listObject, index);
			return Float.valueOf(obj.toString());
		} catch (NumberFormatException nf) {
			return 0f;
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

}
