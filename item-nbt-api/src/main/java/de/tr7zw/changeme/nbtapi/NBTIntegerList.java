package de.tr7zw.changeme.nbtapi;

public class NBTIntegerList extends NBTList<Integer>{

	protected NBTIntegerList(NBTCompound owner, String name, NBTType type, Object list) {
		super(owner, name, type, list);
	}

	@Override
	protected Object asTag(Integer object) throws Exception {
		return ClassWrapper.NMS_NBTTAGINT.getClazz().getConstructor(int.class).newInstance(object);
	}

	@Override
	public Integer get(int index) {
		try {
			Object obj = ReflectionMethod.LIST_GET.run(listObject, index);
			return Integer.valueOf(obj.toString());
		}catch(NumberFormatException nf) {
			return 0;
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

}
