package de.tr7zw.itemnbtapi;

import de.tr7zw.itemnbtapi.utils.MinecraftVersion;

public class NBTIntegerList extends NBTList<Integer>{

	protected NBTIntegerList(NBTCompound owner, String name, NBTType type, Object list) {
		super(owner, name, type, list);
	}

	@Override
	public boolean add(Integer element) {
		try {
			if (MinecraftVersion.getVersion().getVersionId() >= MinecraftVersion.MC1_14_R1.getVersionId()) {
				ReflectionMethod.LIST_ADD.run(listObject, 0,
						ClassWrapper.NMS_NBTTAGINT.getClazz().getConstructor(int.class).newInstance(element));
			} else {
				ReflectionMethod.LEGACY_LIST_ADD.run(listObject,
						ClassWrapper.NMS_NBTTAGINT.getClazz().getConstructor(int.class).newInstance(element));
			}
			save();
			return true;
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

	@Override
	public void add(int index, Integer element) {
		try {
			if (MinecraftVersion.getVersion().getVersionId() >= MinecraftVersion.MC1_14_R1.getVersionId()) {
				ReflectionMethod.LIST_ADD.run(listObject, index,
						ClassWrapper.NMS_NBTTAGINT.getClazz().getConstructor(int.class).newInstance(element));
			} else {
				ReflectionMethod.LEGACY_LIST_ADD.run(listObject,
						ClassWrapper.NMS_NBTTAGINT.getClazz().getConstructor(int.class).newInstance(element));
			}
			save();
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

	@Override
	public Integer get(int index) {
		try {
			Object obj = ReflectionMethod.LIST_GET.run(listObject, index);
			System.out.println("OBJ: " + obj.getClass() + " " + obj);
			return Integer.valueOf(obj.toString());
		}catch(NumberFormatException nf) {
			return 0;
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

	@Override
	public Integer set(int index, Integer element) {
		Integer prev = get(index);
		try {
			ReflectionMethod.LIST_SET.run(listObject, index,
					ClassWrapper.NMS_NBTTAGINT.getClazz().getConstructor(int.class).newInstance(element));
			save();
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
		return prev;
	}

}
