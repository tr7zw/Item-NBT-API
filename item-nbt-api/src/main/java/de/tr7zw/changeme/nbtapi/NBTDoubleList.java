package de.tr7zw.changeme.nbtapi;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ClassWrapper;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;

/**
 * Double implementation for NBTLists
 * 
 * @author tr7zw
 *
 */
public class NBTDoubleList extends NBTList<Double> {

	protected NBTDoubleList(NBTCompound owner, String name, NBTType type, Object list) {
		super(owner, name, type, list);
	}

	@Override
	protected Object asTag(Double object) {
		try {
			Constructor<?> con = ClassWrapper.NMS_NBTTAGDOUBLE.getClazz().getDeclaredConstructor(double.class);
			con.setAccessible(true);
			return con.newInstance(object);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new NbtApiException("Error while wrapping the Object " + object + " to it's NMS object!", e);
		}
	}

	@Override
	public Double get(int index) {
		try {
			Object obj = ReflectionMethod.LIST_GET.run(listObject, index);
			return Double.valueOf(obj.toString());
		} catch (NumberFormatException nf) {
			return 0d;
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

}
