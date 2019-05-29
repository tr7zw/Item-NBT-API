package de.tr7zw.itemnbtapi;

import de.tr7zw.itemnbtapi.utils.MinecraftVersion;

public class NBTStringList extends NBTList<String>{

	protected NBTStringList(NBTCompound owner, String name, NBTType type, Object list) {
		super(owner, name, type, list);
	}

	@Override
	public boolean add(String element) {
		try {
			if (MinecraftVersion.getVersion().getVersionId() >= MinecraftVersion.MC1_14_R1.getVersionId()) {
				ReflectionMethod.LIST_ADD.run(listObject, 0,
						ClassWrapper.NMS_NBTTAGSTRING.getClazz().getConstructor(String.class).newInstance(element));
			} else {
				ReflectionMethod.LEGACY_LIST_ADD.run(listObject,
						ClassWrapper.NMS_NBTTAGSTRING.getClazz().getConstructor(String.class).newInstance(element));
			}
			save();
			return true;
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

	@Override
	public void add(int index, String element) {
		try {
			if (MinecraftVersion.getVersion().getVersionId() >= MinecraftVersion.MC1_14_R1.getVersionId()) {
				ReflectionMethod.LIST_ADD.run(listObject, index,
						ClassWrapper.NMS_NBTTAGSTRING.getClazz().getConstructor(String.class).newInstance(element));
			} else {
				ReflectionMethod.LEGACY_LIST_ADD.run(listObject,
						ClassWrapper.NMS_NBTTAGSTRING.getClazz().getConstructor(String.class).newInstance(element));
			}
			save();
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

	@Override
	public String get(int index) {
		try {
			return (String) ReflectionMethod.LIST_GET_STRING.run(listObject, index);
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

	@Override
	public String set(int index, String element) {
		String prev = get(index);
		try {
			ReflectionMethod.LIST_SET.run(listObject, index,
					ClassWrapper.NMS_NBTTAGSTRING.getClazz().getConstructor(String.class).newInstance(element));
			save();
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
		return prev;
	}

}
