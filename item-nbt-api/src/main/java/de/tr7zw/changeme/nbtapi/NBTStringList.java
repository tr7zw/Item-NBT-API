package de.tr7zw.changeme.nbtapi;

public class NBTStringList extends NBTList<String>{

	protected NBTStringList(NBTCompound owner, String name, NBTType type, Object list) {
		super(owner, name, type, list);
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
	protected Object asTag(String object) throws Exception {
		return ClassWrapper.NMS_NBTTAGSTRING.getClazz().getConstructor(String.class).newInstance(object);
	}

}
