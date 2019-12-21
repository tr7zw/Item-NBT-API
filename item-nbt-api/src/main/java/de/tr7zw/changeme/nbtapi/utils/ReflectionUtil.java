package de.tr7zw.changeme.nbtapi.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import de.tr7zw.changeme.nbtapi.NbtApiException;

public final class ReflectionUtil {

	private static Field field_modifiers;

	static {
		try {
			field_modifiers = Field.class.getDeclaredField("modifiers");
			field_modifiers.setAccessible(true);
		} catch (NoSuchFieldException ex) {
			try {
				// This hacky workaround is for newer jdk versions 11+?
				Method fieldGetter = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
				fieldGetter.setAccessible(true);
				Field[] fields = (Field[]) fieldGetter.invoke(Field.class, false);
				for (Field f : fields)
					if (f.getName().equals("modifiers")) {
						field_modifiers = f;
						field_modifiers.setAccessible(true);
						break;
					}
			} catch (Exception e) {
				throw new NbtApiException(e);
			}
		}
		if (field_modifiers == null) {
			throw new NbtApiException("Unable to init the modifiers Field.");
		}
	}

	public static Field makeNonFinal(Field field) throws IllegalArgumentException, IllegalAccessException {
		int mods = field.getModifiers();
		if (Modifier.isFinal(mods)) {
			field_modifiers.set(field, mods & ~Modifier.FINAL);
		}
		return field;
	}

	public static void setFinal(Object obj, Field field, Object newValue)
			throws IllegalArgumentException, IllegalAccessException {
		field.setAccessible(true);
		field = makeNonFinal(field);
		field.set(obj, newValue);
	}

}